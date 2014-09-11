package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

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

    public ChatServer() {
        this.ipAddress = "127.0.0.1";
        this.port = 8014;
        running = true;
    }

    @Override
    public void run() {

        openConnection();
        while (running) {

            Socket socket;
            try {
                System.out.println("waiting for connection");
                socket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            } catch (Exception e) {

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
        this.thread = new Thread(this);
        this.running = true;
        thread.start();
    }

    public void stopServer() {
// send close til alle clienter
        try {
            this.running = false;
            this.serverSocket.close();
        } catch (IOException ex) {
            System.out.println("closed");
        }
    }

}
