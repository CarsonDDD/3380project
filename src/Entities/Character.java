package Entities;
import Entities.Interfaces.*;

import java.util.*;

public class Character implements IHasPersonal, IHasDebut, IHasTools, IHasJutsu, IHasNatureType, IHasVoiceActor, IHasUniqueTraits {
	private static HashMap<Integer, Character> charactersAll = new HashMap<>();
	int id;
	public String name;
	//Other information
	private final ArrayList<Jutsu> jutsus = new ArrayList<>();

	public final ArrayList<UniqueTrait> traits = new ArrayList<>();

	public final ArrayList<Media> debuts = new ArrayList<>();

	public final ArrayList<Personal> personals = new ArrayList<>();

	public final ArrayList<VoiceActor> voiceActors = new ArrayList<>();

	public final ArrayList<Tool> tools = new ArrayList<>();

	public final ArrayList<NatureType> natureTypes = new ArrayList<>();

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

	public static Character get(int characterId){
		return charactersAll.get(characterId);
	}

	public static Character put(int charId, Character character){
		return charactersAll.put(charId, character);
	}

	public static Character get(String characterName){
		for (Character character : charactersAll.values()) {
			if (character.name.contains(characterName)) {
				return character;
			}
		}
		return null;
	}

	public static Set<Map.Entry<Integer, Character>> entrySet(){
		return charactersAll.entrySet();
	}

}
