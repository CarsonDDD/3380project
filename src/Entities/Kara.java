package Entities;

import Entities.Interfaces.IHasDebut;
import Entities.Interfaces.IHasKekkiGenkai;
import Entities.Interfaces.IHasPersonal;
import Entities.Interfaces.IHasTools;

import java.util.ArrayList;
import java.util.HashMap;

public class Kara implements IHasPersonal, IHasDebut, IHasTools, IHasKekkiGenkai {
    private static HashMap<Integer, Kara> karaAll = new HashMap<>();
    String name;
    int id;

    private final ArrayList<Media> debuts = new ArrayList<>();

    private final ArrayList<Personal> personal = new ArrayList<>();

    private final ArrayList<Tool> tools = new ArrayList<>();

    private final ArrayList<KekkeiGenkai> kekkeiGenkais = new ArrayList<>();

    public Kara(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public void addPersonal(Personal newPersonal) {
        personal.add(newPersonal);
    }

    @Override
    public void addMedia(Media newMedia) {
        debuts.add(newMedia);
    }

    @Override
    public void addTool(Tool newTool) {
        tools.add(newTool);
    }

    @Override
    public void addKekkiGenkai(KekkeiGenkai kekki) {
        kekkeiGenkais.add(kekki);
    }

    public static Kara get(Integer id) {
        return karaAll.get(id);
    }

    public static void put(Integer id, Kara kara) {
        karaAll.put(id, kara);
    }
}
