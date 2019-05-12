package ui;

import core.GuiActionCreator;

public class GuiBuilder {

    public static Panel createLoginScreen() {
        VPanel panel =
                GuiFactory.createDefaultCenteredPanel(
                        null, false, Colors.BLUE);

        panel.addElement(GuiFactory.createDefaultPlainText(
                panel, HorizontalAlign.CENTER,
                "Multiplayer Client",
                Colors.YELLOW)
        );

        TextField usernameTextField = GuiFactory.createDefaultTextField(panel, "Username");
        TextField passwordTextField = GuiFactory.createDefaultPasswordField(panel, "Password");

        panel.addElement(usernameTextField);
        panel.addElement(passwordTextField);

        panel.addElement(GuiFactory.createDefaultConnectButton(
                panel,
                GuiActionCreator.createConnectRunnable(
                        usernameTextField, passwordTextField))
        );
        panel.addElement(GuiFactory.createDefaultExitButton(panel));

        panel.shrink();
        return panel;
    }
}
