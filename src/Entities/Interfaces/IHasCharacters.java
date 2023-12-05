package Entities.Interfaces;

import Entities.Character;

public interface IHasCharacters {
    void addCharacter(Character newCharacter);

    boolean containsCharacter(Character character);
}
