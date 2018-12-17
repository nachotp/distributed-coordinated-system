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
import main.hospital.paciente;

import com.sun.net.httpserver.Headers;

public class Server {

    private static final String AND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";
    public static Client cliente;

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
        server.createContext("/pushed", new PushHandler());
        server.createContext("/heartbeat", new AliveHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public boolean tryLock(int id){
        paciente pac = Main.this.listaPacientes.get(id-1);
        if (pac.locked)
            return false;
        pac.locked = true;
        Main.this.listaPacientes.set(id - 1, pac);
        return true;
    }  
    
    public void makeChanges(int id,String accion, String opcion){
        paciente pac = cte.listaPacientes.get(id-1);
        switch (accion) { //ver cual es el procedimiento
            case "1":
                pac.medRecetados.add(opcion);
                break;
            case "2":
                pac.medSuministrados.add(opcion);
                break;
            case "3":
                pac.procCompletados.add(opcion);
                break;
            case "4":
                pac.procAsignados.add(opcion);
                break;
            case "5":
                pac.examNorealizados.add(opcion);
                break;
            case "6":
                pac.examRealizados.add(opcion);
                break;
        }
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
            makeChanges(Integer.valueOf(data.get("id")), (String)data.get("accion"),(String)data.get("opcion"));
            boolean success = tryLock(Integer.valueOf(data.get("id")));
            String response = String.valueOf(success);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

            if (success)
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