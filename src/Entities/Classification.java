package Entities;

import Entities.Interfaces.IHasClassification;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Classification {
    private static HashMap<String, Classification> classificationAll = new HashMap<>();
    private static int numClassifications = 0;

    public int id;

    public String classification;

    public Classification(String classification){
        this.classification = classification;
        id = numClassifications++;
    }

    public static Classification get(String key) {
        return classificationAll.get(key);
    }

    public static void put(String key, Classification newClassification) {
        classificationAll.put(key, newClassification);
    }

    public static Set<Map.Entry<String, Classification>> entrySet(){
        return classificationAll.entrySet();
    }
}
