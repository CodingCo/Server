package chatserver;

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
public class ProtocolTest {
    
    public ProtocolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of close method, of class Protocol.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        assertEquals(Protocol.CLOSE, "CLOSE#");
    }

    /**
     * Test of connect method, of class Protocol.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        assertEquals(Protocol.CONNECT, "CONNECT#");
    }

    /**
     * Test of send method, of class Protocol.
     */
    @Test
    public void testSend() {
        System.out.println("send");
	
	ConnectionMock cm;
	MessageHandlerMock mhm;
        Message m = new Message(new ClientHandler(cm = new ConnectionMock(),mhm = new MessageHandlerMock()), "SEND#Per#Hello", "Person");
	String[] testArr = Protocol.send(m);
	assertEquals(testArr[0], "MESSAGE#Person#Hello");
    }
    
}
