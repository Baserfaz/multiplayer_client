package ui;


public interface InteractableGuiElement {
    void onHover();
    void onClick();
    void onUnfocus();
    void setHovering(boolean b);
}
