package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Clan implements IHasCharacters {
	private static HashMap<Integer, Clan> clansAll = new HashMap<>();
	public int id;
	public String name;

	private final ArrayList<Character> characters = new ArrayList<>(1500);

	public Clan(int id, String name){
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

	public static Clan get(int clanId){
		return clansAll.get(clanId);
	}

	public static Clan put(int clanId, Clan clan){
		return clansAll.put(clanId, clan);
	}

	public static Set<Map.Entry<Integer, Clan>> entrySet(){
		return clansAll.entrySet();
	}
}
