package ui;

import core.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class Panel extends GuiElement {

    protected Panel parent;
    protected int margin;
    protected Color backgroundColor;
    protected Color borderColor;
    protected int borderThickness;
    protected boolean drawBorders;
    protected boolean isTransparent;

    protected VerticalAlign verticalAlign;
    protected HorizontalAlign horizontalAlign;

    protected List<GuiElement> elements = new ArrayList<>();

    public Panel(
            VerticalAlign va,
            HorizontalAlign ha,
            int width,
            int height,
            Panel parent,
            Color bgColor,
            Color borderColor,
            boolean isTransparent,
            boolean borders,
            int borderThickness,
            int margin) {

        super(width, height);
        this.verticalAlign = va;
        this.horizontalAlign = ha;
        this.parent = parent;
        this.backgroundColor = bgColor;
        this.isTransparent = isTransparent;
        this.drawBorders = borders;
        this.margin = margin;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
    }

    public List<GuiElement> getElements() {
        return elements;
    }

    protected Point calculatePanelAlignmentPos() {

        int xx = x, yy = y;

        if(this.horizontalAlign != null) {
            switch (this.horizontalAlign) {
                case CENTER:
                    xx = (Game.instance.WIDTH / 2) - (w / 2);
                    break;
                case LEFT:
                    xx = margin;
                    break;
                case RIGHT:
                    xx = Game.instance.WIDTH - (w + margin);
                    break;
            }
        }

        if(this.verticalAlign != null) {
            switch (this.verticalAlign) {
                case TOP:
                    yy = margin;
                    break;
                case BOTTOM:
                    yy = Game.instance.HEIGHT - (h + margin);
                    break;
                case MIDDLE:
                    yy = (Game.instance.HEIGHT / 2) - (h / 2);
                    break;
            }
        }

        return new Point(xx, yy);
    }

    @Override
    public void render(Graphics g) {
        if(!isVisible || this.elements.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        Stroke defaultStroke = g2d.getStroke();

        // update our position
        Point pos = this.calculatePanelAlignmentPos();
        x = pos.x;
        y = pos.y;

        // draw rect
        if(!this.isTransparent) {
            g2d.setColor(backgroundColor);
            g2d.fillRect(x, y, w, h);
        }

        if (drawBorders) {
            g2d.setStroke(new BasicStroke(borderThickness));
            g2d.setColor(borderColor);
            g2d.drawRect(x - borderThickness, y - borderThickness,
                    w + borderThickness * 2, h + borderThickness * 2);
            g2d.setStroke(defaultStroke);
        }

        // render all elements inside this panel
        updatePanelItems();

        ListIterator<GuiElement> iter = new ArrayList<>(elements).listIterator();
        while(iter.hasNext()) {
            GuiElement next = iter.next();
            next.render(g);
        }
    }

    // this method shrinks the panel
    // to fit exactly the dimensions of it's children.
    protected void shrink() {
        if(this.elements.isEmpty()) return;
        this.shrinkVertically();
        this.shrinkHorizontally();
    }

    private void shrinkHorizontally() {

        // shrinks the child elements to be maximum of panel width
        for(GuiElement e : this.getElements()) {
            if(e.w > this.w) {
                int diff = e.w - this.w;
                e.w = this.w;
                e.x += diff / 2;
            }
        }
    }

    private void shrinkVertically() {

        // top and bottom margins
        int height = margin;

        if(this instanceof VPanel) {

            // calculate total element height
            for (GuiElement element : this.getElements()) {

                if (element instanceof Panel) {
                    ((Panel) element).shrink();
                }

                height += element.getHeight() + element.getMargin();
            }

        } else {

            // HPanel

            GuiElement highestItem = null;
            for (GuiElement element : this.getElements()) {

                if(highestItem == null) {
                    highestItem = element;
                } else {
                    if(element.getHeight() > highestItem.getHeight()) {
                        highestItem = element;
                    }
                }

                if (element instanceof Panel) {
                    ((Panel) element).shrink();
                }
            }

            height += highestItem != null
                    ? (highestItem.getHeight() + highestItem.margin)
                    : this.margin;
        }

        // shrink the bottom of the panel to fit the content
        this.setHeight(height);
    }

    public abstract void updatePanelItems();

    public GuiElement addElement(GuiElement e) {
        if(!this.elements.contains(e)) {
            this.elements.add(e);
        }
        return e;
    }

    public void removeElement(GuiElement e) {
        this.elements.remove(e);
    }

    @Override
    public void tick() {
        for(GuiElement e : elements) {
            e.tick();
        }
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }
}
