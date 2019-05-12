package core;

import pojos.Command;
import pojos.GenericResultPojo;
import pojos.User;
import ui.GuiFactory;
import ui.Panel;
import ui.TextField;

public class GuiActionCreator {

    public static Runnable createConnectRunnable(TextField username, TextField password) {
        return () -> {
            if(!Game.instance.getServerConnection().isConnecting()) {

                Panel loadingPane = GuiFactory.createLoadingPane("Connecting...");

                boolean success = false;
                try {
                    success = Game.instance.getServerConnection().connect();
                } catch (IllegalStateException e) {
                    GuiFactory.createMessagePanel(e.getMessage());
                }

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
                        if(!resultPojo.isSuccess()) {
                            GuiFactory.createMessagePanel(
                                    "Failed to login: invalid username & password."
                            );
                        }
                    }
                }

                loadingPane.disable();

            } else {
                System.out.println("Already trying to connect...");
            }
        };
    }

}
