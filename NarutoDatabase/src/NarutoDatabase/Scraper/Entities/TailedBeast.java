package NarutoDatabase.Scraper.Entities;

import java.util.ArrayList;

// Tailed beast and character are basically the same
public class TailedBeast extends Character {

	public ArrayList<Character> jinchuriki = new ArrayList<>();

	public TailedBeast(int id, String name) {
		super(id, name);
	}
}
