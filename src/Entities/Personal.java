package Entities;

import java.util.HashMap;

// TODO: Personals is weird
public class Personal {
    private static int numPersonal = 0;
    public int id;

    String type;
    String value;

    public Personal(String type, String value){
        this.type = type;
        this.value = value;

        id = numPersonal++;
    }

    private static HashMap<String, Personal> personalAll = new HashMap<>();

    public static Personal get(String key) {
        return personalAll.get(key);
    }

    public static void put(String key, Personal personal) {
        personalAll.put(key, personal);
    }
}