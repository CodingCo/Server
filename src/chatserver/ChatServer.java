package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.Utility;

/**
 *
 * @author simon
 */
public class ChatServer implements Runnable {

    private ServerSocket serverSocket;
    private MessageHandler messageHandler;

    public static volatile boolean RUNNING;
    private final String ipAddress;
    private final int port;

    Properties property = Utility.initProperties("serverproperties.txt");

    public ChatServer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        ipAddress = property.getProperty("ipaddress");
        port = Integer.parseInt(property.getProperty("port"));
        Utility.setLogFile("");
    }

    @Override
    public void run() {
        try {
            openConnection();

        } catch (IOException e) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, e);
        }
        while (RUNNING) {
            Socket socket;
            try {
                System.out.println("waiting for connection");
                socket = serverSocket.accept();
                ClientHandler h = new ClientHandler(new Connection(socket), messageHandler);
                Thread handlerThread = new Thread(h);
                handlerThread.start();
            } catch (IOException e) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, e);
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
        RUNNING = true;
        Thread serverThread = new Thread(this);
        Thread message = new Thread(messageHandler);
        message.start();
        System.out.println("message " + message.getName());
        serverThread.start();
        System.out.println("server " + serverThread.getName());
    }

    public void stopServer() throws IOException {
        messageHandler.notifyClients("CLOSE#");
        RUNNING = false;
        messageHandler.stop();
        this.serverSocket.close();
        System.out.println("closed");
    }

}
