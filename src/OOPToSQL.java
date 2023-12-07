import Entities.*;

import java.util.Map;

import Entities.Character;
import Logger.Output;

public class OOPToSQL {

    // Start with basic data tables and connections/relationships later
    // we may need to prestart with generating int ids for our added values (since they mostly have string ids rn)
    private String generateInsert(String table, String[] values){
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ").append(table).append(" VALUES(");

        for(int i =0; i < values.length; i++){
            sb.append(values[i]);

            if(i != values.length-1){
                sb.append(",");
            }
        }
        sb.append(");");

        return sb.toString();
    }


    // Main single entities.
    public void convertVillages(String outputFile){
        Output.log("\n# Villages(villageId, villageName)",outputFile);
        for (Map.Entry<Integer, Village> entry : Village.entrySet()){
            Integer key = entry.getKey();
            Village village = entry.getValue();

            // technically we can use key here
            //generateInsert("Villages", new String[]{village.id +"",village.name});
            Output.log(generateInsert("Villages", new String[]{village.id +"", "\""+village.name + "\""}), outputFile);
        }
    }

    public void convertJutsu(String outputFile){
        Output.log("\n# Jutsu(jutsuId, justuName, classification, range, rank, nature, mediaId) needs personal",outputFile);
        for (Map.Entry<String, Jutsu> entry : Jutsu.entrySet()){
            Jutsu jutsu = entry.getValue();

            Output.log(generateInsert("Jutsu", new String[]{jutsu.id +"", "\""+jutsu.name + "\""}), outputFile);
        }
    }

    public void convertVoiceActors(String outputFile){
        Output.log("\n# VoiceActors(actorId, actorName, language)",outputFile);
        for (Map.Entry<String, VoiceActor> entry : VoiceActor.entrySet()){
            String key = entry.getKey();
            VoiceActor actor = entry.getValue();

            Output.log(generateInsert(
                            "VoiceActors", new String[]{actor.id +"", "\""+actor.name + "\"", "\""+actor.language + "\""}),
                    outputFile);
        }
    }

    public void convertTools(String outputFile){
        Output.log("\n# Tools(toolId, toolName)",outputFile);
        for (Map.Entry<String, Tool> entry : Tool.entrySet()){
            Tool tool = entry.getValue();

            Output.log(generateInsert(
                            "VoiceActors", new String[]{tool.id +"", "\""+tool.name + "\""}),
                    outputFile);
        }
    }

    public void convertMedia(String outputFile){
        Output.log("\n# Medias(mediaId, mediaName, mediaType)",outputFile);
        for (Map.Entry<String, Media> entry : Media.entrySet()){
            String key = entry.getKey();
            Media media = entry.getValue();

            Output.log(generateInsert(
                            "Jutsu", new String[]{media.id +"", "\""+media.name + "\"", "\""+media.type + "\""}),
                    outputFile);
        }
    }

    public void convertKekkeiGenkai(String outputFile){
        Output.log("\n# KekkeiGenkai(genkaiId, genkaiName)",outputFile);
        for (Map.Entry<Integer, KekkeiGenkai> entry : KekkeiGenkai.entrySet()){
            Integer key = entry.getKey();
            KekkeiGenkai kekkei = entry.getValue();

            Output.log(generateInsert(
                            "Jutsu", new String[]{kekkei.id +"", "\""+kekkei.name + "\""}),
                    outputFile);
        }
    }

    // Kekki-Character. This is a copy-paste from above, however lets just separate it out for clarity
    public void createKekkeiGenkaiCharacter(String outputFile){
        Output.log("\n# CharactersHaveKekkeiGenkai(characterId, genkaiId)",outputFile);
        for (Map.Entry<Integer, KekkeiGenkai> entry : KekkeiGenkai.entrySet()){
            KekkeiGenkai kekkei = entry.getValue();

            for(Character character : kekkei.characters){
                Output.log(generateInsert("CharactersHaveKekkeiGenkai", new String[]{character.id + "", kekkei.id + ""}) + "#"+character.name +"---"+kekkei.name , outputFile);
            }
        }
    }

    public void convertCharacter(String outputFile){
        Output.log("\n# Characters(characterId, characterName, villageId, clanId, personalId, beastId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()){
            Integer key = entry.getKey();
            Character character = entry.getValue();

            int id = key;// redundant
            String name = character.name;

            //int villageID //= Village.get();
            // Get villageID
            String villageId = "null";
            for (Map.Entry<Integer, Village> villageEntry : Village.entrySet()){
                Village village = villageEntry.getValue();
                if(village.containsCharacter(character)){
                    villageId = village.id+"";
                    break;
                }
            }

            // clanID
            String clanId = "null";
            for (Map.Entry<Integer, Clan> clanEntry : Clan.entrySet()){
                Clan clan = clanEntry.getValue();
                if(clan.containsCharacter(character)){
                    clanId = clan.id+"";
                    break;
                }
            }

            // PersonalID TODO: FIX/ CHANGE PERSONAL
            // possible many to many TODO:
            int personalId = -999;
            // TODO: beast?!?!?!?!?!!?!?!?!?! is this jikugienuri?
            // TODO: TailedBeast, Personal (for real), Rank, Occupation, Classifications

            Output.log(generateInsert("Characters", new String[]{id +"", "\""+name + "\"", villageId+"", clanId+""}), outputFile);
        }
    }

    // Rank is many to many
    // Current only handles character
    public void convertRank(String outputFile){
        Output.log("\n# Rank(rankId, period, rankName)",outputFile);
        for (Map.Entry<String, Rank> ranks : Rank.entrySet()) {
            Rank rank = ranks.getValue();

            String rankId = rank.id +"";
            String rankPeriod = "'" + rank.period + "'";;
            String rankName = "'" + rank.name + "'";
            Output.log(generateInsert("Ranks", new String[]{rankId,rankPeriod,rankName})+" #"+rank.name, outputFile);
        }
    }

    // Occupation is many to many
    public void convertOccupation(String outputFile){
        Output.log("\n# Occupation(occupationId, occupationName)",outputFile);
        for (Map.Entry<String, Occupation> occupations : Occupation.entrySet()) {
            Occupation occupation = occupations.getValue();

            String ocId = occupation.id +"";
            String ocName = "'" + occupation.occupation + "'";
            Output.log(generateInsert("Occupations", new String[]{ocId,ocName}), outputFile);
        }
    }

    // Classification is many to many
    public void convertClassification(String outputFile){
        Output.log("\n# Classifications(classificationId, classificationName)",outputFile);
        for (Map.Entry<String, Classification> classifications : Classification.entrySet()) {
            Classification classification = classifications.getValue();

            String cId = classification.id +"";
            String cName = "'" + classification.classification + "'";
            Output.log(generateInsert("Classifications", new String[]{cId,cName}), outputFile);
        }
    }

    // NatureType-Character is many to many
    public void createCharacterNatureTypes(String outputFile){
        Output.log("\n# CharactersNatureTypes(natureType, characterId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(NatureType type : character.natureTypes){
                Output.log(generateInsert("CharactersNatureTypes", new String[]{type.id+"",characterId+""})+" #"+type.type+"—"+character.name, outputFile);
            }
        }
    }

    public void createCharacterJutsu(String outputFile){
        Output.log("\n# CharactersHaveJutsu(characterId, jutsuId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Character character = entry.getValue();

            for(Jutsu jutsu : character.jutsus){
                Output.log(generateInsert("CharactersHaveJutsu", new String[]{character.id+"",jutsu.id+"" })+" #"+character.name+"—"+jutsu.name, outputFile);
            }
        }
    }

    // Tools-Character is many to many
    public void createCharacterTools(String outputFile){
        Output.log("\n# CharacterTools(characterId, toolId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(Tool tool : character.tools){
                Output.log(generateInsert("CharactersTools", new String[]{characterId+"",tool.id+""})+" #"+tool.name+"—"+character.name, outputFile);
            }
        }
    }

    // Debut-Character is many to many
    public void createCharacterDebuts(String outputFile){
        Output.log("\n# CharacterDebuts(mediaId, characterId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Character character = entry.getValue();

            for(Media media : character.debuts){
                Output.log(generateInsert("CharactersDebuts", new String[]{media.id+"",character.id+""})+" #"+media.name+"—"+character.name, outputFile);
            }
        }
    }

    // VoiceActor-Character is many to many
    public void createCharacterVoiceActors(String outputFile){
        Output.log("\n# VoiceActorsCharacters(characterId, actorId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(VoiceActor actor : character.voiceActors){
                Output.log(generateInsert("VoiceActorsCharacters", new String[]{characterId+"",actor.id+""})+" #"+character.name+"—"+actor.name+":"+actor.language, outputFile);
            }
        }
    }

    // UniqueTraits-Character is many to one
    public void createCharacterUniqueTraits(String outputFile){
        Output.log("\n# CharactersUniqueTraits(trait, characterId)",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(UniqueTrait trait : character.traits){
                Output.log(generateInsert("CharactersUniqueTraits", new String[]{"\""+trait.trait+"\"", characterId+""})+" #"+character.name, outputFile);
            }
        }
    }

    public void convertClan(String outputFile){
        Output.log("\n# Clans(clanId, clanName)",outputFile);
        for (Map.Entry<Integer, Clan> entry : Clan.entrySet()){
            Clan clan = entry.getValue();

            Output.log(generateInsert("Clans", new String[]{clan.id +"", "\""+clan.name + "\""}),outputFile);
        }
    }

    public void convertTeam(String outputFile){
        Output.log("\n# Teams(teamId, teamName)",outputFile);
        for (Map.Entry<Integer, Team> entry : Team.entrySet()){
            Team team = entry.getValue();

            Output.log(generateInsert("Teams", new String[]{team.id +"", "\""+team.name + "\""}),outputFile);
        }
    }

    public void convertAkatsuki(String outputFile){
        Output.log("\n# Akatsuki as Teams, Teams(teamId, teamName)",outputFile);
        Akatsuki akatsuki = Akatsuki.instance;
        Output.log(generateInsert("Teams", new String[]{akatsuki.ID +"", "\""+akatsuki.NAME + "\""}),outputFile);

        // Many to many table
        for(Character character : akatsuki.characters){
            Output.log(generateInsert("TeamMembers", new String[]{character.id +"", "\""+akatsuki.ID + "\""})+"#"+character.name + "---" + akatsuki.NAME,outputFile);
        }
    }

    public void convertKara(String outputFile){
        Output.log("\n# Kara as Teams, Teams(teamId, teamName)",outputFile);
        Kara kara = Kara.instance;
        Output.log(generateInsert("Teams", new String[]{kara.ID +"", "\""+kara.NAME + "\""}),outputFile);

        // Many to many table
        for(Character character : kara.characters){
            Output.log(generateInsert("TeamMembers", new String[]{character.id +"", "\""+kara.ID + "\""})+"#"+character.name + "---" + kara.NAME,outputFile);
        }
    }

    public void createTeamMembers(String outputFile){
        Output.log("\n# TeamMembers(characterId, teamId)",outputFile);
        for (Map.Entry<Integer, Team> entry : Team.entrySet()){
            Team team = entry.getValue();
            Output.log("\n#"+team.name,outputFile);
            for(Character memeber : team.characters){
                Output.log(generateInsert("TeamMembers", new String[]{memeber.id +"", "\""+team.id + "\""})+"#"+memeber.name + "---" + team.name,outputFile);
            }
        }
    }

    // Personal-Character is many to many?
    public void createCharacterPersonal(){
        /*Output.log("\n# \n#Personal-Characters Many to Many",outputFile);
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();
            Output.log("\n# #Personal-Characters for: " + character.name,outputFile);

            for(Personal personal : character.personals){
                Output.log(generateInsert("CharactersPersonal", new String[]{characterId+"",personal.id+""})+" #"+personal., outputFile);
            }
        }*/
    }
}
