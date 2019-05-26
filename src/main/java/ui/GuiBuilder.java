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
        Checkbox createNewAccountCheckbox = new Checkbox(
                panel, "Create New Account", false);

        panel.addElement(usernameTextField);
        panel.addElement(passwordTextField);
        panel.addElement(createNewAccountCheckbox);

        panel.addElement(GuiFactory.createDefaultConnectButton(
                panel,
                GuiActionCreator.createConnectRunnable(
                        usernameTextField,
                        passwordTextField,
                        createNewAccountCheckbox)
                )
        );
        panel.addElement(GuiFactory.createDefaultExitButton(panel));

        panel.shrink();
        return panel;
    }

    public static Panel createCharacterSelectionScreen() {

        VPanel mainPanel = GuiFactory.createDefaultCenteredPanel(null, false, Colors.WHITE);
        mainPanel.setH(0.8);
        mainPanel.setW(0.75);

        VPanel titlePanel = GuiFactory.createDefaultCenteredPanel(mainPanel, false, Colors.BLUE);
        titlePanel.addElement(
                GuiFactory.createDefaultPlainText(
                        titlePanel,
                        HorizontalAlign.CENTER,
                        "CHARACTER SELECTION",
                        Colors.BLACK
                )
        );

        HPanel characterPanel = GuiFactory.createDefaultHorizontalPanel(
                mainPanel,
                VerticalAlign.MIDDLE,
                HorizontalAlign.CENTER,
                false,
                Colors.WHITE
        );

        // todo: create character boxes inside characterPanel

        HPanel buttonPanel = GuiFactory.createDefaultHorizontalPanel(
                mainPanel,
                VerticalAlign.MIDDLE,
                HorizontalAlign.RIGHT,
                false,
                Colors.WHITE
        );

        Button logoutButton = GuiFactory.createLogoutButton(buttonPanel);
        Button createCharacterButton = GuiFactory.createCharacterButton(buttonPanel);

        buttonPanel.addElement(logoutButton);
        buttonPanel.addElement(createCharacterButton);

        mainPanel.addElement(titlePanel);
        mainPanel.addElement(characterPanel);
        mainPanel.addElement(buttonPanel);

        mainPanel.shrink();

        return mainPanel;
    }

}
