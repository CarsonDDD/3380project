package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.*;

public class Village implements IHasCharacters {
	private static HashMap<Integer, Village> villagesAll = new HashMap<>();
	public int id;
	public String name;
	private ArrayList<Character> characters = new ArrayList();

	public Village(int id, String name){
		this.id = id;
		this.name = name;
	}



	@Override
	public void addCharacter(Character newCharacter) {
		characters.add(newCharacter);
	}

	@Override
	public boolean containsCharacter(Character character){
		return characters.contains(character);
	}

	public static Village get(Integer id) {
		return villagesAll.get(id);
	}

	public static void put(Integer id, Village village) {
		villagesAll.put(id, village);
	}

	public static Set<Map.Entry<Integer, Village>> entrySet(){
		return villagesAll.entrySet();
	}
}
