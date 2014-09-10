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

        String contentType = "";
        String contentFolder = "public/";

        @Override

        public void handle(HttpExchange he) throws IOException {

            String fileName = he.getRequestURI().getPath().substring(1);
            File file;
            
            if (fileName.isEmpty() || fileName.equals("/")) {
                file = new File(contentFolder + "index2.html");
            } else {
                contentType = getContentType(fileName);
                System.out.println(contentType);
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
    
    private static String getContentType(String s) {

        String contentType = s.substring(s.lastIndexOf(".")+1);
        System.out.println(contentType);

        switch (contentType) {
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "pdf":
                return "application/pdf";
            case "jar":
                return "applikation/zip";
            case "png":
            case "jpeg":
            case "jpg":
            case "gif":
                return "image/" + contentType;       
        }
        return contentType;
    }

}
