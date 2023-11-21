package Scraper;

import java.io.BufferedReader;
import java.io.IOException;

public class Program {

	public static void main(String[] args) throws IOException {
		NarutoScraper scraper = new NarutoScraper();
		
		BufferedReader reader = scraper.fetchData("https://narutodb.xyz/api/character");

		String line;
		for(int i =0; (line = reader.readLine()) != null; i++){
			System.out.println(i + ": " + line);
		}
	}
}
