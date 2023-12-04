package Entities;

public class Personal {
    private static int numPersonal = 0;
    int id;

    String type;
    String value;

    public Personal(String type, String value){
        this.type = type;
        this.value = value;

        id = numPersonal++;
    }
}
