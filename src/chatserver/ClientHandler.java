package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Robert
 */
public class ClientHandler implements HandlerInterface, Runnable {

    private final Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private String userName;
    private boolean running;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void openStreams(Socket client) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.output = new PrintWriter(client.getOutputStream());
        System.out.println("Client is being handled ");
    }

    @Override
    public void run() {
        try {
            openStreams(client);

            while (this.running) {
                String message = input.readLine();
                System.out.println(message);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void Connect(String protocol) {
        if (protocol.startsWith("CONNECT#")) {
            String name = protocol.replace("CONNECT#", "");
            Dispatcher.registrerUser(name, this);
            Dispatcher.sendMessage(null);
        }

    }

    public void decodeProtocol(String protocol) {

        if (protocol.startsWith("CONNECT#")) {
            String name = protocol.replace("CONNECT#", "");
            Dispatcher.registrerUser(name, this);
            Dispatcher.sendMessage(null);

        } else if (protocol.startsWith("SEND#")) {
            String[] tokens = protocol.split("#");
            Dispatcher.sendMessage(new Message(tokens[1], tokens[2], this.userName));

        } else if (protocol.startsWith("CLOSE#")) {
            try {
                Dispatcher.sendMessage(null);
                Dispatcher.deleteUser(userName);
                this.closeConnection();
                this.running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void closeConnection() throws IOException {
        this.input.close();
        this.output.close();
        this.client.close();
        System.out.println("Gracefull shutdown");
    }

    @Override
    public void sendMessage(String Message) {
        this.output.println(Message);
    }

}
