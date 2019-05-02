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

        for(Panel panel : panels) {
            handlePanelElements(panel, mousePosition);
        }
    }

    // go through panel's child panels too recursively
    private void handlePanelElements(Panel panel, Point mousePos) {
        for(GuiElement el : panel.getElements()) {
            if(el instanceof Panel) { this.handlePanelElements((Panel) el, mousePos); }
            if(handleClick(panel, el, mousePos)) break;
        }
    }

    private boolean handleClick(Panel panel, GuiElement el, Point mousePos) {

        // tie mouse position to camera position

        //Rectangle cameraBounds = Game.instance.getCamera().getCameraBounds();
        Point mousePoint = mousePos;
        //mousePoint.x += cameraBounds.x;
        //mousePoint.y += cameraBounds.y;

        if(!el.getBounds().contains(mousePoint)) {
            if(el instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) el;
                iel.onUnfocus();
            }
            return false;
        }

        if(!el.isEnabled() || !(el instanceof InteractableGuiElement)) return false;
        InteractableGuiElement iel = (InteractableGuiElement) el;

        iel.onClick();
        return true;
    }

    // hover effects on gui elements.
    public void mouseMoved(MouseEvent e) {
        this.mousePosition = e.getPoint();

        List<Panel> panels = this.guiElementManager.getPanels(Game.instance.getGameState());
        if(panels == null || panels.isEmpty()) return;

        this.hoveredOnSomething = false;

        for(Panel panel : panels) { if(handleHoverOnPanelElements(panel, e)) break; }

        if(!hoveredOnSomething && this.lastElementHovered != null) {
            if(this.lastElementHovered instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) this.lastElementHovered;
                iel.setHovering(false);
            }
            this.lastElementHovered = null;
        }
    }

    private boolean handleHoverOnPanelElements(Panel panel, MouseEvent e) {
        for(GuiElement el : panel.getElements()) {
            if(el instanceof Panel) { this.handleHoverOnPanelElements((Panel) el, e); }
            if(this.handleHoverOnElement(el, e)) return true;
        }
        return false;
    }

    private boolean handleHoverOnElement(GuiElement el, MouseEvent e) {
        if(!(el instanceof InteractableGuiElement) || !el.isEnabled()) {
            return false;
        }

        // tie the mouse position to camera position
        //Rectangle cameraBounds = Game.instance.getCamera().getCameraBounds();
        Point mousePoint = e.getPoint();
        //mousePoint.x += cameraBounds.x;
        //mousePoint.y += cameraBounds.y;

        boolean hovering = el.getBounds().contains(mousePoint);
        if(!hovering) return false;

        InteractableGuiElement iel = (InteractableGuiElement) el;
        this.hoveredOnSomething = true;

        iel.setHovering(true);
        iel.onHover();
        this.lastElementHovered = el;
        return true;
    }

    public void mouseDragged(MouseEvent e) {
        this.mousePosition = e.getPoint();
    }

    // not used
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
