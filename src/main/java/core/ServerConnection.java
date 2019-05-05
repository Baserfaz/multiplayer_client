package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String address;
    private int port;

    private boolean connecting;

    public ServerConnection(String address, int port) {
        this.address = address;
        this.port = port;
        connecting = false;
    }

    public void disconnect() {
        if(this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.socket = null;
            }
        }
    }

    public boolean connect() throws IllegalStateException {

        disconnect();
        connecting = true;

        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to connect to server. Server is down.");
        } finally {
            connecting = false;
        }

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.outputStream.flush();
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to create input and output streams.");
        }

        return true;
    }

    public void send(Object obj) {
        try {
            this.outputStream.writeObject(obj);
            this.outputStream.flush();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to send object!");
        }
    }

    public Object receive() {
        Object obj;
        try {
            obj = this.inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException(
                    "Failed to receive object!");
        }
        return obj;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public boolean isConnecting() {
        return connecting;
    }
}
