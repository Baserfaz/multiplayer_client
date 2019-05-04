package ui;

import core.Game;

import java.awt.*;

public class Button extends GuiElement implements InteractableGuiElement {

    private Color fontColor;
    private Color bgColor;
    private int fontSize;
    private String txt;
    private Panel parent;

    private Runnable onClickRunnable, onHoverRunnable;
    boolean isHovering = false;

    public Button(Panel parent, int width, int height,
                  String txt, Color fontColor, Color bgColor, int fontSize,
                  Runnable onClickRunnable, Runnable onHoverRunnable) {
        super(parent.x, parent.y, width, height);

        this.parent = parent;
        this.fontColor = fontColor;
        this.bgColor = bgColor;
        this.fontSize = fontSize;
        this.txt = txt;

        this.onClickRunnable = onClickRunnable;
        this.onHoverRunnable = onHoverRunnable;
    }

    public void render(Graphics g) {
        if(this.isVisible()) {

            Rectangle r = new Rectangle(this.x + this.parent.x,
                    this.y + this.parent.y, this.w, this.h);

            if (this.isHovering) g.setColor(this.bgColor.darker());
            else g.setColor(this.bgColor);

            // render box
            g.fillRect(r.x, r.y, r.width, r.height);

            int txtWidth = g.getFontMetrics().stringWidth(this.txt);
            int txtHeight = g.getFontMetrics().getHeight();

            int centerX = (r.x + r.width / 2) - txtWidth / 2;
            int centerY = (r.y + txtHeight + r.height / 2) - txtHeight / 2;

            // render text inside the button rectangle
            Font font = Game.instance
                    .getCustomFont()
                    .deriveFont(Font.PLAIN, this.fontSize);
            g.setFont(font);
            g.setColor(this.fontColor);
            g.drawString(this.txt, centerX, centerY);
        }
    }

    public void tick() { if(this.isEnabled()) {} }

    @Override
    public void onHover() {
        if(this.isEnabled
                && this.onHoverRunnable != null) {
            this.onHoverRunnable.run();
        }
    }

    @Override
    public void onClick() {
        if (this.isEnabled
                && this.onClickRunnable != null) {
            this.onClickRunnable.run();
        }
    }

    @Override
    public void onUnfocus() { }

    @Override
    public void setHovering(boolean b) {
        this.isHovering = b;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
}
