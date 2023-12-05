package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Team implements IHasCharacters {
	private static HashMap<Integer, Team> teamsAll = new HashMap<>();
	public int id;
	public String name;

	public final ArrayList<Character> characters = new ArrayList<>(1500);

	public Team(int id, String name){
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

	public static Team get(int teamId){
		return teamsAll.get(teamId);
	}

	public static Team put(int teamId, Team team){
		return teamsAll.put(teamId, team);
	}

	public static Set<Map.Entry<Integer, Team>> entrySet(){
		return teamsAll.entrySet();
	}
}
