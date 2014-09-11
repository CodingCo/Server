package chatroom;

import chatserver.ClientHandler;
import chatserver.Message;

/**
 *
 * @author Grønborg
 */
public interface HandlerIntf {

    public void notifyClients(String message);

    public void notifyReciever(String message, ClientHandler handler);

    public boolean registrerClients(String name, ClientHandler handler);

    public boolean unregistrerClients(String name);

    public void addToMessagePool(Message message) throws InterruptedException;
}
