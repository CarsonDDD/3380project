package Entities;

import java.util.HashMap;

public class Jutsu {
	private static HashMap<String, Jutsu> jutsusAll = new HashMap<>();// key is value
	static int numJutsu =0;

	int id;
	String name;

	public Jutsu(String name){
		this.name = name;
		id = numJutsu++;
	}

	public static Jutsu get(String jutsuName){
		return jutsusAll.get(jutsuName);
	}

	public static Jutsu put(String jutsuName, Jutsu jutsu){
		return jutsusAll.put(jutsuName, jutsu);
	}
}
