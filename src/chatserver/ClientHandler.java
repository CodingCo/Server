package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ClientHandler implements HandlerInterface, Runnable {

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void openStreams(Socket client) {
        try {
            this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.output = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // CONNECT
    // SEND 
    //CLOSE
    @Override
    public void run() {
        try {

            String message = input.readLine();
            connect(message);

            while (true) {

            }
        } catch (IOException ex) {

        }
    }

    public void connect(String protocol) {
        boolean connected = false;
        if (protocol.contains("CONNECT#")) {
            String name = protocol.replace("CONNECT#", "");
            // static connect
            connected = true;
        }
        if (connected) {
            // send message to all about connected
        }

    }

    public void closeConnection() {
        try {
            this.input.close();
            this.output.close();
            this.client.close();
        } catch (IOException ex) {
            System.out.println("error in gracefull shutdown");
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public boolean sendMessage(PrintWriter writer, String Message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean recieveMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
