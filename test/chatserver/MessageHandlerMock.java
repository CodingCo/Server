/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

import serverinterfaces.IClient;
import serverinterfaces.IHandler;
import java.util.ArrayList;

/**
 *
 * @author kasper
 */
public class MessageHandlerMock implements IHandler{

    public ArrayList<Message> meh;
    
    public MessageHandlerMock(){
	meh = new ArrayList();
    }
    
 
    public void notifyClients(String message) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public void notifyReciever(String message, ClientHandler handler) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public boolean registrerClients(String name, ClientHandler handler) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean unregistrerClients(String name) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addToMessagePool(Message message) throws InterruptedException {
	meh.add(message);
    }

    @Override
    public void stopMessagePool() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUsers(IClient c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
