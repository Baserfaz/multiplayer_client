package pojos;

import java.io.Serializable;

public abstract class BaseMessage implements Serializable {

    protected Command command;

    public BaseMessage(Command command) {
        this.command = command;
    }

    public BaseMessage() {}

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
