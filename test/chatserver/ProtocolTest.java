package chatserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Grønborg
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

    @Test
    public void testConnect() {
        System.out.println("getConnect test");
        String message = "CONNECT#simon";
        String expected = "simon";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertEquals(expected, actual);
    }

    @Test
    public void testConnect1() {
        System.out.println("getConnect test");
        String message = "CONNECT# olsen";
        String expected = "olsen";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertEquals(expected, actual);
    }

    @Test
    public void testConnect2() {
        System.out.println("getConnect test");
        String message = "CONNECT#CONNECT";
        String expected = "CONNECT";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertEquals(expected, actual);
    }

    @Test
    public void testConnectFail() {
        System.out.println("getConnect test");
        String message = "CONNECT#CONNECT#";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertNull(actual);
    }

    @Test
    public void testConnectFail1() {
        System.out.println("getConnect test");
        String message = "CONNECT#   ";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertNull(actual);
    }

    @Test
    public void testConnectFail2() {
        System.out.println("getConnect test");
        String message = "CONNECT# CONNECT#";
        String actual = Protocol.CheckMessage.getConnect(message);
        assertNull(actual);
    }

    
    @Test
    public void testCommand() {
        System.out.println("command test");
        String invalid = "";
        String connect = Protocol.CONNECT;
        String send = Protocol.SEND;
        String close = Protocol.CLOSE;
        int one = Protocol.CheckMessage.findCommand(invalid);
        int two = Protocol.CheckMessage.findCommand(connect);
        int three = Protocol.CheckMessage.findCommand(send);
        int four = Protocol.CheckMessage.findCommand(close);
        assertEquals(6, (one+two+three+four));
    }
    
    
    
    
    
    
    
    
}
