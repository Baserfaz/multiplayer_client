package ui;

import core.Game;
import core.GameState;
import core.SpriteType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GuiFactory {

    private static final int DEFAULT_PANEL_WIDTH = 500;
    private static final int DEFAULT_PANEL_HEIGHT = 300;

    private static final int DEFAULT_BUTTON_WIDTH = 150;
    private static final int DEFAULT_BUTTON_HEIGHT = 35;
    private static final int DEFAULT_FONTSIZE = 40;

    private static final int DEFAULT_TEXTFIELD_WIDTH = 350;
    private static final int DEFAULT_TEXTFIELD_HEIGHT = 35;

    private static final int DEFAULT_TEXTFIELD_MAXLEN = 15;

    // ------------------

    public static Panel createLoadingPane(String msg) {

        PopupPanel popupPanel = new PopupPanel(
                350, 100, true, Colors.DARK_BLUE);

        popupPanel.addElement(
                GuiFactory.createDefaultPlainText(
                        popupPanel,
                        HorizontalAlign.CENTER,
                        msg,
                        Colors.BLACK)
        );

        // create animation frames
        List<BufferedImage> frames = new ArrayList<>();
        frames.add(
                Game.instance
                        .getSpriteStorage()
                        .getSprite(SpriteType.LOADING_0)
        );
        frames.add(
                Game.instance
                        .getSpriteStorage()
                        .getSprite(SpriteType.LOADING_1)
        );
        frames.add(
                Game.instance
                        .getSpriteStorage()
                        .getSprite(SpriteType.LOADING_2)
        );

        popupPanel.addElement(
                new GuiAnimation(
                        popupPanel,
                        frames,
                        HorizontalAlign.CENTER
                )
        );
        popupPanel.shrink();
        return popupPanel;
    }

    public static Panel createMessagePanel(String message) {

        PopupPanel popupPanel = new PopupPanel(
                650, 300, true, Colors.DARK_BLUE);

        popupPanel.addElement(
                new PlainText(
                        popupPanel,
                        HorizontalAlign.CENTER,
                        message,
                        28,
                        Colors.BLACK)
        );

        popupPanel.addElement(
            createDraggablePanelCloseButton(popupPanel)
        );

        popupPanel.shrink();

        return popupPanel;
    }

    public static PlainText createDefaultPlainText(
            Panel panel,
            HorizontalAlign align,
            String text,
            Color color) {
        return new PlainText(panel, align, text, 40, color);
    }

    public static TextField createDefaultPasswordField(Panel panel, String label) {
        return new TextField(panel,
                DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT,
                15, DEFAULT_FONTSIZE, DEFAULT_TEXTFIELD_MAXLEN,
                true, true, label);
    }

    public static TextField createDefaultTextField(Panel panel, String label) {
        return new TextField(panel,
                DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT,
                15, DEFAULT_FONTSIZE, DEFAULT_TEXTFIELD_MAXLEN,
                true, false, label);
    }

    public static HPanel createDefaultHorizontalPanel(Panel parent,
                                                      VerticalAlign va,
                                                      HorizontalAlign ha,
                                                      boolean isTransparent,
                                                      Color bgcolor) {
        return new HPanel(
                va, ha,
                Game.instance.WIDTH + 10,
                50,
                parent,
                bgcolor, Colors.GRAY,
                isTransparent,
                false,
                15,
                true);
    }

    public static VPanel createDefaultCenteredPanel(Panel parent,
                                                    boolean isTransparent,
                                                    Color bgcolor) {
        return new VPanel(
                VerticalAlign.MIDDLE,
                HorizontalAlign.CENTER,
                DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT,
                parent,
                bgcolor, Colors.DARK_BLUE,
                isTransparent,
                true, 15,
                HorizontalAlign.CENTER
        );
    }

    public static Button createDefaultConnectButton(
            Panel panel,
            Runnable connectRunnable) {

        return new Button(panel, 600, 50,
                "Connect", Color.black, Color.white, DEFAULT_FONTSIZE,
                connectRunnable,
                null);
    }

    public static Button createDraggablePanelCloseButton(PopupPanel panel) {

        Runnable close = () -> {
            panel.isVisible = false;
            panel.isEnabled = false;
            panel.elements.clear();
        };

        return new Button(panel, 600, 50,
                "Close", Color.black, Color.white, DEFAULT_FONTSIZE,
                close, null);
    }

    public static Button createDefaultPlayButton(Panel panel) {
        return new Button(panel, 600, 50,
                "Play", Color.black, Color.white, DEFAULT_FONTSIZE,
                null, null);
    }

    public static Button createDefaultExitToMainMenuButton(Panel panel) {
        return new Button(panel, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT,
                "Exit to mainmenu", Color.black, Color.white, DEFAULT_FONTSIZE,
                null, null);
    }

    public static Button createDefaultExitButton(Panel panel) {
        return new Button(panel, 600, 50,
                "Exit", Color.black, Color.white, DEFAULT_FONTSIZE,
                () -> System.exit(0), null);
    }

    public static Button createDefaultResumeButton(Panel panel) {
        return new Button(panel, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT,
                "Resume", Color.black, Color.white, DEFAULT_FONTSIZE,
                () -> {
                    Game.instance.setGameState(GameState.INGAME);
                }, null);
    }

}
