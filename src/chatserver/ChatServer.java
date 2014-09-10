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
    private final MessageHandler mh;

    private boolean running;
    private final String ipAddress;
    private final int port;

    public ChatServer(MessageHandler mh) {
        this.mh = mh;
        this.ipAddress = "127.0.0.1";
        this.port = 8014;
        running = true;
    }

    @Override
    public void run() {
        try {
            openConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (running) {
            Socket socket;
            try {
                System.out.println("waiting for connection");
                socket = serverSocket.accept();
                ClientHandler h = new ClientHandler(socket, mh);
                Thread handlerThread = new Thread(h);
                handlerThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("server closed");
    }

    public void openConnection() throws IOException {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(ipAddress, port));
        System.out.println("Connection opened on: " + ipAddress + " port: " + port);

    }

    public void startServer() {
        Thread server = new Thread(this);
        Thread message = new Thread(mh);
        message.start();
        server.start();
        this.running = true;
    }

    public void stopServer() throws IOException {
        this.running = false;
        this.serverSocket.close();
        System.out.println("closed");
    }

}
