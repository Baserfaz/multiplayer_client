package core;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteStorage {

    private Map<SpriteType, BufferedImage> sprites;

    public SpriteStorage() {
        this.sprites = new HashMap<>();
    }
 
    public void loadSprites() {
        SpriteCreator sc = Game.instance.getSpriteCreator();
        for(SpriteType s : SpriteType.values()) {
            BufferedImage img = sc.createSprite(s);
            if(img != null) { this.addSprite(s, img); }
        }
    }

    public void addSprite(SpriteType type, BufferedImage img) {
        this.sprites.putIfAbsent(type, img);
    }

    public BufferedImage getSprite(SpriteType type) { 
        BufferedImage img = this.sprites.get(type);
        if(img == null) {
            System.out.println(
                    "SpriteStorage::getSprite: " +
                            "no sprite found for spritetype: "
                            + type);
        }
        return img;
    }

}
