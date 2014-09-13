package chatserver;

import serverinterfaces.IHandler;
import serverinterfaces.IClient;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class MessageHandler implements Runnable, IHandler {

    private final ArrayBlockingQueue<Message> messages;
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
    public void addToMessagePool(Message message) throws InterruptedException {
        messages.put(message);
    }

    @Override
    public void stopMessagePool() {
        
    }

    @Override
    public String getUsers(IClient c) {
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
            try {
                Message m = messages.take();
                String input = m.getMessage();
                System.out.println("message is: " + input);
                if (input != null) {
                    if (input.startsWith(Protocol.CONNECT)) {
                        // registrer ClientHandler and client name
                        String name = input.replace(Protocol.CONNECT, "").trim();
                        if (!users.containsValue(m.getClientHandler())) {
                            if (registrerClients(name, m.getClientHandler())) {
                                notifyClients(Protocol.connect() + getOnlineClientNames());
                                m.getClientHandler().setName(name);
                                System.out.println("user: " + name + " is online");
                            } else {
                                notifyReciever(Protocol.close(), m.getClientHandler());
                            }
                        } else {
                            m.getClientHandler().sendMessage("MESSAGE#server# you are already online");
                        }
                    }
                    if (users.containsKey(m.getSender())) {
                        if (input.startsWith(Protocol.SEND)) {
                            String[] x = Protocol.send(m);
                            if (x.length == 1) {
                                notifyClients(x[0]);
                            } else {
                                for (String string : x[1].split(",")) {
                                    if (users.containsKey(string)) {
                                        notifyReciever(x[0], users.get(string));
                                    }
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
                }
            } catch (InterruptedException e) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, e);
                return;
            }
        }
    }

    @Override
    public void notifyUsers(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class ProtocolCheck {

        String message;

        public ProtocolCheck(String message) {
            this.message = message;
        }

        public String getConnect() {
            StringBuilder sb = new StringBuilder();
            String tmp;
            if (message.startsWith(Protocol.CONNECT)) {
                tmp = message.replace(Protocol.CONNECT, "");
                if (tmp.trim().equals("")) {
                    message = "@";
                } else {
                    sb.append(Protocol.CONNECT);
                }
            }

            return null;
        }

    }

}
