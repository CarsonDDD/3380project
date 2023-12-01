package NarutoDatabase.Scraper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import NarutoDatabase.Logger.Output; // THis is not an error
import NarutoDatabase.Scraper.Entities.*;
import NarutoDatabase.Scraper.Entities.Character;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;

public class NarutoScraper {

	HashMap<Integer,Village> villages = new HashMap<>();
	HashMap<String,Jutsu> jutsus = new HashMap<>();
	HashMap<Integer,NarutoDatabase.Scraper.Entities.Character> characers = new HashMap<>();

	public static void main(String[] args) throws Exception {
		NarutoScraper scraper = new NarutoScraper();
		long startTime = System.currentTimeMillis();
		System.out.println("Starting Application...");


		// https://narutodb.xyz/api/character/55 RETURNS PARENT OBJECT, THEN ARRAY
		//BufferedReader reader = scraper.fetchData("https://www.narutodb.xyz/api/character");

		// YEs, lets load the entire database into memory first!
		//JSONArray characters = scraper.getJsonArray("https://www.narutodb.xyz/api/character", "characters");
		JSONArray clans = scraper.getJsonArray("https://www.narutodb.xyz/api/clan", "clans");
		JSONArray villages = scraper.getJsonArray("https://www.narutodb.xyz/api/village", "villages");
		//JSONArray kekkeigenkai = scraper.getJsonArray("https://www.narutodb.xyz/api/kekkei-genkai", "kekkeigenkai");
		//JSONArray tailedBeast = scraper.getJsonArray("https://www.narutodb.xyz/api/tailed-beast", "tailedBeasts");
		//JSONArray teams = scraper.getJsonArray("https://www.narutodb.xyz/api/team", "teams");
		//JSONArray akatsuki = scraper.getJsonArray("https://www.narutodb.xyz/api/akatsuki", "akatsuki");
		JSONArray kara = scraper.getJsonArray("https://www.narutodb.xyz/api/kara", "kara");

		//System.out.println("Total Characters: " + characters.size());
		/*for (Object characterObj : characters) {
			JSONObject character = (JSONObject) characterObj;
			//System.out.println(character.get("id") + ": "+ character.get("name"));
			//Debug.log(character.get("id") + ": "+ character.get("name"));
		}*/

		scraper.generateVillageSQL(villages);
		scraper.generateClanSQL(clans);


		Output.close();
		long totalTime = System.currentTimeMillis() - startTime;
		long min = totalTime / 60000;
		long sec = (totalTime % 60000) / 1000;
		//long mili = totalTime % 1000;
		System.out.println("Finished Application!");
		System.out.println("run time: " + min + "m:"+sec + "s");
	}

	public BufferedReader fetchData(String endpoint) throws IOException {
		URL url = new URL(endpoint);

		// Attempt to get data
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(true);// magic?
		conn.setRequestMethod("GET"); // REQUEST
		conn.connect();

		// Check if the connection is successful
		int responseCode = conn.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code: " + responseCode);
		}

		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}

	public JSONArray getJsonArray(String endpoint, String key) throws Exception{
		JSONArray allData = new JSONArray();
		JSONParser parser = new JSONParser();

		// Attempt to call fetch until we cannot and store the result in a single array
		int currentPage = 1;
		while(true){
			String pageURL = endpoint + "?page=" + currentPage;
			BufferedReader reader = fetchData(pageURL);

			JSONObject json = (JSONObject)parser.parse(reader);
			JSONArray pageData = (JSONArray)json.get(key);

			if (pageData == null || pageData.isEmpty()) {
				break; // Break the loop if no data is returned
			}

			allData.addAll(pageData);
			currentPage++;
		}

		return allData;
	}

	// Age, height, weight for personal.
	public void processVillageJSON(JSONArray villageData){
		// Loop through every village
		for (Object obj : villageData) {
			JSONObject villageJson = (JSONObject) obj;
			//String village = villageJson.toJSONString();
			int vId = (int)villageJson.get("id");// THIS MAY NOT WORK
			String vName = String.valueOf(villageJson.get("name"));

			Village v = new Village(vId, vName);

			JSONArray characersInVillage = (JSONArray)villageJson.get("Characters"); // This cast prob doesnt work, lets assume it does for now
			for (Object charObj : characersInVillage) {
				JSONObject charJson = (JSONObject)charObj;

				int cId = (int)charJson.get("id");
				Character c = characers.get(cId);
				if(c == null){
					String cName = String.valueOf(charJson.get("name"));
					c = new Character(cId, cName);

					// GET ALL THE OTHER CHARACTER INFORMATION
					// Justsu array
					// personal object ??!?!?!?!?!?
					// uniquite traits arary

					JSONArray justuInCharacter = (JSONArray)charJson.get("jutsu"); // This cast prob doesnt work, lets assume it does for now
					// for each
					for (Object jutsuObj : justuInCharacter) {
						JSONObject jutsuJson = (JSONObject)jutsuObj;
						String jName = String.valueOf(jutsuJson);

						Jutsu j = jutsus.get(jutsuJson);
						if(j == null){
							j = new Jutsu(jName);
						}
						c.jutsu.add(j);
					}


					JSONArray uniqueTraits = (JSONArray)charJson.get("uniqueTraits"); // This cast prob doesnt work, lets assume it does for now
					// for each
					for (Object traitObj : justuInCharacter) {
						JSONObject traitJson = (JSONObject)traitObj;
						// WHAT EVER THIS IS
					}


				}

				v.characters.add(c);

			}

			// NOW DO CHARACTER
		}
	}

	public void generateVillageSQL(JSONArray villageData){
		// take in string array
		// convert output inserts with debugger
		for (Object obj : villageData) {
			JSONObject villageJson = (JSONObject) obj;
			//String village = villageJson.toJSONString();
			String id = String.valueOf(villageJson.get("id"));
			String name = "'" + String.valueOf(villageJson.get("name")) + "'";

			// WE NEED TO FORMAT HERE
			String sql = generateInsert("Villages", new String[]{id, "'" + name + "'"});
			//String s = village.toJSONString();
			Output.log(sql, "villages.txt");
		}
	}

	public void generateClanSQL(JSONArray clanData){
		for (Object obj : clanData) {
			JSONObject clanJson = (JSONObject) obj;
			//String clan = clanJson.toJSONString();
			String id = String.valueOf(clanJson.get("id"));
			String name = "'" + String.valueOf(clanJson.get("name")) + "'";
			//String clanImage;
			//String villageId;
			//String genkaiId;

			// WE NEED TO FORMAT HERE
			String sql = generateInsert("Clans", new String[]{id, name});
			//String s = clan.toJSONString();
			Output.log(sql, "clans.txt");
		}
	}

	public void generateKaraSQL(JSONArray karaData){
		for (Object obj : karaData) {
			JSONObject karaJson = (JSONObject) obj;
			//String kara = karaJson.toJSONString();
			String id = String.valueOf(karaJson.get("id"));
			String name = "'" + String.valueOf(karaJson.get("name")) + "'";
			//String karaImage;
			//String villageId;
			//String genkaiId;

			// WE NEED TO FORMAT HERE
			String sql = generateInsert("kara", new String[]{id, name});
			//String s = kara.toJSONString();
			Output.log(sql, "kara.txt");
		}
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
