package chatserver;

/**
 *
 * @author Robert
 */
public class Message {

    private String recievers;
    private String message;
    private String sender;

    public Message(String recievers, String message, String sender) {
        this.recievers = recievers;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getRecievers() {
        return recievers;
    }

    public String getSender() {
        return sender;
    }

}
