import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Entities.*;

import Entities.Character;
import Entities.Interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;

public class NarutoScraper {

    public static void main(String[] args) throws Exception {
        JsonToOOP scraper = new JsonToOOP();
        long startTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        System.out.println("Loading JSON...");

        System.out.println("\nProcessing Character JSON");
        currentTime = System.currentTimeMillis();
        JSONArray characters = scraper.getJsonArray("https://www.narutodb.xyz/api/character", "characters");
        scraper.processCharacterJson(characters);
        System.out.println("Finished Characters--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing Clan JSON");
        currentTime = System.currentTimeMillis();
        JSONArray clans = scraper.getJsonArray("https://www.narutodb.xyz/api/clan", "clans");
        scraper.processClanJson(clans);
        System.out.println("Finished Clans--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing Kekkeigenkai JSON");
        currentTime = System.currentTimeMillis();
        JSONArray kekkeigenkai = scraper.getJsonArray("https://www.narutodb.xyz/api/kekkei-genkai", "kekkeigenkai");
        scraper.processKekkiGenkaiJson(kekkeigenkai);
        System.out.println("Finished Kekkeigenkai--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing Teams JSON");
        currentTime = System.currentTimeMillis();
        JSONArray teams = scraper.getJsonArray("https://www.narutodb.xyz/api/team", "teams");
        scraper.processTeamJson(teams);
        System.out.println("Finished Teams--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing Village JSON");
        currentTime = System.currentTimeMillis();
        JSONArray villages = scraper.getJsonArray("https://www.narutodb.xyz/api/village", "villages");
        scraper.processVillageJson(villages);
        System.out.println("Finished Villages--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing TailedBeast JSON");
        currentTime = System.currentTimeMillis();
        JSONArray tailedBeast = scraper.getJsonArray("https://www.narutodb.xyz/api/tailed-beast", "tailedBeasts");
        scraper.processTailedBeastJson(tailedBeast);
        System.out.println("Finished TailedBeasts--" + getElapsedTime(currentTime));


        System.out.println("\nProcessing Kara JSON");
        currentTime = System.currentTimeMillis();
        JSONArray kara = scraper.getJsonArray("https://www.narutodb.xyz/api/kara", "kara");
        scraper.processKaraJson(kara);
        System.out.println("Finished Kara--" + getElapsedTime(currentTime));

        System.out.println("\nProcessing Akatsuki JSON");
        currentTime = System.currentTimeMillis();
        JSONArray akatsuki = scraper.getJsonArray("https://www.narutodb.xyz/api/akatsuki", "akatsuki");
        scraper.processAkatsukiJson(akatsuki);
        System.out.println("Finished Kara--" + getElapsedTime(currentTime));

        //Output.close();
        System.out.println("\nFinished LoadingJson!");
        System.out.println("Total time: " + getElapsedTime(startTime));


        // ---------------------OOP TO SQL--------------------------------

        OOPToSQL converter = new OOPToSQL();
        System.out.println("\nConverting to SQL...");
        currentTime = System.currentTimeMillis();

        // Character
        System.out.println("\nCreating Character SQL");
        currentTime = System.currentTimeMillis();
        converter.convertCharacter("Character.txt");
        converter.createCharacterNatureTypes("Character.txt");
        converter.createCharacterTools("Character.txt");
        converter.createCharacterDebuts("Character.txt");
        converter.createCharacterVoiceActors("Character.txt");
        converter.createCharacterUniqueTraits("Character.txt");
        converter.createKekkeiGenkaiCharacter("Character.txt");
        converter.createCharacterJutsu("Character.txt");
        // UNIQUE TRAIT INFO
        System.out.println("Finished creating Character SQL--" + getElapsedTime(currentTime));

        //TOOLS
        System.out.println("\nCreating Tools SQL");
        currentTime = System.currentTimeMillis();
        converter.convertTools("Non-specific.txt");
        System.out.println("Finished creating Tool SQL--" + getElapsedTime(currentTime));

        // MEDIA
        System.out.println("\nCreating Media SQL");
        currentTime = System.currentTimeMillis();
        converter.convertMedia("Non-specific.txt");
        System.out.println("Finished creating Media SQL--" + getElapsedTime(currentTime));


        System.out.println("\nCreating Village SQL");
        currentTime = System.currentTimeMillis();
        converter.convertVillages("Non-specific.txt");
        System.out.println("Finished creating Village SQL--" + getElapsedTime(currentTime));

        System.out.println("\nCreating Jutsu SQL");
        currentTime = System.currentTimeMillis();
        converter.convertJutsu("Non-specific.txt");
        System.out.println("Finished creating Jutsu SQL--" + getElapsedTime(currentTime));

        System.out.println("\nCreating Voice Actor SQL");
        currentTime = System.currentTimeMillis();
        converter.convertVoiceActors("Non-specific.txt");
        System.out.println("Finished creating Voice SQL--" + getElapsedTime(currentTime));

        System.out.println("\nCreating KekkeiGenkai SQL");
        currentTime = System.currentTimeMillis();
        converter.convertKekkeiGenkai("Non-specific.txt");
        System.out.println("Finished creating KekkeiGenkai SQL--" + getElapsedTime(currentTime));

        System.out.println("\nCreating Clan SQL");
        currentTime = System.currentTimeMillis();
        converter.convertClan("Non-specific.txt");
        System.out.println("Finished creating Clan SQL--" + getElapsedTime(currentTime));

        System.out.println("\nCreating Team SQL");
        currentTime = System.currentTimeMillis();
        converter.convertTeam("Non-specific.txt");
        converter.createTeamMembers("Non-specific.txt");
        System.out.println("Finished creating Team SQL--" + getElapsedTime(currentTime));


        System.out.println("\nFinished converting!");
        System.out.println("Total time: " + getElapsedTime(startTime));
    }

    public static String getElapsedTime(long startTime){
        long totalTime = System.currentTimeMillis() - startTime;
        long min = totalTime / 60000;
        long sec = (totalTime % 60000) / 1000;
        long millis = totalTime % 1000;
        return min + "m:" + sec + "s:" + millis + "ms";
    }

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
}
