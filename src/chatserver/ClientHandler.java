package chatserver;

import chatroom.HandlerIntf;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Robert
 */
public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private HandlerIntf messageHandler;
    private String name;

    private boolean running = true;

    public ClientHandler(Socket client, HandlerIntf msgHandler) {
        this.client = client;
        this.messageHandler = msgHandler;
    }

    public void openStreams(Socket client) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.output = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            openStreams(client);
            String message;
            while (running) {
                message = input.readLine();
                messageHandler.addToMessagePool(new Message(this, message, name));
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void sendMessage(String Message) {
        this.output.println(Message);
    }

    public void closeConnection() throws IOException {
        this.running = false;
        this.input.close();
        this.output.close();
        this.client.close();
        System.out.println("Gracefull shutdown");
    }

}
