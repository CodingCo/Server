package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author simon
 */
public class ServerExecutor {

    static ChatServer server;
    static BufferedReader input;

    public static void main(String[] args) {
        ArrayBlockingQueue messageQue = new ArrayBlockingQueue(100);
        MessageHandler msgHandler = new MessageHandler(messageQue, new HashMap());
        server = new ChatServer(msgHandler);
        server.startServer();
        serverCommands();
    }

    public static void serverCommands() {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        try {
            String command = r.readLine();
            do {
                switchCommands(command);
            } while (!(command = r.readLine()).contains("killall"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server shutdown");
    }

    private static void switchCommands(String command) {
        switch (command) {
            case "stop server":
                try {
                    server.stopServer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "start server":
                server.startServer();
                break;
            case "status":
                System.out.println("running");
                break;
            default:
                if (!command.equals("killall")) {
                    System.out.println("unknow command");
                }
        }
    }

}
