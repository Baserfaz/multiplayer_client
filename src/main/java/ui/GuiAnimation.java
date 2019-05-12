package ui;

import core.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GuiAnimation extends GuiElement {

    private final double NANOSECONDS_TO_MILLISECONDS = 1.0 / 1000000.0;

    private List<BufferedImage> animFrames;
    private int currentFrame;
    private HorizontalAlign horizontalAlign;
    private Panel parent;

    private int animationSpeedInMillis = 100;
    private double timer, last;

    public GuiAnimation(
            Panel parent,
            List<BufferedImage> frames,
            HorizontalAlign horizontalAlign) {
        super(parent);
        this.parent = parent;
        this.horizontalAlign = horizontalAlign;
        this.animFrames = frames;
        this.currentFrame = 0;
        this.timer = 0;
        this.last = System.nanoTime() * NANOSECONDS_TO_MILLISECONDS;
    }

    @Override
    public void render(Graphics g) {
        if(!isVisible || animFrames.size() == 0) return;
        BufferedImage frame = animFrames.get(currentFrame);

        switch (horizontalAlign) {
            case CENTER:
                x = parent.getX() + (parent.getW() / 2) - (Game.CALCULATED_SPRITE_SIZE / 2);
                break;
            case LEFT:
                x = parent.getX() + margin;
                break;
            case RIGHT:
                x = parent.getX() + parent.getW() - (Game.CALCULATED_SPRITE_SIZE + margin);
                break;
        }

        g.drawImage(frame, x, y, null);
    }

    @Override
    public void tick() {
        if(!isEnabled) return;

        double now = System.nanoTime() * NANOSECONDS_TO_MILLISECONDS;
        double passedTime = now - last;

        timer += passedTime;

        if(timer > animationSpeedInMillis) {
            currentFrame ++;
            timer = 0;
            if(animFrames.size() <= currentFrame) {
                currentFrame = 0;
            }
        }

        last = now;
    }
}
