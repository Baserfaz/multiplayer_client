package core;

import ui.GuiElement;
import ui.GuiElementManager;
import ui.InteractableGuiElement;
import ui.Panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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

        // order panels: highest z-level first
        panels = panels.stream()
                .sorted(Comparator.comparing(Panel::getZ).reversed())
                .collect(Collectors.toList());

        // if this is changed to true,
        // then we don't want to handle clicks anymore.
        boolean isClickHandled = false;

        ListIterator<Panel> iter = new ArrayList<>(panels).listIterator();
        while(iter.hasNext()) {
            Panel next = iter.next();
            boolean b = handlePanelElements(next, mousePosition, isClickHandled);

            // can only once be set to true.
            // if at any stage it's changed to true, then it no longer can be false.
            if(!isClickHandled) {
                isClickHandled = b;
            }
        }
    }

    private boolean handlePanelElements(Panel panel, Point mousePos, boolean isClickHandled) {

        // order elements: highest z-level first
        List<GuiElement> collect = panel.getElements().stream()
                .sorted(Comparator.comparing(GuiElement::getZ).reversed())
                .collect(Collectors.toList());

        // use iterator
        ListIterator<GuiElement> iter = new ArrayList<>(collect).listIterator();
        while(iter.hasNext()) {
            GuiElement el = iter.next();
            if(el instanceof Panel) {
                boolean b = this.handlePanelElements((Panel) el, mousePos, isClickHandled);

                // can only change to true once
                if(!isClickHandled) {
                    isClickHandled = b;
                }
            }
            boolean b = handleElement(el, mousePos, isClickHandled);

            // can only change to true once
            if(!isClickHandled) {
                isClickHandled = b;
            }
        }

        return isClickHandled;
    }

    /**
     * Returns if click is already handled,
     * then all other panels and elements are not handled anymore.
     * @param el
     * @param mousePos
     * @return
     */
    private boolean handleElement(GuiElement el, Point mousePos, boolean isClickHandled) {

        // unfocus everything else that are not clicked
        if(!el.getBounds().contains(mousePos)) {
            if(el instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) el;
                iel.onUnfocus();
            }
            return false;
        }

        // if the element that we clicked is not interactable
        if(!el.isEnabled()
                || !(el instanceof InteractableGuiElement)
                || isClickHandled) return false;

        // focus only the element we clicked on
        InteractableGuiElement clickedElement = (InteractableGuiElement) el;
        clickedElement.onClick();
        return true;
    }

    // hover effects on gui elements.
    public void mouseMoved(MouseEvent e) {
        this.mousePosition = e.getPoint();

        List<Panel> panels = this.guiElementManager.getPanels(Game.instance.getGameState());
        if(panels == null || panels.isEmpty()) return;

        this.hoveredOnSomething = false;

        List<Panel> collect = panels.stream()
                .sorted(Comparator.comparing(Panel::getZ).reversed())
                .collect(Collectors.toList());

        boolean hasHoveredOver = false;

        for (Panel a : collect) {
            boolean b = handleHoverOnPanelElements(a, e, hasHoveredOver);
            if(!hasHoveredOver) {
                hasHoveredOver = b;
            }
        }

        if(!hoveredOnSomething && this.lastElementHovered != null) {
            if(this.lastElementHovered instanceof InteractableGuiElement) {
                InteractableGuiElement iel = (InteractableGuiElement) this.lastElementHovered;
                iel.setHovering(false);
            }
            this.lastElementHovered = null;
        }
    }

    private boolean handleHoverOnPanelElements(Panel panel, MouseEvent e, boolean hasHoveredOver) {

        List<GuiElement> collect = panel.getElements().stream()
                .sorted(Comparator.comparing(GuiElement::getZ).reversed())
                .collect(Collectors.toList());

        for(GuiElement el : collect) {
            if(el instanceof Panel) {
                boolean b = this.handleHoverOnPanelElements((Panel) el, e, hasHoveredOver);
                if(!hasHoveredOver) {
                    hasHoveredOver = b;
                }
            }
            boolean b = this.handleHoverOnElement(el, e, hasHoveredOver);
            if(!hasHoveredOver) {
                hasHoveredOver = b;
            }
        }

        return hasHoveredOver;
    }

    private boolean handleHoverOnElement(GuiElement el, MouseEvent e, boolean hasHoveredOver) {

        if(!(el instanceof InteractableGuiElement) || !el.isEnabled()) {
            return false;
        }

        boolean hovering = el.getBounds().contains(e.getPoint());
        if(!hovering || hasHoveredOver){ return false; }

        InteractableGuiElement iel = (InteractableGuiElement) el;

        iel.setHovering(true);
        iel.onHover();

        this.hoveredOnSomething = true;
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
