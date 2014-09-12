package webserver;

import chatserver.MessageHandler;
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

    public void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
            server.createContext("/", new PageHandler());
            server.createContext("/log", new LogHandler());
            server.createContext("/users", new UserHandler());
            server.setExecutor(null); // Use the default executor
            server.start();
            System.out.println("Server started, listening on port: " + port);
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeHttpServer() {
        this.server.stop(5);
        System.out.println("webserver closed");
    }

    static class PageHandler implements HttpHandler {

        String contentType = "";
        String contentFolder = "public/";

        @Override

        public void handle(HttpExchange he) throws IOException {

            String fileName = he.getRequestURI().getPath().substring(1);
            File file;

            if (fileName.isEmpty() || fileName.equals("/")) {
                file = new File(contentFolder + "index.html");
            } else {
                contentType = getContentType(fileName);
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

            File first = new File("public/" + "admin1.html");
            File second = new File("public/" + "admin2.html");
            File third = new File("public/" + "admin3.html");
            BufferedReader reader = new BufferedReader(new FileReader(first));
            StringBuilder hbr = new StringBuilder();

            String input;
            while ((input = reader.readLine()) != null) {
                hbr.append(input);
            }
            String line;
            while ((line = br.readLine()) != null) {
                hbr.append("<li class=\"list-group-item\">");
                hbr.append(line);
                hbr.append("</li>");
            }
            reader = new BufferedReader(new FileReader(second));
            while ((input = reader.readLine()) != null) {
                hbr.append(input);
            }

            String onlineUsers = MessageHandler.getUserSize();
            if (onlineUsers.equals("")) {
                hbr.append("<li class=\"list-group-item list-group-item-danger \">");
                hbr.append("no users online");
                hbr.append("</li>");
            } else {
                for (String name : onlineUsers.split(",")) {
                    hbr.append("<li class=\"list-group-item list-group-item-success \">");
                    hbr.append(name);
                    hbr.append("</li>");
                }

            }
            reader = new BufferedReader(new FileReader(third));
            while ((input = reader.readLine()) != null) {
                hbr.append(input);
            }

            String response = hbr.toString();
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

    static class UserHandler implements HttpHandler {

        String contentType = "";
        String[] names;
        StringBuilder sb;

        @Override

        public void handle(HttpExchange he) throws IOException {

            sb = new StringBuilder();

            /*if (!MessageHandler.getUsernames().isEmpty()) {
             Set<String> userNames = MessageHandler.getUsernames();
             names = (String[]) userNames.toArray();
             }*/
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>Online Users</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            /*
             sb.append("<table border = 1>\n");
             sb.append("<th colspan = 10>UserNames</th>");

             for (int i = 0; i < names.length; i++) {
             if (i == 0 || i % 10 == 0) {
             sb.append("<tr>");
             }

             sb.append("<td>");
             sb.append(names[i]);
             sb.append("</td>");

             if (i == 0 || i % 10 == 0) {
             sb.append("</tr>");
             }
             }
             */
            sb.append("<p>");
            sb.append("Users Online: ");
            sb.append(MessageHandler.getUserSize());
            sb.append("</p>");
            sb.append("</body>\n");
            sb.append("</html>\n");
            String response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response);
            }
        }
    }

    private static String getContentType(String s) {

        String contentType = s.substring(s.lastIndexOf(".") + 1);
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
