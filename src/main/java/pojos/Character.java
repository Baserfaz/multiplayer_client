package pojos;

import java.io.Serializable;

public class Character extends BaseMessage implements Serializable {

    private String characterName;

    public Character(Command command, String characterName) {
        this.command = command;
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
