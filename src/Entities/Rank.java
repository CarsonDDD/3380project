package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rank {
	private static HashMap<String, Rank> ranksAll = new HashMap<>();
	private static int numRanks = 0;

	public int id;
	public String period;
	public String name;

	public Rank(String period, String name) {
		this.period = period;
		this.name = name;

		id = numRanks++;
	}

	public static Rank get(String key) {
		return ranksAll.get(key);
	}

	public static void put(String key, Rank rank) {
		ranksAll.put(key, rank);
	}

	public static Set<Map.Entry<String, Rank>> entrySet(){
		return ranksAll.entrySet();
	}
}
