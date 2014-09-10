package chatserver;

import chatroom.HandlerIntf;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, HandlerIntf {

    private ArrayBlockingQueue<String> messages;
    private HashMap<String, ClientHandler> users;

    public MessageHandler(ArrayBlockingQueue<String> messages, HashMap<String, ClientHandler> users) {
        this.messages = messages;
        this.users = users;
    }

    @Override
    public void notifyClients(String message) {
        for (Map.Entry<String, ClientHandler> entry : users.entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

    @Override
    public void registrerClients(String name, ClientHandler handler) {
        users.put(name, handler);
        notifyClients(name + "is now online");
    }

    @Override
    public void unregistrerClients(String name) {
        users.remove(name);
    }

    @Override
    public void addToMessagePool(String message) throws InterruptedException {
        messages.put(message);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("ready to take message");
                String message = messages.take();
                notifyClients(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
