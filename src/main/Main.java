package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

public class Main {

    public static void main (String args[]) {
        Server serversito = new Server();
        Client clientillo = new Client();
        Scanner scanner = new Scanner(System.in);
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("data/funcionarios.JSON"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray Doctors = (JSONArray) jsonObject.get("Doctor");
            Iterator<JSONObject> iterator = Doctors.iterator();
			while (iterator.hasNext()) {
                System.out.println(iterator.next().toJSONString());
            }
        } catch (Exception e) {
			//manejo de error
		}

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