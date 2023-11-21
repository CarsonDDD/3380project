package Scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NarutoScraper {

	public BufferedReader fetchData(String endPoint) throws IOException {
		URL url = new URL(endPoint);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(true);// maigc?
		conn.setRequestMethod("GET"); // REQUEST
		conn.connect();

		int responseCode = conn.getResponseCode();

		// Check if the connection is successful
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code: " + responseCode);
		}

		/*try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line); // PROCESS LINE
			}
		}*/

		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}
}
