package chatserver;

import chatroom.HandlerIntf;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, HandlerIntf {

    private ArrayBlockingQueue<Message> messages;
    private HashMap<String, ClientHandler> users;

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
        while (true) {
            try {
                Message m = messages.take();
                String input = m.getMessage();

                if (input.startsWith("CONNECT#")) {
                    // registrer ClientHandler and client name
                    if (registrerClients(input.replace("CONNECT#", ""), m.getClientHandler())) {
                        notifyClients("ONLINE#" + getOnlineClientNames());
                    } else {
                        notifyReciever("CLOSE#", m.clientHandler);
                    }
                }
                if (users.containsKey(m.getSender())) {

                    if (input.startsWith("SEND#")) {
                        String[] tokens = input.replace("SEND#", "").split("#", 1);
                        if (tokens[0].equals("*")) {
                            notifyClients("MESSAGE#" + m.getSender() + "#" + tokens[1]);
                        } else {
                            for (String names : tokens[0].split(",")) {
                                notifyReciever("MESSAGE#" + m.getSender() + "#" + tokens[1], users.get(names));
                            }
                        }
                    }

                    if (input.startsWith("CLOSE#")) {
                        m.getClientHandler().sendMessage("CLOSE#");
                        m.getClientHandler().closeConnection();
                        unregistrerClients(m.getSender());
                        notifyClients("ONLINE#" + getOnlineClientNames());
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
