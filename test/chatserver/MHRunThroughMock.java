package chatserver;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author simon
 */
public class MHRunThroughMock extends MessageHandler {

    public boolean client = false;
    public boolean all = false;

    public MHRunThroughMock(ArrayBlockingQueue<Message> messages, HashMap<String, ClientHandler> users) {
        super(messages, users);
    }

    @Override
    public void notifyClients(String message) {
        client = true;
    }

    @Override
    public void notifyReciever(String message, ClientHandler handler) {
        all = true;
    }

}
