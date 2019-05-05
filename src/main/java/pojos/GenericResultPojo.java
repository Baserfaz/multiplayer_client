package pojos;

import java.io.Serializable;

public class GenericResultPojo implements Serializable {

    private Command command;
    private Exception exception;
    private String message;
    private boolean success;

    public GenericResultPojo(Exception exception, String message, boolean success, Command command) {
        this.command = command;
        this.exception = exception;
        this.message = message;
        this.success = success;
    }

    public GenericResultPojo() {}

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String print() {
        return "Command: " + command + ", " +
                (exception != null ? "Exception: " + exception.getMessage() : "") +
                (message != null ? "Message" + message : "") +
                success;
    }

}
