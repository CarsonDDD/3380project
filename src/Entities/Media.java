package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Media {
	private static HashMap<String, Media> mediasAll = new HashMap<>();
	private static int numMedias = 0;
	public int id;
	/*String novel;
	String movie;
	String anime;
	String game;
	String ova;*/

	public String type;
	public String name;

	public Media(String type, String name) {
		this.type = type;
		this.name = name;

		id = numMedias++;
	}

	public static Media get(String key) {
		return mediasAll.get(key);
	}

	public static void put(String key, Media media) {
		mediasAll.put(key, media);
	}

	public static Set<Map.Entry<String, Media>> entrySet(){
		return mediasAll.entrySet();
	}
}
