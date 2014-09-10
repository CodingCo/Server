package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author simon
 */
public class ServerExecutor {

    static ChatServer server;
    static BufferedReader input;

    public static void main(String[] args) {

        server = new ChatServer();
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
                server.stopServer();
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
