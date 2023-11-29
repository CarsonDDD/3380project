package NarutoDatabase.Scraper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import NarutoDatabase.Logger.Debug;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NarutoScraper {

	public static void main(String[] args) throws Exception {
		NarutoScraper scraper = new NarutoScraper();
		Debug.instance();// remove later

		// https://narutodb.xyz/api/character/55 RETURNS PARENT OBJECT, THEN ARRAY
		//BufferedReader reader = scraper.fetchData("https://www.narutodb.xyz/api/character");

		// YEs, lets load the entire database into memory first!
		JSONArray characters = scraper.getJsonArray("https://www.narutodb.xyz/api/character", "characters");
		JSONArray clans = scraper.getJsonArray("https://www.narutodb.xyz/api/clan", "clans");
		JSONArray villages = scraper.getJsonArray("https://www.narutodb.xyz/api/village", "villages");
		JSONArray kekkeigenkai = scraper.getJsonArray("https://www.narutodb.xyz/api/kekkei-genkai", "kekkeigenkai");
		JSONArray tailedBeast = scraper.getJsonArray("https://www.narutodb.xyz/api/tailed-beast", "tailedBeasts");
		JSONArray teams = scraper.getJsonArray("https://www.narutodb.xyz/api/team", "teams");
		JSONArray akatsuki = scraper.getJsonArray("https://www.narutodb.xyz/api/akatsuki", "akatsuki");
		JSONArray kara = scraper.getJsonArray("https://www.narutodb.xyz/api/kara", "kara");

		System.out.println("Total Characters: " + characters.size());
		for (Object characterObj : characters) {
			JSONObject character = (JSONObject) characterObj;
			//System.out.println(character.get("id") + ": "+ character.get("name"));
			//Debug.log(character.get("id") + ": "+ character.get("name"));
		}

		Debug.close();
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

	public void generateVillageSQL(JSONArray villageData){
		// take in string array
		// convert output inserts with debugger
		for (Object characterObj : villageData) {
			JSONObject village = (JSONObject) characterObj;
			String s = getInsert("Villages", new String[]{(String)village.get("id"), (String)village.get("name")});

			Debug.log(s, "villages.txt");
		}
	}

	private String getInsert(String table, String[] values){
		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO ").append(table).append(" VALUES(");

		for(int i =0; i < values.length; i++){
			sb.append(values[i]);

			if(i != values.length){
				sb.append(",");
			}
		}

		for (String value : values) {
			sb.append(value).append(",");
		}

		sb.append(");");
		
		return sb.toString();
	}
}
