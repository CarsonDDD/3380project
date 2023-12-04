package Entities;

import java.util.HashMap;

public class UniqueTrait {
	private static HashMap<String, UniqueTrait> traitsAll = new HashMap<>();
	String trait;

	public UniqueTrait(String trait){
		this.trait = trait;
	}

	public static UniqueTrait get(String traitName){
		return traitsAll.get(traitName);
	}

	public static UniqueTrait put(String traitName, UniqueTrait trait){
		return traitsAll.put(traitName, trait);
	}
}
