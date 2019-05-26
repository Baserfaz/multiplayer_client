package ui;

import core.GameState;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class GuiElementManager {

    private HashMap<GameState, ArrayList<Panel>> gameStateToPanelMap;

    public GuiElementManager() {
        this.gameStateToPanelMap = new HashMap<>();
        for(GameState state : GameState.values()) {
            gameStateToPanelMap.put(state, new ArrayList<>());
        }

        // build login screen
        this.addElementToMap(GameState.LOGINSCREEN, GuiBuilder.createLoginScreen());
        this.addElementToMap(GameState.CHARACTERSCREEN, GuiBuilder.createCharacterSelectionScreen());
    }

    public void render(Graphics g, GameState state) {
        ListIterator<Panel> iter = this.getPanels(state).listIterator();
        while(iter.hasNext()) {
            Panel next = iter.next();
            if(next.isVisible) {
                next.render(g);
            }
        }
    }
    
    public void tick(GameState state) {
        for(GuiElement e : this.getPanels(state)) {
            if(e.isEnabled) {
                e.tick();
            }
        }
    }
    
    public void activateGuiElementsInGameState(GameState state) {
        this.deactivateAllElements();
        for(GuiElement e : getPanels(state)) {
            e.isEnabled = true; 
            e.isVisible = true;
        }
    }
    
    private void deactivateAllElements() {
        for(GameState s : GameState.values()) {
            for(GuiElement e : this.getPanels(s)) {
                e.isEnabled = false;
                e.isVisible = false;
            }
        }
    }

    public ArrayList<Panel> getPanels(GameState state) { return this.gameStateToPanelMap.get(state); }
    public void addElementToMap(GameState state, Panel panel) { this.gameStateToPanelMap.get(state).add(panel); }
}
