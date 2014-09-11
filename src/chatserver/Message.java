package chatserver;

/**
 *
 * @author simon
 */
public class Message {

    ClientHandler ch;
    String message;

    public Message(ClientHandler ch, String message) {
        this.ch = ch;
        this.message = message;
    }

    public ClientHandler getCh() {
        return ch;
    }

    public String getMessage() {
        return message;
    }

}
