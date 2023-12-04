package NarutoDatabase.Scraper.Entities;

public class Tool {
    private static int numTools = 0;
    int id;

    String name;

    public Tool(String name){
        this.name = name;

        id = numTools++;
    }
}
