package serverinterfaces;

import chatserver.Message;

/**
 *
 * @author Grønborg
 */
public interface IHandler {

    public void stopMessagePool();
    public void addToMessagePool(Message message) throws InterruptedException;
    public String getUsers(IClient c);
    public void notifyUsers(String message);
}
