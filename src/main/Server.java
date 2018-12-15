package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public class Server {

    public void runServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("Server inicializado.");
        server.createContext("/info", new InfoHandler());
        server.createContext("/get", new GetHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class InfoHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            h.add("Content-Type", "application/json");
            String response = "{response: 'Use /get to download a PDF'}";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            // add the required response header for a JSON file
            Headers h = t.getResponseHeaders();
            h.add("Content-Type", "application/json");

            // a PDF (you provide your own!)
            File file = new File("c:/temp/doc.pdf");
            byte[] bytearray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytearray, 0, bytearray.length);

            // ok, we are ready to send the response.
            t.sendResponseHeaders(200, file.length());
            OutputStream os = t.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            bis.close();
            os.close();
        }
    }
}