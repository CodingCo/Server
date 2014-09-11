package chatserver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CloseSuccesException;

/**
 *
 * @author Robert
 */
public class ClientHandler implements Runnable {

    private final IConnection connection;
    private final IHandler messageHandler;
    private String name;

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
        } catch (IOException e) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            String message;
            while ((message = connection.read()) != null) {
                messageHandler.addToMessagePool(new Message(this, message, name));
            }
            messageHandler.addToMessagePool(new Message(this, "CLOSE#", name));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    public void sendMessage(String message) {
        this.connection.write(message);
    }

    public void closeConnection() throws IOException {
        connection.close();
        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, "client: " + name + " disconnected from server");
        System.out.println("connection closed");
    }

}
