package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;

public class Team implements IHasCharacters {
	int id;
	String name;

	private final ArrayList<Character> characters = new ArrayList<>(1500);

	public Team(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addCharacter(Character newCharacter) {
		characters.add(newCharacter);
	}
}
