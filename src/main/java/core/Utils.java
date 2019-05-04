package core;

import java.awt.*;
import java.io.IOException;

public class Utils {

    public static boolean isCharInArray(char c, char[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(c == arr[i]) return true;
        }
        return false;
    }

    public static void loadCustomFont() {

        String fullPath = "/fonts/" + Game.CUSTOM_FONT_FOLDER + "/" + Game.CUSTOM_FONT_NAME + Game.CUSTOM_FONT_EXTENSION;
        Font font;

        System.out.println(
                "Trying to load font: " + fullPath);

        try {
            font = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Utils.class.getResourceAsStream(fullPath));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (FontFormatException | IOException e) {
            throw new IllegalStateException("Failed to load font");
        }

        // cache the font
        Game.instance.setCustomFont(font);

        System.out.println(
                "Succesfully loaded custom font: "
                        + font.getFontName());
    }

}
