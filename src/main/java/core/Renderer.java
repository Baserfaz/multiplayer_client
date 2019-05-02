package core;

import ui.Colors;
import ui.GuiElementManager;

import java.awt.*;

public class Renderer {

    private GuiElementManager guiElementManager;

    public Renderer(Graphics g, GuiElementManager gem) {
        RenderUtils.setRenderingHints((Graphics2D) g);
        this.guiElementManager = gem;
    }

    public void preRender(Graphics g) {
        GameState gamestate = Game.instance.getGameState();

        if(gamestate == GameState.INGAME) this.renderIngame(g);
        else if(gamestate== GameState.MAINMENU) this.renderMainMenu(g);
        else if(gamestate == GameState.LOADING) this.renderLoading(g);
        else if(gamestate == GameState.PAUSEMENU) this.renderPauseMenu(g);
    }

    private void renderPauseMenu(Graphics g) {}

    private void renderLoading(Graphics g) {}

    private void renderMainMenu(Graphics g) {
        RenderUtils.fillScreen(g, Colors.WHITE);
        guiElementManager.render(g, Game.instance.getGameState());
    }

    private void renderIngame(Graphics g) {
        RenderUtils.fillScreen(g, Colors.GAME_BACKGROUND);
    }



}
