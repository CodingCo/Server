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
        String expResult = "";
        String result = Protocol.close();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connect method, of class Protocol.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        String expResult = "";
        String result = Protocol.connect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class Protocol.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        Message message = null;
        String[] expResult = null;
        String[] result = Protocol.send(message);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
