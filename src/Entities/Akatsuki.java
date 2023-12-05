package Entities;

import Entities.Interfaces.IHasCharacters;

import java.util.ArrayList;
import java.util.HashMap;

// THIS TABLE IS NOT COMPLETE
public class Akatsuki implements IHasCharacters {
    private static HashMap<Integer, Akatsuki> akatsukiAll = new HashMap<>();
    int id;
    String name;
    private final ArrayList<Character> characters = new ArrayList();

    public Akatsuki(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public void addCharacter(Character newCharacter) {
        characters.add(newCharacter);
    }

    @Override
    public boolean containsCharacter(Character character) {
        return characters.contains(character);
    }

    public static Akatsuki get(Integer id) {
        return akatsukiAll.get(id);
    }

    public static void put(Integer id, Akatsuki akatsuki) {
        akatsukiAll.put(id, akatsuki);
    }
}
