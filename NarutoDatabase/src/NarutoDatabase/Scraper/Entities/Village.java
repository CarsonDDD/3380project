package NarutoDatabase.Scraper.Entities;

import java.util.*;

public class Village {
	int id;
	String name;
	public ArrayList<Character> characters = new ArrayList();

	public Village(int id, String name){
		this.id = id;
		this.name = name;
	}
}
