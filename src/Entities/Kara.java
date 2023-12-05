package Entities;

import Entities.Interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Kara implements IHasCharacters {
    public static Kara instance;
    public final int ID = 696969;
    public final String NAME = "Kara";
    //public int id;
    public final ArrayList<Character> characters = new ArrayList();

    public Kara(){
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
