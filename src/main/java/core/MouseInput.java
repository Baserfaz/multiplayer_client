package core;

import ui.GuiElement;
import ui.GuiElementManager;
import ui.InteractableGuiElement;
import ui.Panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class MouseInput implements MouseMotionListener, MouseListener {

    private GuiElement lastElementHovered;
    private boolean hoveredOnSomething;
    private GuiElementManager guiElementManager;
    private Point mousePosition;

    public MouseInput(GuiElementManager guiElementManager) {
        this.guiElementManager = guiElementManager;
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {

        if(e.getButton() != MouseEvent.BUTTON1) return;

        List<Panel> panels = this.guiElementManager.getPanels(Game.instance.getGameState());
        if(panels.isEmpty() || mousePosition == null) return;

        panels.forEach(a -> handlePanelElements(a, mousePosition));
    }

    private void handlePanelElements(Panel panel, Point mousePos) {
        for(GuiElement el : panel.getElements()) {
            if(el instanceof Panel) { this.handlePanelElements((Panel) el, mousePos); }
            handleClick(el, mousePos);
        }
    }

    private void handleClick(GuiElement el, Point mousePos) {

        // unfocus everything else
        if(!el.getBounds().contains(mousePos)) {
            if(el instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) el;
                iel.onUnfocus();
            }
            return;
        }

        // focus only the element we clicked on
        if(!el.isEnabled() || !(el instanceof InteractableGuiElement)) return;
        ((InteractableGuiElement) el).onClick();
    }

    // hover effects on gui elements.
    public void mouseMoved(MouseEvent e) {
        this.mousePosition = e.getPoint();

        List<Panel> panels = this.guiElementManager.getPanels(Game.instance.getGameState());
        if(panels == null || panels.isEmpty()) return;

        this.hoveredOnSomething = false;

        panels.forEach(a -> handleHoverOnPanelElements(a, e));

        if(!hoveredOnSomething && this.lastElementHovered != null) {
            if(this.lastElementHovered instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) this.lastElementHovered;
                iel.setHovering(false);
            }
            this.lastElementHovered = null;
        }
    }

    private void handleHoverOnPanelElements(Panel panel, MouseEvent e) {
        for(GuiElement el : panel.getElements()) {
            if(el instanceof Panel) { this.handleHoverOnPanelElements((Panel) el, e); }
            this.handleHoverOnElement(el, e);
        }
    }

    private void handleHoverOnElement(GuiElement el, MouseEvent e) {

        if(!(el instanceof InteractableGuiElement) || !el.isEnabled()) {
            return;
        }

        boolean hovering = el.getBounds().contains(e.getPoint());
        if(!hovering) return;

        InteractableGuiElement iel = (InteractableGuiElement) el;

        iel.setHovering(true);
        iel.onHover();

        this.hoveredOnSomething = true;
        this.lastElementHovered = el;
    }

    public void mouseDragged(MouseEvent e) {
        this.mousePosition = e.getPoint();
    }

    // not used
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
