package chatserver;

import exceptions.CloseSuccesException;
import java.io.IOException;

/**
 *
 * @author simon
 */
public interface IConnection {

    public void write(String input);

    public String read() throws IOException;

    public void close() throws CloseSuccesException;

    public void open() throws IOException;

}
