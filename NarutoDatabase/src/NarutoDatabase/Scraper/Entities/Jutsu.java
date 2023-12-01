package NarutoDatabase.Scraper.Entities;

public class Jutsu {

	static int numJutsu =0;

	int id;
	String name;

	public Jutsu(String name){
		this.name = name;
		id = numJutsu++;
	}
}
