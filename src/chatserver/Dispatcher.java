package chatserver;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Robert
 */
public class Dispatcher implements Runnable {

    private static HashMap<String, HandlerInterface> users;
    private static ArrayBlockingQueue<Message> messages;

    public Dispatcher(HashMap<String, HandlerInterface> users, ArrayBlockingQueue<Message> messages) {
        Dispatcher.users = users;
        Dispatcher.messages = messages;

    }

    @Override
    public void run() {
        while (true) {

            try {
                messages.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void decodeMessage(Message string) {

    }

    public static void sendMessage(Message message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean registrerUser(String name, HandlerInterface handler) {
        if (users.containsKey(name)) {
            users.put(name, handler);
            return true;
        }
        return false;
    }

    public static boolean deleteUser(String name) {
        return users.remove(name) != null;
    }

}
