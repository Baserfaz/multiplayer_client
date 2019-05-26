package ui;

import core.Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class GuiElement {

    public final int TEXT_LINEHEIGHT = 2;

    protected int margin;
    protected int x, y, w, h, z;
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
        this.z = 0;

        this.margin = 15;
    }

    public abstract void render(Graphics g);
    public abstract void tick();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setW(double w) {
        if(w < 0) { this.w = 0;
        } else if(w > 1.0) { this.w = Game.instance.WIDTH; }

        this.w = new BigDecimal(Game.instance.WIDTH)
                .multiply(new BigDecimal(w).setScale(2, RoundingMode.HALF_UP))
                .intValue();
    }

    public void setH(double h) {
        if(h < 0) { this.h = 0;
        } else if(h > 1.0) { this.h = Game.instance.HEIGHT; }

        this.h = new BigDecimal(Game.instance.HEIGHT)
                .multiply(new BigDecimal(h).setScale(2, RoundingMode.HALF_UP))
                .intValue();
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Rectangle getBounds() { return new Rectangle(this.x, this.y, this.w, this.h); }

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
