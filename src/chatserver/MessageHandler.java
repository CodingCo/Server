package chatserver;

import serverinterfaces.IHandler;
import serverinterfaces.IClient;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, IHandler {

    private ArrayBlockingQueue<Message> messages;
    private HashMap<String, ClientHandler> users;
    private boolean running = true;

    public MessageHandler(ArrayBlockingQueue<Message> messages, HashMap<String, ClientHandler> users) {
        this.messages = messages;
        this.users = users;
    }

    public void notifyReciever(String message, ClientHandler handler) {
        handler.sendMessage(message);
    }

    @Override
    public void notifyUsers(String message) {
        users.entrySet().stream().forEach((entry) -> {
            entry.getValue().sendMessage(message);
        });
    }

    @Override
    public synchronized void addToMessagePool(Message message) throws InterruptedException {
        messages.put(message);
    }

    @Override
    public void stopMessagePool() {
        running = false;
        messages.clear();
    }

    @Override
    public synchronized String getUsers(IClient c) {
        StringBuilder b = new StringBuilder();
        users.entrySet().stream().forEach((entry) -> {
            b.append(entry.getKey());
            b.append(",");
        });
        return b.substring(0, b.length() - 1);
    }

    @Override
    public void run() {
        while (running) {
            
        }
    }

    static class CheckMessage {

        public static String getConnect(String s) {
            if (s.startsWith(Protocol.CONNECT)) {
                    if (!s.replace(Protocol.CONNECT, "").trim().equals("")) {
                    return s;
                }
            }
            return null;
        }

    }

}
