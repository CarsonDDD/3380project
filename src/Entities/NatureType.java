package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NatureType {
    private static HashMap<String, NatureType> natureTypeAll = new HashMap<>();
    private static int numTypes = 0;

    public String type;
    public int id;


    public NatureType(String type) {
        this.type = type;
        id = numTypes++;
    }


    public static NatureType get(String key) {
        return natureTypeAll.get(key);
    }

    public static void put(String key, NatureType natureType) {
        natureTypeAll.put(key, natureType);
    }

    public static Set<Map.Entry<String, NatureType>> entrySet(){
        return natureTypeAll.entrySet();
    }
}