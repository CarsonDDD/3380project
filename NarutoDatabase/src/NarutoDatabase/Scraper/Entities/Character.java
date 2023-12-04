package NarutoDatabase.Scraper.Entities;
import NarutoDatabase.Scraper.Entities.Interfaces.*;

import java.util.*;

public class Character implements IHasPersonal, IDebut, IHasTools, IHasJutsu, IHasNatureType, IHasVoiceActor, IHasUniqueTraits {
	int id;
	public String name;
	//Other information
	private ArrayList<Jutsu> jutsus = new ArrayList<>();
	private ArrayList<UniqueTrait> traits = new ArrayList<>();

	private ArrayList<Media> debuts = new ArrayList<>();

	private ArrayList<Personal> personals = new ArrayList<>();

	private ArrayList<VoiceActor> voiceActors = new ArrayList<>();

	private ArrayList<Tool> tools = new ArrayList<>();

	private ArrayList<NatureType> natureTypes = new ArrayList<>();

	public Character(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addPersonal(Personal newPersonal) {
		personals.add(newPersonal);
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
	public void addJutus(Jutsu jutsu) {
		jutsus.add(jutsu);
	}

	@Override
	public void addNatureType(NatureType type) {
		natureTypes.add(type);
	}

	@Override
	public void addTrait(UniqueTrait trait) {
		traits.add(trait);
	}

	@Override
	public void addActor(VoiceActor actor) {
		voiceActors.add(actor);
	}
}
