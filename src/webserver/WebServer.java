package webserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class WebServer {

    private HttpServer server;
    static int port = 8028;
    static String ip = "127.0.0.1";

    public static void main(String[] args) {

        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            server.createContext("/", new PageHandler());
            server.setExecutor(null); // Use the default executor
            server.start();
            System.out.println("Server started, listening on port: " + port);
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class PageHandler implements HttpHandler {

        String contentFolder = "public/";

        @Override

        public void handle(HttpExchange he) throws IOException {
            String contentType = "";
            String fileName = he.getRequestURI().toString().substring(1);
            File file;
            
            switch(contentType){
                case "html":
                    contentType = "text/html";
                    break;
                case "css":
                    contentType = "text/css";
                    break;
                case "pdf":
                    contentType = "application/pdf";
                    break;
                case "jar":
                    contentType = "applikation/zip";
                    break;
                case "png":
                case "jpeg":
                case "jpg":
                case "gif":
                    contentType = "image/"+contentType;
                    break;
            }
            
            if (fileName.isEmpty()) {
                file = new File(contentFolder + "index.html");
            } else {
                file = new File(contentFolder + fileName);
            }
            
            byte[] bytesToSend = new byte[(int) file.length()];
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
            } catch (IOException ie) {
                Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ie);
            }
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", contentType);
            he.sendResponseHeaders(200, bytesToSend.length);

            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }
    }
}
