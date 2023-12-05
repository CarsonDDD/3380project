package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tool {
    private static int numTools = 0;
    public int id;

    public String name;

    public Tool(String name){
        this.name = name;

        id = numTools++;
    }

    private static HashMap<String, Tool> toolsAll = new HashMap<>();

    public static Tool get(String key) {
        return toolsAll.get(key);
    }

    public static void put(String key, Tool tool) {
        toolsAll.put(key, tool);
    }

    public static Set<Map.Entry<String, Tool>> entrySet(){
        return toolsAll.entrySet();
    }
}