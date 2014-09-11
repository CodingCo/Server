package chatserver;

/**
 *
 * @author simon
 */
public class Protocol {

    public static final String CONNECT = "CONNECT#";
    public static final String CLOSE = "CLOSE#";
    public static final String SEND = "SEND#";
    public static final String ONLINE = "ONLINE#";
    public static final String MESSAGE = "MESSAGE#";
    public static final String ALL = "*";

    public static String close() {
        return CLOSE;

    }

    public static String connect() {
        return ONLINE;
    }

    public static String[] send(Message message) {
        String input = message.getMessage();
        String[] tokens = input.replaceFirst(SEND, "").split("#", 2);
        for (String string : tokens) {
            System.out.println(string);
        }
        if (tokens[0].equals(ALL)) {
            return new String[]{(MESSAGE + message.getSender() + "#" + tokens[1])};
        } else {
            String[] info = new String[2];
            info[0] = (MESSAGE + message.getSender() + "#" + tokens[1]);
            info[1] = tokens[0];
            return info;
        }
    }

}
