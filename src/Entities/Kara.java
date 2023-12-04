package Entities;

import Entities.Interfaces.IDebut;
import Entities.Interfaces.IHasKekkiGenkai;
import Entities.Interfaces.IHasPersonal;
import Entities.Interfaces.IHasTools;

import java.util.ArrayList;

public class Kara implements IHasPersonal, IDebut, IHasTools, IHasKekkiGenkai {
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
}
