package Entities;

import Entities.Interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Tailed beast and character are basically the same
public class TailedBeast implements IHasCharacters{
	private static HashMap<Integer, TailedBeast> tailedBeastsAll = new HashMap<>();
	public ArrayList<Character> jinchuriki = new ArrayList<>();
	public int id;
	public String name;

	public TailedBeast(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static TailedBeast get(Integer id) {
		return tailedBeastsAll.get(id);
	}

	public static void put(Integer id, TailedBeast tailedBeast) {
		tailedBeastsAll.put(id, tailedBeast);
	}

	@Override
	public void addCharacter(Character newCharacter) {
		jinchuriki.add(newCharacter);
	}

	@Override
	public boolean containsCharacter(Character character) {
		return jinchuriki.contains(character);
	}

	public static Set<Map.Entry<Integer, TailedBeast>> entrySet(){
		return tailedBeastsAll.entrySet();
	}

}
