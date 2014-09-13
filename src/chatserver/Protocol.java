package chatserver;

/**
 *
 * @author simon
 */
public interface Protocol {

    public static final String CONNECT = "CONNECT#";
    public static final String CLOSE = "CLOSE#";
    public static final String SEND = "SEND#";
    public static final String ONLINE = "ONLINE#";
    public static final String MESSAGE = "MESSAGE#";
    public static final String ALL = "*#";

    static class CheckMessage {

        static int findCommand(String message) {
            if (message.startsWith(CONNECT)) {
                return 1;
            }
            if (message.startsWith(SEND)) {
                return 2;
            }
            if (message.startsWith(CLOSE)) {
                return 3;
            }
            return 0;
        }

        public static String getConnect(String s) {
            if (s.startsWith(CONNECT)) {
                if (!s.replace(CONNECT, "").trim().equals("")) {
                    return s.replace(CONNECT, "").trim();
                }
            }
            return null;
        }

        public static String[] getSend(String s) {
            if (s.startsWith(SEND)) {
                String tmp = s.replace(SEND, "");
                if (!tmp.replaceAll("#", "").trim().equals("")) {
                    String[] tokens = tmp.split("#", 2);
                    if (tokens[0].equals("*")) {
                        
                    }
                    
                }

            }
            return null;
        }

        public static String getClose(String s) {
            return "";
        }

    }
}
