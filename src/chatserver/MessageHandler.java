package chatserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, IHandler {

    private final ArrayBlockingQueue<Message> messages;
    private final HashMap<String, ClientHandler> users;

    public MessageHandler(ArrayBlockingQueue<Message> messages, HashMap<String, ClientHandler> users) {
        this.messages = messages;
        this.users = users;

    }

    @Override
    public void notifyReciever(String message, ClientHandler handler) {
        handler.sendMessage(message);
    }

    @Override
    public void notifyClients(String message) {
        users.entrySet().stream().forEach((entry) -> {
            entry.getValue().sendMessage(message);
        });
    }

    @Override
    public boolean registrerClients(String name, ClientHandler handler) {
        users.put(name, handler);
        return users.containsKey(name);
    }

    @Override
    public boolean unregistrerClients(String name) {
        users.remove(name);
        return users.containsKey(name);
    }

    @Override
    public void addToMessagePool(Message message) throws InterruptedException {
        messages.put(message);
    }

    public String getOnlineClientNames() {
        StringBuilder b = new StringBuilder();
        users.entrySet().stream().forEach((entry) -> {
            b.append(entry.getKey());
            b.append(",");
        });
        return b.substring(0, b.length() - 1);
    }

    @Override
    public void run() {
        while (ChatServer.RUNNING) {
            try {

                Message m = messages.take();
                String input = m.getMessage();
                if (input.startsWith(Protocol.CONNECT)) {
                    // registrer ClientHandler and client name
                    String name = input.replace(Protocol.CONNECT, "");
                    if (registrerClients(name, m.getClientHandler())) {
                        notifyClients(Protocol.connect() + getOnlineClientNames());
                        m.getClientHandler().setName(name);
                    } else {
                        notifyReciever(Protocol.close(), m.getClientHandler());
                    }
                }
                if (users.containsKey(m.getSender())) {
                    if (input.startsWith(Protocol.SEND)) {
                        String[] x = Protocol.send(m);
                        if (x.length == 1) {
                            notifyClients(x[0]);
                        } else {
                            for (String string : x[1].split(",")) {
                                notifyReciever(x[0], users.get(string));
                            }
                        }
                    }
                    if (input.startsWith(Protocol.CLOSE)) {

                        m.getClientHandler().sendMessage(Protocol.close());
                        unregistrerClients(m.getSender());
                        m.getClientHandler().closeConnection();
                        if (!users.isEmpty()) {
                            notifyClients(Protocol.ONLINE + getOnlineClientNames());
                        }
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
