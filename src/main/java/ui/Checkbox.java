package ui;

import java.awt.*;

public class Checkbox extends GuiElement implements InteractableGuiElement {

    private boolean isSelected;
    private Panel parent;

    public Checkbox(Panel parent, String label, boolean isSelected) {
        super(parent);
        this.isSelected = isSelected;
        this.parent = parent;
    }

    @Override
    public void render(Graphics g) {

        // render box
        g.drawRect();

        // render tick mark

        // render label

    }

    @Override
    public void tick() { }

    @Override
    public void onClick() {
        isSelected = !isSelected;
    }

    @Override
    public void onHover() {}

    @Override
    public void onUnfocus() {}

    @Override
    public void setHovering(boolean b) {}

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
