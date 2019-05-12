package ui;

import core.Game;

import java.awt.*;

import static ui.HorizontalAlign.CENTER;

public class PopupPanel extends VPanel {

    public PopupPanel(
            int width,
            int height,
            boolean hasBorder,
            Color borderColor) {
        super(VerticalAlign.MIDDLE,
                CENTER,
                width,
                height,
                null,
                Colors.WHITE,
                borderColor,
                false,
                hasBorder,
                2,
                CENTER);

        // always on top
        this.z = 100;

        // add this panel to the draw list
        Game.instance
                .getGuiElementManager()
                .addElementToMap(Game.instance.getGameState(), this);
    }
}
