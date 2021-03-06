package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GuiImage extends GuiElement {

    private BufferedImage img;
    private Panel parent;

    public GuiImage(Panel parent, BufferedImage img) {
        super(parent.x, parent.y, img.getWidth(), img.getHeight());
        this.img = img;
        this.parent = parent;
    }

    public void render(Graphics g) {
        if(!this.isVisible) return;
        int xx = this.x;
        int yy = this.y;
        g.drawImage(this.img, xx , yy, null);
    }

    public void tick() {}
}
