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
    public void convertVillages(){
        for (Map.Entry<Integer, Village> entry : Village.entrySet()){
            Integer key = entry.getKey();
            Village village = entry.getValue();

            // technically we can use key here
            //generateInsert("Villages", new String[]{village.id +"",village.name});
            Output.log(generateInsert("Villages", new String[]{village.id +"", "\""+village.name + "\""}), "Villages.txt");
        }
    }

    public void convertJutsu(){
        for (Map.Entry<String, Jutsu> entry : Jutsu.entrySet()){
            String key = entry.getKey();
            Jutsu jutsu = entry.getValue();

            Output.log(generateInsert(
                    "Jutsu", new String[]{jutsu.id +"", "\""+jutsu.name + "\""}),
                    "Jutsu.txt");
        }
    }

    public void convertVoiceActors(){
        for (Map.Entry<String, VoiceActor> entry : VoiceActor.entrySet()){
            String key = entry.getKey();
            VoiceActor actor = entry.getValue();

            Output.log(generateInsert(
                            "VoiceActors", new String[]{actor.id +"", "\""+actor.name + "\"", "\""+actor.language + "\""}),
                    "Actors.txt");
        }
    }

    public void convertMedia(){
        for (Map.Entry<String, Media> entry : Media.entrySet()){
            String key = entry.getKey();
            Media media = entry.getValue();

            Output.log(generateInsert(
                            "Jutsu", new String[]{media.id +"", "\""+media.name + "\"", "\""+media.type + "\""}),
                    "Media.txt");
        }
    }

    public void convertKekkeiGenkai(){
        for (Map.Entry<Integer, KekkeiGenkai> entry : KekkeiGenkai.entrySet()){
            Integer key = entry.getKey();
            KekkeiGenkai kekkei = entry.getValue();

            Output.log(generateInsert(
                            "Jutsu", new String[]{kekkei.id +"", "\""+kekkei.name + "\""}),
                    "Media.txt");
        }
    }

    public void convertCharacter(){
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


            // Tool is many to many DONE BELOW
            // TODO: beast?!?!?!?!?!!?!?!?!?! is this jikugienuri?
            // Media is many to many DONE BELOW

            Output.log(generateInsert(
                            "Characters", new String[]{id +"", "\""+name + "\"", villageId+"", clanId+""}),
                    "Characters.txt");
        }
    }

    // NatureType-Character is many to many
    public void createCharacterNatureTypes(){
        Output.log("\n\n#NatureTypes-Characters Many to Many","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(NatureType type : character.natureTypes){
                Output.log(generateInsert("CharactersNatureTypes", new String[]{type.id+"",characterId+""})+" #"+type.type+"—"+character.name, "Characters.txt");
            }
        }
    }

    // Tools-Character is many to many
    public void createCharacterTools(){
        Output.log("\n\n#Tools-Characters Many to Many","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(Tool tool : character.tools){
                Output.log(generateInsert("CharactersTools", new String[]{characterId+"",tool.id+""})+" #"+tool.name+"—"+character.name, "Characters.txt");
            }
        }
    }

    // Debut-Character is many to many
    public void createCharacterDebuts(){
        Output.log("\n\n#Debut-Characters Many to Many","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(Media media : character.debuts){
                Output.log(generateInsert("CharactersDebuts", new String[]{characterId+"",media.id+""})+" #"+character.name+"—"+media.name, "Characters.txt");
            }
        }
    }

    // VoiceActor-Character is many to many
    public void createCharacterVoiceActors(){
        Output.log("\n\n#Actors-Characters Many to Many","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(VoiceActor actor : character.voiceActors){
                Output.log(generateInsert("CharactersActors", new String[]{characterId+"",actor.id+""})+" #"+character.name+"—"+actor.name+":"+actor.language, "Characters.txt");
            }
        }
    }

    // UniqueTraits-Character is many to one
    public void createCharacterUniqueTraits(){
        Output.log("\n\n#UniqueTraits-Characters Many to One","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();

            for(UniqueTrait trait : character.traits){
                Output.log(generateInsert("CharactersUniqueTraits", new String[]{"\""+trait.trait+"\"", characterId+""})+" #"+character.name, "Characters.txt");
            }
        }
    }

    // Personal-Character is many to many?
    public void createCharacterPersonal(){
        /*Output.log("\n\n#Personal-Characters Many to Many","Characters.txt");
        for (Map.Entry<Integer, Character> entry : Character.entrySet()) {
            Integer characterId = entry.getKey();
            Character character = entry.getValue();
            Output.log("\n#Personal-Characters for: " + character.name,"Characters.txt");

            for(Personal personal : character.personals){
                Output.log(generateInsert("CharactersPersonal", new String[]{characterId+"",personal.id+""})+" #"+personal., "Characters.txt");
            }
        }*/
    }
}
