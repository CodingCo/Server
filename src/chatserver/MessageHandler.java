package chatserver;

import serverinterfaces.IHandler;
import serverinterfaces.IClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, IHandler {

    private final ArrayBlockingQueue<Message> messages;
    private final HashMap<String, IClient> users;
    private boolean running = true;

    public MessageHandler(ArrayBlockingQueue<Message> messages, HashMap<String, IClient> users) {
        this.messages = messages;
        this.users = users;
    }

    public boolean registrerClients(String name, IClient client) {
        users.put(name, client);
        return users.containsKey(name);
    }

    public boolean unregistrerClients(String name) {
        users.remove(name);
        return users.containsKey(name);
    }

    public void notifyReciever(String message, IClient client) {
        client.sendMessage(message);
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
    public synchronized String getUsers() {
        if (!users.isEmpty()) {
            StringBuilder b = new StringBuilder();
            users.entrySet().stream().forEach((entry) -> {
                b.append(entry.getKey());
                b.append(",");
            });
            return b.substring(0, b.length() - 1);
        }
        return "";
    }

    @Override
    public void run() {
        try {
            while (running) {
                Message m = messages.take();
                String message = m.getMessage();
                int command = Protocol.CheckMessage.findCommand(message);
                switch (command) {
                    case 1:
                        connect(m);
                        break;
                    case 2:
                        send(m);
                        break;
                    case 3:
                        close(m);
                        break;
                    default:
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, ("invalid message: " + message));
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, ex.toString());
        }
    }

    public void connect(Message m) {
        String tmp = Protocol.CheckMessage.getConnect(m.getMessage());
        if (tmp != null) {
            registrerClients(tmp, m.getIClient());
            notifyUsers(Protocol.ONLINE + getUsers());
        }

    }

    public void send(Message m) {
        String tmp = Protocol.CheckMessage.getSend(m.getMessage());
        if (tmp != null) {
            if (tmp.startsWith("*")) {
                notifyUsers(tmp.replaceFirst("*", ""));
            } else {
                String[] value = tmp.split(tmp, 1);
                for (String names : tmp.split(",")) {
                    //  notifyReciever(tmp[1], users.get(names));
                }
            }
        }
    }

    
    // brug name
    public void close(Message m) {
        for (Map.Entry<String, IClient> entry : users.entrySet()) {
            if (entry.getValue().equals(m.getIClient())) {
                m.getIClient().sendMessage(Protocol.CLOSE);
                unregistrerClients(entry.getKey());
                notifyUsers(Protocol.ONLINE + getUsers());
            }
        }
    }

}
