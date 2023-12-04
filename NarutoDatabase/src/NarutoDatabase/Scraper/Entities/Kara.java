package NarutoDatabase.Scraper.Entities;

import NarutoDatabase.Scraper.Entities.Interfaces.IDebut;
import NarutoDatabase.Scraper.Entities.Interfaces.IHasKekkiGenkai;
import NarutoDatabase.Scraper.Entities.Interfaces.IHasPersonal;
import NarutoDatabase.Scraper.Entities.Interfaces.IHasTools;

import java.util.ArrayList;

public class Kara implements IHasPersonal, IDebut, IHasTools, IHasKekkiGenkai {
    String name;
    int id;

    private ArrayList<Media> debuts = new ArrayList<>();

    private ArrayList<Personal> personal = new ArrayList<>();

    private ArrayList<Tool> tools = new ArrayList<>();

    private ArrayList<KekkeiGenkai> kekkeiGenkais = new ArrayList<>();

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
