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

		// https://narutodb.xyz/api/character/55 RETURNS PARENT OBJECT, THEN ARRAY
		//BufferedReader reader = scraper.fetchData("https://www.narutodb.xyz/api/character");

		JSONArray characters = scraper.getJsonArray("https://www.narutodb.xyz/api/character", "characters");

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
}
