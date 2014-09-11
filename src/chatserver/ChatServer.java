package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author simon
 */
public class ChatServer implements Runnable {

    private ServerSocket serverSocket;
    private final MessageHandler messageHandler;

    public static boolean RUNNING;
    private final String ipAddress;
    private final int port;
    private Properties property;

    public ChatServer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        ipAddress = "127.0.0.1";
        port = 8014;

    }

    @Override
    public void run() {
        try {
            openConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (RUNNING) {
            Socket socket;
            try {
                System.out.println("waiting for connection");
                socket = serverSocket.accept();
                ClientHandler h = new ClientHandler(new Connection(socket), messageHandler);
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
        Thread message = new Thread(messageHandler);
        message.start();
        server.start();
        RUNNING = true;
    }

    public void stopServer() throws IOException {
        messageHandler.notifyClients("CLOSE#");
        RUNNING = false;
        this.serverSocket.close();
        System.out.println("closed");
    }

}
