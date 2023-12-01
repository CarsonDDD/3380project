package NarutoDatabase.Scraper.Entities;
import java.util.*;

public class Character {
	int id;
	String name;
	//Other information
	public ArrayList<Jutsu> jutsu = new ArrayList<>();

	public Character(int id, String name){
		this.id = id;
		this.name = name;
	}
}
