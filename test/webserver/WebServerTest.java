/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert
 */
public class WebServerTest {

    public WebServerTest() {
        WebServer ws;
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        
        
    }

    @Test
    public void testStartServer() {

        WebServer ws = new WebServer();
        
        WebServer.startServer();
        
        //do something
        
        //måske mock
                fail("The test case is a prototype.");
    }
    
        @Test
    public void testGetContentType() {

        WebServer ws = new WebServer();
              
        //do something
        
        //øøhh private variable
                fail("The test case is a prototype.");
    }

}
