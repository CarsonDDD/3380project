package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VoiceActor {
	private static HashMap<String, VoiceActor> voiceActorsAll = new HashMap<>();
	private static int numVoiceActor = 0;
	public int id;
	public String name;
	public String language;

	public VoiceActor(String name, String language){
		this.name = name;
		this.language = language;

		id = numVoiceActor++;
	}

	public static VoiceActor get(String key) {
		return voiceActorsAll.get(key);
	}

	public static void put(String key, VoiceActor voiceActor) {
		voiceActorsAll.put(key, voiceActor);
	}

	public static Set<Map.Entry<String, VoiceActor>> entrySet(){
		return voiceActorsAll.entrySet();
	}
}