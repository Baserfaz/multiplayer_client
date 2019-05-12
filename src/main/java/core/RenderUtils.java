package core;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class RenderUtils {

    public static void setRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static void fillScreen(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(0, 0,
                Game.instance.WIDTH + 10,
                Game.instance.HEIGHT + 10);
    }

    public static BufferedImage scaleSprite(BufferedImage sprite, double scale) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scale, scale);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(sprite, null);
    }

    public static BufferedImage tint(BufferedImage image, boolean darker, int times) {

        // see: http://stackoverflow.com/questions/4248104/applying-a-tint-to-an-image-in-java

        // copy the image
        BufferedImage tintedImage = RenderUtils.deepCopy(image);

        // loop through all pixels
        for(int i = 0; i < times; i++) {
            for(int x = 0; x < tintedImage.getWidth(); x++) {
                for (int y = 0; y < tintedImage.getHeight(); y++) {

                    // second parameter is if there is alpha channel.
                    Color color = new Color(tintedImage.getRGB(x, y), true);
                    Color tintedColor;

                    // make the pixel's color darker.
                    if(darker) tintedColor = color.darker();
                    else tintedColor = color.brighter();

                    // apply color to new image.
                    tintedImage.setRGB(x, y, tintedColor.getRGB());
                }
            }
        }
        return tintedImage;
    }

    public static BufferedImage tintWithColor(BufferedImage image, Color tintColor) {

        // see: // https://stackoverflow.com/questions/4248104/applying-a-tint-to-an-image-in-java

        // copy the image
        BufferedImage tintedImage = RenderUtils.deepCopy(image);

        // loop through all pixels
        for(int x = 0; x < tintedImage.getWidth(); x++) {
            for (int y = 0; y < tintedImage.getHeight(); y++) {

                // second parameter is if there is alpha channel.
                Color pixelColor = new Color(tintedImage.getRGB(x, y), true);

                int r = (pixelColor.getRed() + tintColor.getRed()) / 2;
                int g = (pixelColor.getGreen() + tintColor.getGreen()) / 2;
                int b = (pixelColor.getBlue() + tintColor.getBlue()) / 2;
                int a = pixelColor.getAlpha();

                int rgba = (a << 24) | (r << 16) | (g << 8) | b;

                // apply color to new image.
                tintedImage.setRGB(x, y, rgba);
            }
        }
        return tintedImage;
    }

    public static BufferedImage changeImageColor(BufferedImage image, Color color) {

        // copy the image
        BufferedImage tintedImage = RenderUtils.deepCopy(image);

        // loop through all pixels
        for(int x = 0; x < tintedImage.getWidth(); x++) {
            for (int y = 0; y < tintedImage.getHeight(); y++) {

                // second parameter is if there is alpha channel.
                Color pixelColor = new Color(tintedImage.getRGB(x, y), true);

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = pixelColor.getAlpha();

                int rgba = (a << 24) | (r << 16) | (g << 8) | b;

                // apply color to new image.
                tintedImage.setRGB(x, y, rgba);
            }
        }
        return tintedImage;
    }

    public static BufferedImage highlightTileBorders(BufferedImage image, Color tintColor) {

        // copy the image
        BufferedImage tintedImage = RenderUtils.deepCopy(image);

        // loop through all pixels
        for(int x = 0; x < tintedImage.getWidth(); x++) {
            for (int y = 0; y < tintedImage.getHeight(); y++) {

                // get pixel + alpha values
                Color pixelColor = new Color(tintedImage.getRGB(x, y), true);
                int r, g, b, a, rgba;


                // don't highlight the corner pixels
                if((x == 0 && y == 0)
                        || (x == tintedImage.getWidth() - 1 && y == 0)
                        || (x == 0 && y == tintedImage.getHeight() - 1)
                        || (x == tintedImage.getWidth() - 1 && y == tintedImage.getHeight() - 1)) {

                    // use default pixel color.
                    r = pixelColor.getRed();
                    g = pixelColor.getGreen();
                    b = pixelColor.getBlue();
                    a = pixelColor.getAlpha();
                    rgba = (a << 24) | (r << 16) | (g << 8) | b;

                    tintedImage.setRGB(x, y, rgba);

                    continue;
                }

                // if the pixel is not a corner pixel
                if(x == 0 || x == tintedImage.getWidth() - 1 || y == 0 || y == tintedImage.getHeight() - 1) {

                    // tint the border with some color
                    // calculates average values of all color channels.
                    r = (pixelColor.getRed() + tintColor.getRed()) / 2;
                    g = (pixelColor.getGreen() + tintColor.getGreen()) / 2;
                    b = (pixelColor.getBlue() + tintColor.getBlue()) / 2;
                    a = 255;
                    rgba = (a << 24) | (r << 16) | (g << 8) | b;

                    // ALPHA    RED      GREEN    BLUE
                    // 11111111 00000000 00000000 00000000 32-bit integer

                } else {

                    // use default pixel color.
                    r = pixelColor.getRed();
                    g = pixelColor.getGreen();
                    b = pixelColor.getBlue();
                    a = pixelColor.getAlpha();
                    rgba = (a << 24) | (r << 16) | (g << 8) | b;

                }

                // apply color to new image.
                tintedImage.setRGB(x, y, rgba);
            }
        }
        return tintedImage;
    }

    // http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    // render with free angle
    public static void RenderSpriteWithRotation(BufferedImage sprite,
                                                Point pos, double angle, Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int spriteWidth = sprite.getWidth();
        int spriteHeight = sprite.getHeight();

        double rot = Math.toRadians(angle);
        int x = pos.x;
        int y = pos.y;

        // pivot point in center of the sprite
        int xpivot = x + spriteWidth / 2;
        int ypivot = y + spriteHeight / 2;

        g2d.rotate(rot, xpivot, ypivot);
        g.drawImage(sprite, x, y, spriteWidth, spriteHeight, null);
        g2d.rotate(-rot, xpivot, ypivot);
    }

    public static BufferedImage rotateImageClockwise(BufferedImage img, int times) {

        BufferedImage temp = RenderUtils.deepCopy(img);

        for(int a = 0; a < times; a++) {
            temp = RenderUtils.rotateImg(temp);
        }

        return temp;
    }

    private static BufferedImage rotateImg(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, img.getType());

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));
            }
        }
        return newImage;
    }

    public static void renderSpriteFlippedHorizontally(BufferedImage sprite, Point pos, Graphics g) {
        g.drawImage(RenderUtils.flipSpriteHorizontally(sprite), pos.x, pos.y, null);
    }

    public static BufferedImage flipSpriteHorizontally(BufferedImage sprite) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-sprite.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        sprite = op.filter(sprite, null);
        return sprite;
    }

    public static void FillScreenWithImage(Graphics g, Image img) {
        g.drawImage(img, 0, 0, Game.instance.WIDTH, Game.instance.HEIGHT, null);
    }

    public static void FillRectWithImage(Rectangle rect, Graphics g, Image img) {
        g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
    }

    public static void RenderSpriteWithBorder(BufferedImage sprite,
                                              Point pos, Graphics g, Color borderColor) {
        BufferedImage img = RenderUtils.highlightTileBorders(sprite, borderColor);
        g.drawImage(img, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), null);
    }

    public static void RenderSpriteWithTint(BufferedImage sprite,
                                            Point pos, Graphics g, Color tint) {
        BufferedImage img = RenderUtils.tintWithColor(sprite, tint);
        g.drawImage(img, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), null);
    }

}
