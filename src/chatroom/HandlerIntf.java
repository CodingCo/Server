package chatroom;

import chatserver.ClientHandler;

/**
 *
 * @author Grønborg
 */
public interface HandlerIntf {

    public void notifyClients(String message);

    public void registrerClients(String name, ClientHandler handler);

    public void unregistrerClients(String name);

    public void addToMessagePool(String message) throws InterruptedException;
}
