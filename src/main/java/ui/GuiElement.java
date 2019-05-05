package ui;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GuiElement {

    public final int TEXT_LINEHEIGHT = 2;

    protected int margin;
    protected int x, y, w, h;
    protected boolean isEnabled = true, isVisible = true;

    public GuiElement(Panel p) {
        this(p.x, p.y, p.w, p.h);
    }

    public GuiElement(int w, int h) {
        this(0,0, w, h);
    }

    public GuiElement(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.margin = 15;
    }

    public abstract void render(Graphics g);
    public abstract void tick();

    protected void resetMargin() {
        this.margin = 0;
    }

    // ---- GETTERS & SETTERS ----
    public Rectangle getBounds() { return new Rectangle(this.x, this.y, this.w, this.h); }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return w; }
    public void setWidth(int width) { this.w = width; }
    public int getHeight() { return h; }
    public void setHeight(int height) { this.h = height; }

    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
    public boolean isVisible() { return isVisible; }
    public void setVisible(boolean isVisible) { this.isVisible = isVisible; }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
}
