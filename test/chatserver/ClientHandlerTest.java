/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

import java.io.PrintWriter;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author simon
 */
public class ClientHandlerTest {
    
    
    
    private ClientHandler handler;
    private SocketMock mock;
    
    public ClientHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mock = new SocketMock();
        handler = new ClientHandler(mock);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openStreams method, of class ClientHandler.
     */
    @Test
    public void testOpenStreams() {
        System.out.println("openStreams");
        Socket client = null;
        ClientHandler instance = null;
        instance.openStreams(client);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of connect method, of class ClientHandler.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        String protocol = "";
        ClientHandler instance = null;
        instance.connect(protocol);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class ClientHandler.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        ClientHandler instance = null;
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class ClientHandler.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        PrintWriter writer = null;
        String Message = "";
        ClientHandler instance = null;
        boolean expResult = false;
        boolean result = instance.sendMessage(writer, Message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recieveMessage method, of class ClientHandler.
     */
    @Test
    public void testRecieveMessage() {
        System.out.println("recieveMessage");
        String message = "";
        ClientHandler instance = null;
        boolean expResult = false;
        boolean result = instance.recieveMessage(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
