package ui;

import java.awt.*;

public class VPanel extends Panel {

    private HorizontalAlign itemHorAlign;

    public VPanel(
            VerticalAlign va,
            HorizontalAlign ha,
            int w,
            int h,
            Panel parent,
            Color bgColor,
            Color borderColor,
            boolean isTransparent,
            boolean borders,
            int margin,
            HorizontalAlign itemHorAlign) {

        super(
                va,
                ha,
                w,
                h,
                parent,
                bgColor,
                borderColor,
                isTransparent,
                borders,
                2,
                margin);
        this.itemHorAlign = itemHorAlign;
    }

    @Override
    public void updatePanelItems() {

        int currentHeight = 0;

        // we want to put the items inside the panel under each other
        // the items are in the order they were added
        for(GuiElement element : this.getElements()) {

            int xx = x;
            int yy = y + currentHeight + element.getMargin();

            switch (itemHorAlign) {
                case CENTER:
                    xx = (x + w / 2) - element.w / 2;
                    break;
                case LEFT:
                    xx = x + margin;
                    break;
                case RIGHT:
                    xx = x + w - margin - element.w;
                    break;
            }

            element.setX(xx);
            element.setY(yy);

            currentHeight += element.getHeight() + element.getMargin();
        }
    }

    @Override
    public GuiElement addElement(GuiElement e) {
        super.addElement(e);
        this.updatePanelItems();
        return e;
    }
}
