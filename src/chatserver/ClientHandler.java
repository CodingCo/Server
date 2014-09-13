package chatserver;

import serverinterfaces.IHandler;
import serverinterfaces.IClient;
import serverinterfaces.IConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ClientHandler implements Runnable, IClient {

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
            String message;

            while ((message = connection.read()) != null) {
                messageHandler.addToMessagePool(new Message(this, message, name));
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, ex.toString());
        } finally {
            secureExit();
        }
    }

    public void secureExit() {
        try {
            messageHandler.addToMessagePool(new Message(this, "CLOSE#", name));
        } catch (InterruptedException e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public void sendMessage(String message) {
        this.connection.write(message);
    }

    @Override
    public void closeConnection() {
        connection.close();
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, (name + " disconnected from server"));
    }

}
