package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KekkeiGenkai implements IHasCharacters {
	private static HashMap<Integer, KekkeiGenkai> kekkeiGenkaiAll = new HashMap<>();
	public String name;
	public int id;

	public final ArrayList<Character> characters = new ArrayList<>(1500);

	public KekkeiGenkai(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addCharacter(Character newCharacter) {
		characters.add(newCharacter);
	}

	@Override
	public boolean containsCharacter(Character character) {
		return characters.contains(character);
	}

	public static KekkeiGenkai get(int kekkeiId){
		return kekkeiGenkaiAll.get(kekkeiId);
	}

	public static KekkeiGenkai get(String kekkeiName){
		for (KekkeiGenkai kekkei : kekkeiGenkaiAll.values()) {
			if (kekkei.name.contains(kekkeiName)) { // may not be contains, who knows?
				return kekkei;
			}
		}
		return null;
	}

	public static KekkeiGenkai put(int kekkeiId, KekkeiGenkai kekkei){
		return kekkeiGenkaiAll.put(kekkeiId, kekkei);
	}

	public static Set<Map.Entry<Integer, KekkeiGenkai>> entrySet(){
		return kekkeiGenkaiAll.entrySet();
	}
}
