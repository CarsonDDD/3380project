package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Occupation {
    private static HashMap<String, Occupation> occupationAll = new HashMap<>();
    private static int numOccupations = 0;

    public int id;

    public String occupation;

    public Occupation(String occupation){
        this.occupation = occupation;
        id = numOccupations++;
    }

    public static Occupation get(String key) {
        return occupationAll.get(key);
    }

    public static void put(String key, Occupation newOccupation) {
        occupationAll.put(key, newOccupation);
    }

    public static Set<Map.Entry<String, Occupation>> entrySet(){
        return occupationAll.entrySet();
    }
}
