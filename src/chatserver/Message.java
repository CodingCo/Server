package chatserver;

/**
 *
 * @author simon
 */
public class Message {

    private final ClientHandler clientHandler;
    private final String message;
    private final String sender;

    public Message(ClientHandler ch, String message, String sender) {
        this.clientHandler = ch;
        this.message = message;
        this.sender = sender;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

}
