package ui;

public class GuiBuilder {

    public static Panel createMainMenu() {
        VPanel panel =
                GuiFactory.createDefaultCenteredPanel(
                        null, false, Colors.BLUE);

        panel.addElement(GuiFactory.createDefaultPlainText(
                panel, HorizontalAlign.CENTER,
                "Multiplayer Client", Colors.YELLOW)
        );

        panel.addElement(GuiFactory.createDefaultTextField(panel));
        panel.addElement(GuiFactory.createDefaultPasswordField(panel));

        panel.addElement(GuiFactory.createDefaultConnectButton(panel));
        panel.addElement(GuiFactory.createDefaultExitButton(panel));

        panel.shrink();
        return panel;
    }
}
