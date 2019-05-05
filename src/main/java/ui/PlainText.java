package ui;

import core.Game;

import java.awt.*;

public class PlainText extends GuiElement {

    private Panel parent;
    private int fontSize;
    private Color color;
    private String text;
    private Font font;
    private HorizontalAlign align;

    public PlainText(
            Panel parent, HorizontalAlign align,
            String text, int fontSize, Color color) {

        super(parent.x, parent.y, 0, 0);

        this.align = align;
        this.text = text;
        this.parent = parent;
        this.color = color;
        this.fontSize = fontSize;
        this.font = Game.instance
                .getCustomFont()
                .deriveFont(Font.PLAIN, this.fontSize);

        FontMetrics fontMetric = new Canvas().getFontMetrics(font);
        this.setWidth(fontMetric.stringWidth(text));
        this.setHeight(fontMetric.getHeight());
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.setFont(font);

        int xx = x;
        int yy = y + g2d.getFontMetrics().getHeight();

        switch (align) {
            case CENTER:
                break;
            case RIGHT:
                break;
            case LEFT:
                break;
            default:
                break;
        }

        for (String line : text.split("\n")) {
            g2d.drawString(line, xx, yy);
            yy += g.getFontMetrics().getHeight() + TEXT_LINEHEIGHT;
        }
    }

    @Override
    public void tick() { }

}
