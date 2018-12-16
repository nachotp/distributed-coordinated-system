package main;

import java.util.*;

public class Main {

    public static void main (String args[]) {
        Server serversito = new Server();
        Client clientillo = new Client();
        
        try {
            serversito.runServer();
        } catch (Exception e) {
           
        }
        try {
            clientillo.runClient();
        } catch (Exception e) {
            
        }
    }

}