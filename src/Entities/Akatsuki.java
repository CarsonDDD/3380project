package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;
import java.util.HashMap;

// THIS TABLE IS NOT COMPLETE. THERE IS ONLY ONE AKATSUKI!?!?!?!?!?!?!?!?!?!?!
public class Akatsuki implements IHasCharacters {
    public static Akatsuki instance;
    public final int ID = 420690;
    public final String NAME = "Akatsuki";
    //public int id;
    public final ArrayList<Character> characters = new ArrayList();


    public Akatsuki(){
    }

    @Override
    public void addCharacter(Character newCharacter) {
        characters.add(newCharacter);
    }

    @Override
    public boolean containsCharacter(Character character) {
        return characters.contains(character);
    }
}
