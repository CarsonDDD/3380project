package Entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UniqueTrait {
	private static HashMap<String, UniqueTrait> traitsAll = new HashMap<>();
	private static int numTraits= 0;

	public int id;
	public String trait;

	public UniqueTrait(String trait){
		this.trait = trait;
		id = numTraits++;
	}

	public static UniqueTrait get(String traitName){
		return traitsAll.get(traitName);
	}

	public static UniqueTrait put(String traitName, UniqueTrait trait){
		return traitsAll.put(traitName, trait);
	}

	public static Set<Map.Entry<String, UniqueTrait>> entrySet(){
		return traitsAll.entrySet();
	}
}
