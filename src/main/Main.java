package main;
import main.hospital.*;
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
        JSONObject iter;
        doctor medico;
        enfermero enf;
        paramedico param;
        List<doctor> listaDoctores= new ArrayList<doctor>();
        List<enfermero> listaEnfermeros= new ArrayList<enfermero>();
        List<paramedico> listaParamedicos= new ArrayList<paramedico>();
        try{
            Object obj = parser.parse(new FileReader("data/funcionarios.JSON"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray doctors = (JSONArray) jsonObject.get("Doctor");
            Iterator<JSONObject> iteratorDoc = doctors.iterator();
            JSONArray enfermeros = (JSONArray) jsonObject.get("Enfermero");
            Iterator<JSONObject> iteratorEnf = doctors.iterator();
            JSONArray paramedicos = (JSONArray) jsonObject.get("Paramedico");
            Iterator<JSONObject> iteratorPar = doctors.iterator();
			while (iteratorDoc.hasNext()) {
                iter = iteratorDoc.next();
                medico = new doctor((int) (long) iter.get("id"),(String) iter.get("nombre"),(String)iter.get("apellido"),(int) (long)iter.get("experiencia"),(int) (long) iter.get("estudios"));
                listaDoctores.add(medico);
            }
            while (iteratorEnf.hasNext()) {
                iter = iteratorEnf.next();
                enf = new enfermero((int) (long) iter.get("id"),(String) iter.get("nombre"),(String)iter.get("apellido"),(int) (long)iter.get("experiencia"),(int) (long) iter.get("estudios"));
                listaEnfermeros.add(enf);
            }
            while (iteratorPar.hasNext()) {
                iter = iteratorPar.next();
                param = new paramedico((int) (long) iter.get("id"),(String) iter.get("nombre"),(String)iter.get("apellido"),(int) (long)iter.get("experiencia"),(int) (long) iter.get("estudios"));
                listaParamedicos.add(param);
            }
            
        } catch (Exception e) {
			e.printStackTrace();
		}

        try {
            serversito.runServer();
        } catch (Exception e) {}

        scanner.nextLine();

        try {
            clientillo.runClient();
        } catch (Exception e) {}
    }

}