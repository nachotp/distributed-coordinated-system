package main;

public class Main {

    public static void main (String args[]) {
        Server serversito = new Server();
        Client clientillo = new Client();
        try {
            serversito.runServer();
        } catch (Exception e) {
            //TODO: handle exception
        }

        try {
            clientillo.runClient();
        } catch (Exception e) {
            
        }
        
        System.out.println("SOS\n");
    }

}