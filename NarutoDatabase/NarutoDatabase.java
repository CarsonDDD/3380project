package NarutoDatabase;
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
			default:
				return true;
        }
    }

	public void helpCommand(){
		StringBuilder sb = new StringBuilder();

		sb.append("\n---- Help ----\n");
		sb.append("Commands:\n");
		sb.append("1. `Help` - Shows help menu\n");
		sb.append("2. `Quit` - Quits the program\n");
		sb.append("3.\n");
		sb.append("4.\n");
		sb.append("5.\n");
		sb.append("6.\n");
		sb.append("7.\n");
		sb.append("8.\n");
		sb.append("9.\n");
		sb.append("10.\n");
		sb.append("11.\n");
		sb.append("12.\n");

		System.out.println(sb.toString());
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
