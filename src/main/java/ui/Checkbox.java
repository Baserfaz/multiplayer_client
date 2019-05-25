package ui;

import core.Game;

import java.awt.*;

public class Checkbox extends GuiElement implements InteractableGuiElement {

    private boolean isSelected;
    private String label;

    private Font labelFont, checkboxFont;
    private FontMetrics checkboxFontMetrics;

    private final int checkboxHeight = 16, checkboxWidth = 16;

    public Checkbox(Panel parent, String label, boolean isSelected) {
        super(parent);
        this.isSelected = isSelected;
        this.label = label;

        this.labelFont = Game.instance.getCustomFont().deriveFont(Font.PLAIN, 24);
        this.checkboxFont = Game.instance.getCustomFont().deriveFont(Font.PLAIN, 24);

        this.h = checkboxHeight;

        // todo: hard coded value for single character width
        this.w = checkboxWidth + label.length() * 10;
    }

    @Override
    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if(this.checkboxFontMetrics == null) {
            this.checkboxFontMetrics = g.getFontMetrics(checkboxFont);
        }

        // render box
        g.setColor(Colors.BLACK);
        g.fillRect(x, y, checkboxWidth, checkboxHeight);

        // render tick mark
        if(isSelected) {
            g.setColor(Colors.YELLOW);
            g.setFont(checkboxFont);
            g.drawString(
                    "X",
                    x + checkboxWidth / 2 - checkboxFontMetrics.stringWidth("X") / 2,
                    y + checkboxHeight / 2 + checkboxFontMetrics.getHeight() / 2);
        }

        // render label
        if(label != null) {
            g2d.setFont(labelFont);
            g2d.setColor(Colors.BLACK);
            g2d.drawString(
                    label,
                    x + margin * 2,
                    y + checkboxHeight - 2 // todo: hard coded margin value
            );
        }
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
