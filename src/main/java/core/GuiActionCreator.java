package core;

import pojos.Command;
import pojos.GenericResultPojo;
import pojos.User;
import ui.Checkbox;
import ui.GuiFactory;
import ui.Panel;
import ui.TextField;

public class GuiActionCreator {

    public static Runnable createNewCharacterRunnable() {
        return () -> {

        };
    }

    public static Runnable createLogoutRunnable() {
        return () -> {



        };
    }

    public static Runnable createConnectRunnable(
            TextField usernameField,
            TextField passwordField,
            Checkbox createNewAccountCheckbox) {

        return () -> {

            String username = usernameField.getValue();
            String password = passwordField.getValue();
            boolean createNewAccount = createNewAccountCheckbox.isSelected();

            if(username.trim().isEmpty() || password.trim().isEmpty()) {
                GuiFactory.createMessagePanel("Username and password are both required.");
                return;
            }

            if(!Game.instance.getServerConnection().isConnecting()) {

                // 1.
                Panel loadingPane = GuiFactory.createLoadingPane("Connecting...");

                // 2.
                boolean success = false;
                try {

                    // todo: perhaps this main connection should be somewhere else...
                    success = Game.instance.getServerConnection().connect();
                } catch (IllegalStateException e) {
                    GuiFactory.createMessagePanel(e.getMessage());
                }

                // 3.
                if(success) {

                    System.out.println("Connection successful!");
                    System.out.println("Logging in...");

                    // populate fields from textfields
                    User user = createNewAccount
                            ? new User(Command.CREATE_USER)
                            : new User(Command.LOGIN);

                    user.setUsername(username);
                    user.setPassword(password);

                    // try to send user data to server
                    Game.instance.getServerConnection().send(user);

                    // try to receive response
                    Object receive = Game.instance.getServerConnection().receive();

                    if(receive != null) {
                        if(((GenericResultPojo) receive).isSuccess()) {
                            System.out.println("Login success!");
                            // todo: changing game state here freezes the client
                            Game.instance.setGameState(GameState.CHARACTERSCREEN);
                        } else {
                            GuiFactory.createMessagePanel(
                                    "Failed to login: invalid username & password."
                            );
                        }
                    }
                }

                loadingPane.disable();
            }
        };
    }

}
