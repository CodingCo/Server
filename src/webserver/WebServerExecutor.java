package webserver;

/**
 *
 * @author Robert
 */
public class WebServerExecutor {

    static WebServer ws;
    
    public static void main(String[] args) {
        
        ws = new WebServer();
        WebServer.startServer();
        
    }
    
    
}
