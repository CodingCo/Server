package chatserver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ClientHandler implements Runnable {

    private final IConnection connection;
    private final IHandler messageHandler;
    private String name;
    private boolean running;

    public ClientHandler(IConnection connection, IHandler messageHandler) {
        this.connection = connection;
        this.messageHandler = messageHandler;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            connection.open();
            running = true;
        } catch (IOException e) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            String message;
            while (running) {
                message = connection.read();
                messageHandler.addToMessagePool(new Message(this, message, name));
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message) {
        this.connection.write(message);
    }

    public void closeConnection() throws IOException {
        running = false;
        connection.close();
        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, "client: " + name + " disconnected from server");
        System.out.println("connection closed");
    }

}
