package NarutoDatabase.Scraper.Entities;

import NarutoDatabase.Scraper.Entities.Interfaces.IHasCharacters;

import java.util.*;

public class Village implements IHasCharacters {
	int id;
	String name;
	private ArrayList<Character> characters = new ArrayList<Character>();

	public Village(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addCharacter(Character newCharacter) {
		characters.add(newCharacter);
	}
}
