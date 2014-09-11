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
        for (Map.Entry<String, ClientHandler> entry : users.entrySet()) {
            entry.getValue().sendMessage(message);
        }
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

    @Override
    public void run() {
        while (true) {
            try {
                Message m = messages.take();
                String input = m.getMessage();

                if (input.startsWith("CONNECT#")) {
                    // TJEK LIGE OM input indehodler navn
                    if (registrerClients(input, m.getCh())) {
                        // KONVERTER STRING således, tilføj alle navne på strengen
                        notifyClients("ONLINE#");
                    } else {
                        m.getCh().sendMessage("CLOSE#");
                    }
                }

                if (input.startsWith("SEND#")) {

                    // split strengne op i modtagere
                    // afsender
                    // besked
                    if ("".equals("*")) {
                        notifyClients(input);
                    } else {
                        // iterate throughe modtager, check if true, notify 
                    }

                    if (users.containsKey("afsender")) {
                        notifyClients("MESSAGE# resten af beskeden");
                    }
                }

                if (input.startsWith("CLOSE#")) {
                    m.getCh().sendMessage("CLOSE#");
                    m.getCh().closeConnection();
                    unregistrerClients(m.getMessage()); // pull out name
                    notifyClients("ONLINE MESSAGE");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
