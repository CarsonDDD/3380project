package Entities;

public class Media {
	private static int numMedias = 0;
	int id;
	/*String novel;
	String movie;
	String anime;
	String game;
	String ova;*/

	String type;
	String name;

	public Media(String type, String name) {
		this.type = type;
		this.name = name;

		id = numMedias++;
	}
}
