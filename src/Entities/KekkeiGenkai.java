package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;

public class KekkeiGenkai implements IHasCharacters {
	public String name;
	int id;

	private final ArrayList<Character> characters = new ArrayList<>(1500);

	public KekkeiGenkai(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addCharacter(Character newCharacter) {
		characters.add(newCharacter);
	}
}
