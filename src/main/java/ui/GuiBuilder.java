package ui;

import core.Game;
import pojos.Command;
import pojos.GenericResultPojo;
import pojos.User;

public class GuiBuilder {

    public static Panel createMainMenu() {
        VPanel panel =
                GuiFactory.createDefaultCenteredPanel(
                        null, false, Colors.BLUE);

        panel.addElement(GuiFactory.createDefaultPlainText(
                panel, HorizontalAlign.CENTER,
                "Multiplayer Client", Colors.YELLOW)
        );

        TextField usernameTextField = GuiFactory.createDefaultTextField(panel, "Username");
        TextField passwordTextField = GuiFactory.createDefaultPasswordField(panel, "Password");

        panel.addElement(usernameTextField);
        panel.addElement(passwordTextField);

        panel.addElement(GuiFactory.createDefaultConnectButton(
                panel,
                createConnectRunnable(usernameTextField, passwordTextField))
        );
        panel.addElement(GuiFactory.createDefaultExitButton(panel));

        panel.shrink();
        return panel;
    }

    private static Runnable createConnectRunnable(TextField username, TextField password) {
        return () -> {
            if(!Game.instance.getServerConnection().isConnecting()) {
                boolean success = Game.instance.getServerConnection().connect();
                if(success) {

                    System.out.println("Connection successful!");
                    System.out.println("Logging in...");

                    // populate fields from textfields
                    User user = new User(Command.LOGIN);
                    user.setUsername(username.getValue());
                    user.setPassword(password.getValue());

                    // try to send user data to server
                    Game.instance.getServerConnection().send(user);

                    // try to receive response
                    Object receive = Game.instance.getServerConnection().receive();

                    if(receive != null) {
                        GenericResultPojo resultPojo = (GenericResultPojo) receive;
                        System.out.println(resultPojo.print());
                    }
                }
            } else {
                System.out.println("Already trying to connect...");
            }
        };
    }
}
