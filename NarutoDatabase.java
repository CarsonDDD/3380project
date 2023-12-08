import java.sql.SQLException;
import java.sql.PreparedStatement;

public class NarutoDatabase extends Database implements CommandProcessor{
    // COMMANDS AND QUERIES GO IN THIS FILE
    @Override
    public boolean processCommand(String command, String[] args) throws Exception{
		switch (command.toLowerCase()) {
			case "quit":
				System.out.println("Existing database...");
				return false;
			case "help":
				helpCommand();
				return true;
			case "aa":
				averageAgeClan();
				return true;
			case "jubr":
				if (args.length == 1)
					jutsuByRank(args[0]);
				else
					System.err.println("The format is `jubr rankName`, please try again");
				return true;
			case "cg":
				clanGenkai();
				return true;
			case "rn":
				rationNature();
				return true;
			case "mpc":
				mostPopularChar();
				return true;
			case "ma":
				mostActors();;
				return true;
			case "ut":
				if (args.length >= 1)
					uniqueTrait(args[0]);
				else
					System.out.println("Need at least one argument");
				return true;
			case "ik":
				individualKek();
				return true;
			case "tc":
				teamChange();
				return true;
			default:
				System.out.println("Command not find, please use `Help` to view list of command");
				return true;
        }
    }

	public void helpCommand(){
		StringBuilder sb = new StringBuilder();

		sb.append("\n---- Help ----\n");
		sb.append("Commands:\n");
		sb.append("1. `Help` - Shows help menu\n");
		sb.append("2. `Quit` - Quits the program\n");
		sb.append("3. `aa` - The average age of characters in each clan and in ascending order\n");
		sb.append("4. `jubr` - A list of ninja characters from a specific rank that can use and jutsu they can use from each village\n\t(Academy Student, Genin, Chunin, Jonin, Kage,...)\n");
		sb.append("5. `ut` - Find characters with a specific unique trait and their affiliated teams\n");
		sb.append("6. `ik` - All characters (dead/alive) that have unique Kekkeigenkai. (Kekkei Genkai than only that character can use)\n");
		sb.append("7. `tc` - Listing all characters and the number of teams they have changed throughout the Naruto series.\n");
		sb.append("8. `cg` - A list of Clans that have Kekkei Genkai\n");
		sb.append("9. `rn` - For each nature types, find the team with the highest ratio of members of that nature type\n");
		sb.append("10. `mpc` - Top 3 most popular characters base on the number of media types they debut in\n");
		sb.append("11. `ma` - Ranking villages by the number of voice actors associated with characters inside that village\n");

		System.out.println(sb.toString());
	}

	public void averageAgeClan() throws SQLException{
		String sql = """
				SELECT Clans.clanName, ROUND(AVG(Characters.age),2) AS averageAge
				FROM Characters
				JOIN Clans ON Characters.clanId = Clans.clanId
				GROUP BY Clans.clanName
				ORDER BY averageAge ASC;
				""";
		PreparedStatement statement = connection.prepareStatement(sql);

		// Execute query
		// FOR EACH resultSet, we will print out the character id
		// resultSet is variable name, this variable is ALWAYS of type ResultSet, determined by paramter type "Consumer<ResultSet>"
		this.executeQuery(statement, (resultSet) -> {
			try {
				String clanName = resultSet.getString("clanName");
				if (clanName == null)
					clanName = "Not enough data to calculate average age";
				float age = resultSet.getFloat("averageAge");
				System.out.printf("Clan name: %s, Average age: %f\n", clanName, age);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void jutsuByRank(String rank) throws SQLException{
		String sql = """
			SELECT Characters.characterName, Villages.villageName, Ranks.rankName, Jutsu.jutsuName 
			FROM Characters 
			INNER JOIN CharactersHaveRanks ON Characters.characterId = CharactersHaveRanks.characterId 
			INNER JOIN Ranks ON CharactersHaveRanks.rankId = Ranks.rankId
			INNER JOIN Villages ON Characters.villageId = Villages.villageId INNER JOIN CharactersHaveJutsu ON Characters.characterId = CharactersHaveJutsu.characterId 
			INNER JOIN Jutsu ON CharactersHaveJutsu.jutsuId = Jutsu.jutsuId 
			
			-- The user will be given the choice to input whichever rank they
			-- prefer to see the list of
			WHERE Ranks.rankName = ? 
			ORDER BY Villages.villageName, Characters.characterName;
				""";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, rank);

		this.executeQuery(statement, (resultSet)->{
			try {
				String charName = resultSet.getString("characterName");
				String villageName = resultSet.getString("villageName");
				String rankName = resultSet.getString("rankName");
				String jutsuName = resultSet.getString("jutsuName");
				System.out.printf("Character name: %s, from: %s, rank: %s, can use: %s\n", charName, villageName, rankName, jutsuName);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void clanGenkai() throws SQLException{
		String sql = """
			WITH
				countGenkaiUserPerClan AS (
					SELECT count(characterName) as userNum, genkaiName, clanName
					FROM Characters
					NATURAL JOIN Clans
					NATURAL JOIN CharactersHaveKekkeiGenkai
					NATURAL JOIN KekkeiGenkai
					GROUP BY genkaiName, clanName
				)
			SELECT clanName, genkaiName
			FROM countGenkaiUserPerClan
			WHERE userNum >= 2 OR genkaiName LIKE '%Clan%'
			ORDER BY clanName;
		""";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String clanName = resultSet.getString("clanName");
				String genkaiName = resultSet.getString("genkaiName");
				System.out.printf("Clan name: %s, has: %s\n", clanName, genkaiName);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void rationNature() throws SQLException{
		String sql = """
			WITH
				naturePerTeam AS (
					SELECT teamName, natureType, count(characterId) as userNum
					FROM Teams
					NATURAL JOIN TeamMembers
					NATURAL JOIN Characters
					NATURAL JOIN CharactersNatureTypes
					NATURAL JOIN NatureTypes
					GROUP BY teamName, natureType
				),
				teamMemberCount AS (
					SELECT teamName, count(characterId) as memberNum
					FROM Teams
					NATURAL JOIN TeamMembers
					GROUP BY teamName
				),
				natureRankingPerTeam AS (
					SELECT teamName, natureType, userNum, RANK() OVER (PARTITION BY teamName ORDER BY userNum DESC) AS natureRank
					FROM naturePerTeam
				)

			SELECT teamName, natureType, ROUND((userNum * 1.0) / memberNum, 2) as rate
			FROM natureRankingPerTeam
			NATURAL JOIN teamMemberCount
			WHERE natureRank = 1;
		""";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String teamName = resultSet.getString("teamName");
				String natureType = resultSet.getString("natureType");
				float rate = resultSet.getFloat("rate");
				System.out.printf("Team: %s, Nature Type: %s, Rate: %f.2\n", teamName, natureType, rate);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void mostPopularChar() throws SQLException{
		String sql = """
			-- At first we calculate the number of media types of each character debuts -- and only considering the top 10
			WITH 
				PopularCharacters AS (
					SELECT C.characterId, C.characterName, COUNT(DISTINCT CD.mediaId) AS debutCount
					FROM CharacterDebuts CD
					INNER JOIN Characters C ON CD.characterId = C.characterId
					GROUP BY C.characterId, C.characterName
					ORDER BY debutCount DESC
				),
				RankedTable AS (
					SELECT characterId, DENSE_RANK() OVER (ORDER BY debutCount DESC) AS rnk
					FROM PopularCharacters
				)
			-- The query below joins with PopularCharacters displaying relevant info
			SELECT PC.characterName, PC.debutCount, T.teamName, V.villageName, CL.clanName
			FROM PopularCharacters PC
			NATURAL JOIN RankedTable
			INNER JOIN TeamMembers TM ON PC.characterId = TM.characterId
			INNER JOIN Teams T ON TM.teamId = T.teamId
			INNER JOIN Characters C ON PC.characterId = C.characterId
			INNER JOIN Villages V ON C.villageId = V.villageId
			LEFT JOIN Clans CL ON C.clanId = CL.clanId -- if applicable
			WHERE rnk <= 3;
		""";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String charName = resultSet.getString("characterName");
				int debutCount = resultSet.getInt("debutCount");
				String teamName = resultSet.getString("teamName");
				String villageName = resultSet.getString("villageName");
				String clanName = resultSet.getString("clanName");
				if (teamName == null)
					teamName = "Not in team";
				if (villageName == null)
				villageName = "Not in village";
				if (clanName == null)
				clanName = "Not in clan";
				System.out.printf("Character: %s, Debut in: %d medias, team: %s, village: %s, clan %s\n", charName, debutCount, teamName, villageName, clanName);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void mostActors() throws SQLException{
		String sql = """
			SELECT villageName, count(actorId) AS countActor
			FROM Villages
			NATURAL JOIN Characters
			NATURAL JOIN VoiceActorsActCharacters
			GROUP BY villageName
			ORDER BY countActor DESC;
		""";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String villageName = resultSet.getString("villageName");
				int countActor = resultSet.getInt("countActor");
				System.out.printf("Village name: %s, number of actors: %d\n", villageName, countActor);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void uniqueTrait(String trait) throws SQLException{
		String sql = """
					SELECT Characters.characterName, Teams.teamName, CharactersUniqueTraits.trait
					FROM Characters
					INNER JOIN CharactersUniqueTraits ON Characters.characterId = CharactersUniqueTraits.characterId
					LEFT JOIN TeamMembers ON Characters.characterId = TeamMembers.characterId
					LEFT JOIN Teams ON TeamMembers.teamId = Teams.teamId
					WHERE CharactersUniqueTraits.trait LIKE ?;
				""";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + trait + "%");

		this.executeQuery(statement, (resultSet)->{
			try {
				String charName = resultSet.getString("characterName");
				String teamName = resultSet.getString("teamName");
				String traitName = resultSet.getString("trait");
				if (teamName == null)
					teamName = "Not in team";
				System.out.printf("Character: %s, Team: %s, Trait: %s\n", charName, teamName, traitName);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void individualKek() throws SQLException{
		String sql = """
				WITH
					UserCount AS (
						SELECT genkaiId, COUNT(genkaiName) AS count
						FROM Characters
						NATURAL JOIN CharactersHaveKekkeiGenkai
						NATURAL JOIN KekkeiGenkai
						GROUP BY genkaiId
					)
				
				SELECT characterName, genkaiName
				FROM Characters
				NATURAL JOIN CharactersHaveKekkeiGenkai
				NATURAL JOIN KekkeiGenkai
				NATURAL JOIN UserCount
				WHERE count = 1;
				""";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String charName = resultSet.getString("characterName");
				String genkaiName = resultSet.getString("genkaiName");
				System.out.printf("Character: %s, KekkeiGenkai: %s\n", charName, genkaiName);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void teamChange() throws SQLException{
		String sql = """
					WITH TeamChanges AS (
						SELECT characterId,COUNT(DISTINCT teamId) AS teamCount
						FROM TeamMembers
						GROUP BY characterId
						HAVING COUNT(DISTINCT teamId) > 1
					)
					SELECT C.characterId, C.characterName, TC.teamCount
					FROM Characters C
					INNER JOIN TeamChanges TC ON C.characterId = TC.characterId;
				""";;
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				String charName = resultSet.getString("characterName");
				String count = resultSet.getString("teamCount");
				System.out.printf("Character: %s, Number of team: %d\n", charName, count);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void test() throws SQLException{
		String sql = "SELECT * FROM Jutsu";
		PreparedStatement statement = connection.prepareStatement(sql);

		this.executeQuery(statement, (resultSet)->{
			try {
				System.out.println("id: "+resultSet.getInt("jutsuId") + " name: " + resultSet.getString("jutsuName"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}


    // Example
    // 'connection' is from super class
    // 'this.executeQuery' is also from the super class
	public void exampleQuery() throws SQLException{
		String sql = "SELECT ? FROM table";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "characterID");

		// Execute query
		// FOR EACH resultSet, we will print out the character id
		// resultSet is variable name, this variable is ALWAYS of type ResultSet, determined by paramter type "Consumer<ResultSet>"
		this.executeQuery(statement, (resultSet) -> {
			try {
				System.out.println(resultSet.getString("characterID"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}