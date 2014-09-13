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
}
