package Entities;

public class VoiceActor {
	private static int numVoiceActor = 0;
	int id;
	String name;
	String language;

	public VoiceActor(String name, String language){
		this.name = name;
		this.language = language;

		id = numVoiceActor++;
	}
}