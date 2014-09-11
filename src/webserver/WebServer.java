package webserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

    public static void startServer() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            server.createContext("/", new PageHandler());
            server.createContext("/log", new LogHandler());
            //server.createContext("/users", new UserHandler());
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

    static class LogHandler implements HttpHandler {

        String contentType = "";
        String contentFolder = "logfiles/";
        StringBuilder sb;
        FileReader in;
        BufferedReader br;

        @Override

        public void handle(HttpExchange he) throws IOException {
            
            sb = new StringBuilder();
            in = new FileReader(contentFolder + "serverlog0.txt");
            br = new BufferedReader(in);

            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>Server Log</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            
            sb.append("<table border = 1>\n");
            sb.append("<th>ServerLog</th>");

            String line;
            while ((line = br.readLine()) != null) {
                sb.append("<tr><td>");
                sb.append(line);
                sb.append("</tr></td>");
            }
            
            sb.append("</table>\n");
           
            sb.append("</body>\n");
            sb.append("</html>\n");
            String response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); 
            }
            
            in.close();
            br.close();
        }
    }

    private static String getContentType(String s) {

        String contentType = s.substring(s.lastIndexOf(".") + 1);
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
