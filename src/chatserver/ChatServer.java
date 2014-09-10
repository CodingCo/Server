package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class ChatServer implements Runnable {

    private ServerSocket serverSocket;
    private boolean running;
    private Thread thread;

    private String ipAddress;
    private int port;

    @Override
    public void run() {

        openConnection();
        while (running) {

            Socket socket;
            try {

                socket = serverSocket.accept();
                Thread clientThread = new Thread();

            } catch (Exception e) {
                if (!running) {
                    stopServer();
                    return;
                }
            }

        }
        System.out.println("server closed");
    }

    public void openConnection() {
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(new InetSocketAddress(ipAddress, port));
            System.out.println("Connection opened on: " + ipAddress + " port: " + port);
        } catch (IOException ex) {
            System.out.println("could not open server");
            ex.printStackTrace();
        }
    }

    public void startServer() {
        if (!thread.isAlive()) {
            this.thread = new Thread(this);
            thread.start();
        } else {
            System.out.println("server already running");
        }
    }

    public void stopServer() {
        try {
            this.running = false;
            this.serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Error in closing server");
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("server closed");
    }

}
