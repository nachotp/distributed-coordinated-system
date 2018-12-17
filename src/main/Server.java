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
import java.net.URI;

import main.hospital.doctor;

import com.sun.net.httpserver.Headers;

public class Server {

    private static final String AND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";


    static doctor curDoctor;
    HttpServer server;

    public void setDoc(doctor doc){
        curDoctor = doc;
    }

    public void close(){
        server.stop(0);
    }

    public void runServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("Server inicializado.");
        server.createContext("/info", new InfoHandler());
        server.createContext("/heartbeat", new AliveHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    class InfoHandler implements HttpHandler {
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

    class AliveHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            h.add("Content-Type", "application/json");
            Integer exp = Server.this.curDoctor.estudios + Server.this.curDoctor.experiencia;
            String response = exp.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    private void createResponseFromQueryParams(URI uri) {
        //Get the request query
        String query = uri.getQuery();
        if (query != null) {
            System.out.println("Query: " + query);
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0) {
                for (String qParam : queryParams) {
                    String[] param = qParam.split(EQUAL_DELIMITER);
                    
                }
            }
        }

    }
}