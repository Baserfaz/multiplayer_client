package core;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteCreator {

    private String path;
    private int height;
    private int width;
    private int[] pixels;

    public SpriteCreator(String path) {

        BufferedImage image = null;

        System.out.println("Loading spritesheet from path: \'" + path + "\'");

        // get the sprite sheet
        try { image = ImageIO.read(getClass().getResourceAsStream(path)); }
        catch (IOException e) { e.printStackTrace(); }

        if(image == null) {
            System.out.println("SpriteCreator: No image found!");
            return;
        } else {
            System.out.println("Spritesheet \'" + path + "\' loaded succesfully!");
        }

        // set vars
        this.path = path;
        this.height = image.getHeight();
        this.width = image.getWidth();

        // load the color data
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public BufferedImage FlipSpriteVertically(BufferedImage img) {
        // see : https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d

        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage image = op.filter(img, null);
        return image;
    }

    public BufferedImage FlipSpriteHorizontally(BufferedImage img) {
        // see: https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage retimage = op.filter(img, null);
        return retimage;
    }

    public BufferedImage createImageFromFile(String path, int sizeMult) {
        
        BufferedImage image = null;
        
        // get the sprite sheet
        try { image = ImageIO.read(getClass().getResourceAsStream(path)); }
        catch (IOException e) { e.printStackTrace(); }

        if(image == null) {
            System.out.println("SpriteCreator::createImageFromFile: No image found!");
        } else {
            System.out.println("Image \'" + path + "\' loaded succesfully!");
        }
        
        AffineTransform tx = new AffineTransform();
        tx.scale(sizeMult, sizeMult);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        
        return image;
    }

    public BufferedImage createCustomSizeSprite(
            int startx, int starty,
            int spriteWidth, int spriteHeight,
            double sizeMult) {

        BufferedImage sprite = new BufferedImage(spriteWidth, spriteHeight, BufferedImage.TYPE_INT_ARGB);
        int[] spritePixelData = new int[sprite.getWidth() * sprite.getHeight()];

        // calculate tile's pixel locations.
        int startX = startx;
        int endX = startx + spriteWidth;
        int startY = starty;
        int endY = starty + spriteHeight;

        int currentPixel = 0;

        // get the pixel array
        for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                spritePixelData[currentPixel] = pixels[y * width + x];  
                currentPixel ++;
            }
        }

        // set pixels
        for (int y = 0; y < sprite.getHeight(); y++) {
            for (int x = 0; x < sprite.getWidth(); x++) {
                sprite.setRGB(x, y, spritePixelData[y * sprite.getWidth() + x]);
            }
        }

        AffineTransform tx = new AffineTransform();
        tx.scale(sizeMult, sizeMult);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        sprite = op.filter(sprite, null);

        return sprite;
    }
    
    public BufferedImage[] createMultipleSprites(int column, int row, int length) {    

        BufferedImage[] sprites = new BufferedImage[length];
       
        int spriteSize = Game.SPRITE_GRID_SIZE;
        int spriteSizeMult = Game.SPRITE_SIZE_MULT;
        
        for(int i = 0; i < length; i++) {
    
            BufferedImage sprite = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
            int[] spritePixelData = new int[sprite.getWidth() * sprite.getHeight()];
            
            // calculate tile's pixel locations.
            int startX = (column + i) * spriteSize;
            int endX = startX + spriteSize;
            int startY = row * spriteSize;
            int endY = startY + spriteSize;
    
            int currentPixel = 0;
            
            // get the pixel array
            for(int y = startY; y < endY; y++) {
                for (int x = startX; x < endX; x++) {
                    spritePixelData[currentPixel] = pixels[y * width + x];
                    currentPixel ++;
                }
            }
    
            // set pixels
            for (int y = 0; y < sprite.getHeight(); y++) {
                for (int x = 0; x < sprite.getWidth(); x++) {
                    sprite.setRGB(x, y, spritePixelData[y * sprite.getWidth() + x]);
                }
            }
    
            AffineTransform tx = new AffineTransform();
            tx.scale(spriteSizeMult, spriteSizeMult);
    
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sprite = op.filter(sprite, null);
    
            sprites[i] = sprite;
        }
        
        return sprites;
    }
    
    public BufferedImage createSprite(SpriteType type) {

        int spriteSize = Game.SPRITE_GRID_SIZE;
        int spriteSizeMult = Game.SPRITE_SIZE_MULT;
        
        BufferedImage sprite = new BufferedImage(
                spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
        int[] spritePixelData = new int[sprite.getWidth() * sprite.getHeight()];

        // get spritetype coordinates in the spritesheet
        Point pos = this.getSpriteCoordinates(type);

        int column = pos.x;
        int row = pos.y; 

        // calculate tile's pixel locations.
        int startX = column * spriteSize;
        int endX = startX + spriteSize;
        int startY = row * spriteSize;
        int endY = startY + spriteSize;

        int currentPixel = 0;

        // get the pixel array
        for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                spritePixelData[currentPixel] = pixels[y * width + x];
                currentPixel ++;
            }
        }

        // set pixels
        for (int y = 0; y < sprite.getHeight(); y++) {
            for (int x = 0; x < sprite.getWidth(); x++) {
                sprite.setRGB(x, y, spritePixelData[y * sprite.getWidth() + x]);
            }
        }

        AffineTransform tx = new AffineTransform();
        tx.scale(spriteSizeMult, spriteSizeMult);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        sprite = op.filter(sprite, null);

        return sprite;
    }

    // tiles one sprite multiple times creating one large tiled sprite.
    public BufferedImage createTiledSprite(BufferedImage tileSprite, int widthMultiplier, int heightMultiplier) {
        
        if(widthMultiplier <= 0 || heightMultiplier <= 0) {
            System.out.println("Multipliers need to be greater than zero!");
            return null;
        }
        
        // in pixels
        int width = tileSprite.getWidth() * widthMultiplier;
        int height = tileSprite.getHeight() * heightMultiplier;
        
        // create new img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // get tileSprites pixels
        int[] spritePixelData = tileSprite.getRGB(0, 0, tileSprite.getWidth(), tileSprite.getHeight(), null, 0, tileSprite.getWidth());
        int[] resultPixels = new int[img.getWidth() * img.getHeight()];
        int index = 0;
        
        // multiply on x-axis
        for(int y = 0; y < tileSprite.getHeight(); y++) {
            // get row of pixels
            int[] row = this.getPixelInRow(y, spritePixelData, tileSprite.getWidth());
            
            // multiply the selected row pixels
            for(int a = 0; a < widthMultiplier; a++) {
                for(int i = 0; i < row.length; i++) {
                    resultPixels[index] = row[i];
                    index += 1;
                }
            }
        }
        // copy our current array of pixels into temporary one,
        // that we are going to multiply.
        int[] xAxisResultPixels = new int[index];
        System.arraycopy(resultPixels, 0, xAxisResultPixels, 0, index);
        
        // multiply on y-axis
        for(int a = 0; a < heightMultiplier - 1; a++) {
            for(int i = 0; i < xAxisResultPixels.length; i++) {
                resultPixels[index] = xAxisResultPixels[i];
                index += 1;
            }
        }
        
        // here we have the multiplied pixels 
        // and we just put them into our img.
        img.setRGB(0, 0, img.getWidth(), img.getHeight(), resultPixels, 0, img.getWidth());
        
        return img;
    }
    
    private int[] getPixelInRow(int y, int[] data, int width) {
        
        int[] pixels = new int[width];
        int pos = 0;
        for(int x = 0; x < width; x++) {
            pixels[pos] = data[y * width + x];
            pos += 1;
        }
        return pixels;
    }
    
    private Point getSpriteCoordinates(SpriteType type) {
        Point pos = new Point(0, 0);
        switch(type) {

            case LOADING_0:
                pos.x = 0;
                pos.y = 9;
                break;

            case LOADING_1:
                pos.x = 1;
                pos.y = 9;
                break;

            case LOADING_2:
                pos.x = 2;
                pos.y = 9;
                break;

            default:
                System.out.println("Could not find a sprite of spritetype:" + type);
                break;
        }

        return pos;
    }
}
