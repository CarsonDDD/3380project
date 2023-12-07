package Entities;
import Entities.Interfaces.*;

import java.util.*;

// TODO: Write function to flatten personals into a single Personal (used for sql)
public class Character implements IHasPersonal, IHasDebut, IHasTools, IHasJutsu, IHasNatureType, IHasVoiceActor, IHasUniqueTraits, IHasRanks, IHasOccupation, IHasClassification {
	private final static HashMap<Integer, Character> charactersAll = new HashMap<>();
	public int id;
	public String name;
	//Other information
	public final ArrayList<Jutsu> jutsus = new ArrayList<>();

	public final ArrayList<UniqueTrait> traits = new ArrayList<>();

	public final ArrayList<Media> debuts = new ArrayList<>();

	//public final ArrayList<Personal> personals = new ArrayList<>();

	public final ArrayList<VoiceActor> voiceActors = new ArrayList<>();

	public final ArrayList<Tool> tools = new ArrayList<>();

	public final ArrayList<NatureType> natureTypes = new ArrayList<>();

	public final ArrayList<Rank> ranks = new ArrayList<>();

	public final ArrayList<Occupation> occupations = new ArrayList<>();

	public final ArrayList<Classification> classifications = new ArrayList<>();

	public String birthDate = "";
	public String bloodType = "";
	public String status = "";
	public String sex = "";
	public String weight = "";
	public String age = "";
	public String height ="";

	public Character(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public void addOccupation(Occupation newOccupation){
		occupations.add(newOccupation);
	}

	@Override
	public void addPersonal(Personal newPersonal) {
		//personals.add(newPersonal);
		// Check type
		// fill in value
		// dance?!?@?@?@?@

		if(newPersonal.type.equals("birthdate")){
			this.birthDate = newPersonal.value;
		}
		else if(newPersonal.type.equals("bloodType")){
			this.bloodType = newPersonal.value;
		}
		else if(newPersonal.type.equals("status")){
			this.status = newPersonal.value;
		}
		else if(newPersonal.type.equals("sex")){
			this.sex = newPersonal.value;
		}
		else if(newPersonal.type.equals("weight")){
			this.weight = newPersonal.value;
		}
		else if(newPersonal.type.equals("age")){
			this.age = newPersonal.value;
		}
		else if(newPersonal.type.equals("height")){
			this.height = newPersonal.value;
		}
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

	@Override
	public void addRank(Rank rank) {
		ranks.add(rank);
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

	@Override
	public void addClassification(Classification newClassification) {
		classifications.add(newClassification);
	}
}
