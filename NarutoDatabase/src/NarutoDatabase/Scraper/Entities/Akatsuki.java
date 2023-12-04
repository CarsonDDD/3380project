package NarutoDatabase.Scraper.Entities;

import NarutoDatabase.Scraper.Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;

public class Akatsuki implements IHasCharacters {
    int id;
    String name;
    private ArrayList<Character> characters = new ArrayList<Character>();

    public Akatsuki(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public void addCharacter(Character newCharacter) {
        characters.add(newCharacter);
    }
}
