package core;

import pojos.Command;
import pojos.GenericResultPojo;
import pojos.User;
import ui.GuiFactory;
import ui.Panel;

public class GuiActionCreator {

    public static Runnable createConnectRunnable(
            String username,
            String password,
            boolean createNewAccount) {

        return () -> {

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
