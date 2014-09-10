package chatserver;

import java.io.PrintWriter;

/**
 *
 * @author Robert & Simon 
 */
public interface HandlerInterface {

    public boolean sendMessage(PrintWriter writer, String Message);

    public boolean recieveMessage(String message);

}
