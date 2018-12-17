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
    public Client cliente;

    static doctor curDoctor;
    HttpServer server;

    public void setDoc(doctor doc){ // Doctor actual realizando procedimientos
        curDoctor = doc;
    }

    public void close(){ // Detiene el servidor
        server.stop(0);
    }

    Server(Client cle){
        cliente = cle;
    }

    public void runServer() throws Exception { // Inicia servidor y crea rutas
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("Server inicializado.");
        server.createContext("/commit", new CommitHandler());
        server.createContext("/heartbeat", new AliveHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    class CommitHandler implements HttpHandler { //Maneja solicitud de c ambios
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            URI uri = t.getRequestURI();
            HashMap<String,String> data = paramDeserializer(uri.getQuery());
            String response = "true";

            Iterator it = data.entrySet().iterator();
            String serialized = "?";
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    class AliveHandler implements HttpHandler { //Responde con el doctor actual para saber que esta vivo y seleccionar coordinador
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


    private HashMap<String, String> paramDeserializer(String query) { // Toma queryGet y la transforma en un HashMap
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