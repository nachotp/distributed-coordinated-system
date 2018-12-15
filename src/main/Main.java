package main;

import java.util.*;

public class Main {

    public static void main (String args[]) {
        Server serversito = new Server();
        Client clientillo = new Client();
        Scanner scanner = new Scanner(System.in);
        
        try {
            serversito.runServer();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String input = scanner.nextLine();
        try {
            clientillo.runClient();
        } catch (Exception e) {
            
        }
        
        System.out.println("SOS\n");
    }

}