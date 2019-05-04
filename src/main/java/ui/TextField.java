package ui;

import core.Game;

import java.awt.*;

public class TextField extends GuiElement implements InteractableGuiElement {

    private final int borderMargin = 2;
    private final Color fontColor = Colors.BLACK;

    private String value = "";

    private Panel parent;
    private Font font;
    private FontMetrics fontMetrics;

    private boolean isSelected = false;
    private boolean isEditable;
    private int textMargin;
    private int fontSize;
    private int maxLength;
    private int borderThickness;

    private boolean isHovering = false;

    public TextField(Panel panel, int w, int h,
                     int textMargin, int fontSize, int maxlen,
                     boolean isEditable) {
        super(panel.x, panel.y, w, h);

        this.maxLength = maxlen;
        this.isEditable = isEditable;
        this.parent = panel;
        this.textMargin = textMargin;
        this.fontSize = fontSize;

        this.borderThickness = borderMargin * 2;

        this.font = Game.instance.getCustomFont().deriveFont(Font.PLAIN, fontSize);
        this.fontMetrics = new Canvas().getFontMetrics(font);
    }

    @Override
    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int xx = x;
        int yy = y;

        // cache default stroke
        Stroke oldStroke = g2d.getStroke();

        // render a box
        g2d.setColor(Colors.WHITE);
        g2d.fillRect(xx, yy, w, h);

        // set the border thickness
        g2d.setStroke(new BasicStroke(borderThickness));

        // render borders
        if(isSelected) {

            // make borders glow when selected
            g2d.setColor(Colors.YELLOW);
            g2d.drawRect(xx - borderMargin, yy - borderMargin,
                    w + (borderMargin * 2), h + (borderMargin * 2));

        } else {

            g2d.setColor(Colors.GRAY);
            g2d.drawRect(xx - borderMargin, yy - borderMargin,
                    w + (borderMargin * 2), h + (borderMargin * 2));

        }

        // render text
        g2d.setFont(font);
        g2d.setColor(fontColor);
        g2d.drawString(value, xx + textMargin,
                yy + textMargin + (this.fontMetrics.getHeight() / 2));

        // set the old stroke
        g2d.setStroke(oldStroke);

    }

    @Override
    public void tick() {}

    @Override
    public void onHover() {}

    @Override
    public void onClick() {
        if(!isSelected) isSelected = true;
    }

    @Override
    public void onUnfocus() {
        if(isSelected) {
            isSelected = false;
        }
    }

    @Override
    public void setHovering(boolean b) {
        this.isHovering = b;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

}
