package ui;

import core.Game;

import java.awt.*;

public class TextField extends GuiElement implements InteractableGuiElement {

    private final int borderMargin = 2;
    private final Color fontColor = Colors.BLACK;

    private String value = "";
    private String label;

    private Panel parent;
    private Font font;
    private Font labelFont;
    private FontMetrics fontMetrics;

    private boolean isHovering = false;
    private boolean isSelected = false;
    private boolean isEditable;
    private boolean isPassword;
    private int textMargin;
    private int fontSize;
    private int maxLength;
    private int borderThickness;

    public TextField(Panel panel, int w, int h,
                     int textMargin, int fontSize, int maxlen,
                     boolean isEditable, boolean isPassword,
                     String label) {
        super(panel.x, panel.y, w, h);

        this.maxLength = maxlen;
        this.isEditable = isEditable;
        this.isPassword = isPassword;
        this.parent = panel;
        this.textMargin = textMargin;
        this.fontSize = fontSize;
        this.label = label;

        this.borderThickness = borderMargin * 2;

        this.font = Game.instance.getCustomFont().deriveFont(Font.PLAIN, fontSize);
        this.fontMetrics = new Canvas().getFontMetrics(font);

        this.labelFont = Game.instance.getCustomFont().deriveFont(Font.PLAIN, (float) fontSize / 2f);

        if(this.label != null) {
            this.margin = labelFont.getSize() + borderThickness;
        }
    }

    @Override
    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // use temp variables
        int xx = x;
        int yy = y;

        this.renderButtonRect(g2d, xx, yy);
        this.renderBorders(g2d, xx, yy);
        this.renderValue(g2d, xx, yy);
        this.renderLabel(g2d, xx, yy);
    }

    private void renderButtonRect(Graphics2D g2d, int xx, int yy) {
        g2d.setColor(Colors.WHITE);
        g2d.fillRect(xx, yy, w, h);
    }

    private void renderBorders(Graphics2D g2d, int xx, int yy) {

        // cache default stroke
        Stroke oldStroke = g2d.getStroke();

        // set the border thickness
        g2d.setStroke(new BasicStroke(borderThickness));

        // borders
        g2d.setColor(isSelected ? Colors.YELLOW : Colors.GRAY);
        g2d.drawRect(xx - borderMargin, yy - borderMargin,
                w + (borderMargin * 2), h + (borderMargin * 2));

        // set the old stroke
        g2d.setStroke(oldStroke);
    }

    private void renderLabel(Graphics2D g2d, int xx, int yy) {
        if(label != null) {
            g2d.setFont(labelFont);
            g2d.setColor(fontColor);
            g2d.drawString(
                    label,
                    xx,
                    yy + textMargin - borderMargin - labelFont.getSize()
            );
        }
    }

    private void renderValue(Graphics2D g2d, int xx, int yy) {
        g2d.setFont(font);
        g2d.setColor(fontColor);
        g2d.drawString(
                this.isPassword ? obfuscateValueString() : value,
                xx + textMargin,
                yy + textMargin + (this.fontMetrics.getHeight() / 2)
        );
    }

    private String obfuscateValueString() {
        String val = "";
        for (int i = 0; i < value.length(); i++) {
            val += "*";
        }
        return val;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getLabel() {
        return label;
    }
}
