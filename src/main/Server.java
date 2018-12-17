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
import java.util.*;

import main.hospital.doctor;

import com.sun.net.httpserver.Headers;

public class Server {

    private static final String AND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";
    public static Client cliente;

    static doctor curDoctor;
    HttpServer server;

    public void setDoc(doctor doc){
        curDoctor = doc;
    }

    public void close(){
        server.stop(0);
    }

    Server(Client cle){
        cliente = cle;
    }

    public void runServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("Server inicializado.");
        server.createContext("/commit", new CommitHandler());
        server.createContext("/pushed", new PushHandler());
        server.createContext("/heartbeat", new AliveHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void pushChangeIfCoord(HashMap<String,String> data){
        if (cliente.coordinating)
            cliente.pushProcedure(data);
    }
    class CommitHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            URI uri = t.getRequestURI();
            HashMap<String,String> data = paramDeserializer(uri.getQuery());
            // Revisar LOCK

            String response = "true";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();


            pushChangeIfCoord(data);
        }
    }

    class PushHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            URI uri = t.getRequestURI();
            HashMap<String, String> data = paramDeserializer(uri.getQuery());
            String response = "true";
            // CAMBIO AL PACIENTE


            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
    }

    class AliveHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            Integer exp = Server.this.curDoctor.estudios + Server.this.curDoctor.experiencia;
            String response = exp.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    private HashMap<String, String> paramDeserializer(String query) {
        //Get the request query
        HashMap<String, String> data = new HashMap<>();
        if (query != null) {
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0) {
                for (String qParam : queryParams) {
                    String[] param = qParam.split(EQUAL_DELIMITER);
                    data.put(param[0], param[1]);
                }
            }
        }
        return data;
    }
}