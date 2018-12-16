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

import main.hospital.doctor;

import com.sun.net.httpserver.Headers;

public class Server {

    static doctor curDoctor;

    public void setDoc(doctor doc){
        curDoctor = doc;
    }

    public void runServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
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

}