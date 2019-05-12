package ui;

import core.Game;

import java.awt.*;

/**
 * Idea of this panel is that it's simply over everything.
 * It is always created in front of everything else.
 * And it can be created on run.
 */
public class PopupPanel extends VPanel {

    public PopupPanel(int width, int height,
                      boolean hasBorder,
                      Color borderColor) {

        super(VerticalAlign.MIDDLE, HorizontalAlign.CENTER,
                width, height,
                null,
                Colors.WHITE, borderColor,
                false,
                hasBorder, 2,
                HorizontalAlign.CENTER);

        // always on top
        this.z = 100;

        // add this panel to the draw list
        Game.instance
                .getGuiElementManager()
                .addElementToMap(Game.instance.getGameState(), this);
    }
}
