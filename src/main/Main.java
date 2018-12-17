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
    public static requerimiento currReq;
    public static boolean coord;
    public static void main (String args[]) {
        Server server = new Server();
        Client cliente = new Client();
        Scanner scanner = new Scanner(System.in);
        JSONParser parser = new JSONParser();
        JSONObject iter;
        JSONObject iter1;
        JSONObject iter2;
        JSONObject iter3;
        JSONObject iter4;
        JSONObject iter5;
        doctor medico;
        enfermero enf;
        paramedico param;
        requerimiento req;
        paciente pac;
        List<doctor> listaDoctores= new ArrayList<doctor>();
        List<enfermero> listaEnfermeros= new ArrayList<enfermero>();
        List<paramedico> listaParamedicos= new ArrayList<paramedico>();
        List<requerimiento> listaRequerimientos= new ArrayList<requerimiento>();
        List<paciente> listaPacientes = new ArrayList<paciente>();
        List<String> enfermedades = new ArrayList<String>();
        List<String> procAsignados = new ArrayList<String>();
        List<String> procCompletados = new ArrayList<String>();
        List<String> examRealizados = new ArrayList<String>();
        List<String> examNorealizados = new ArrayList<String>();
        List<String> medRecetados = new ArrayList<String>();
        List<String> medSuministrados = new ArrayList<String>();

        try{
            Object obj = parser.parse(new FileReader("data/funcionarios.JSON"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray doctors = (JSONArray) jsonObject.get("Doctor");
            Iterator iteratorDoc = doctors.iterator();
            JSONArray enfermeros = (JSONArray) jsonObject.get("Enfermero");
            Iterator iteratorEnf = doctors.iterator();
            JSONArray paramedicos = (JSONArray) jsonObject.get("Paramedico");
            Iterator iteratorPar = doctors.iterator();
			while (iteratorDoc.hasNext()) {
                iter = (JSONObject)iteratorDoc.next();
                medico = new doctor((int) (long) iter.get("id"),
                                    (String) iter.get("nombre"),
                                    (String)iter.get("apellido"),
                                    (int) (long)iter.get("experiencia"),
                                    (int) (long) iter.get("estudios"));
                listaDoctores.add(medico);
            }
            while (iteratorEnf.hasNext()) {
                iter = (JSONObject)iteratorEnf.next();
                enf = new enfermero((int) (long) iter.get("id"),
                                    (String) iter.get("nombre"),
                                    (String)iter.get("apellido"),
                                    (int) (long)iter.get("experiencia"),
                                    (int) (long) iter.get("estudios"));

                listaEnfermeros.add(enf);
            }
            while (iteratorPar.hasNext()) {
                iter = (JSONObject)iteratorPar.next();
                param = new paramedico((int) (long) iter.get("id"),
                                       (String) iter.get("nombre"),
                                       (String)iter.get("apellido"),
                                       (int) (long)iter.get("experiencia"),
                                       (int) (long) iter.get("estudios"));

                listaParamedicos.add(param);
            }
            obj = parser.parse(new FileReader("data/requerimientos.JSON"));
            jsonObject = (JSONObject) obj;
            JSONArray requerimientos = (JSONArray) jsonObject.get("requerimientos");
            Iterator iteratorReq = requerimientos.iterator();
            while (iteratorReq.hasNext()) {
                iter = (JSONObject)iteratorReq.next();
                Iterator pacientes =( (JSONArray) iter.get("pacientes")).iterator();
                HashMap<String, String> procedimientos = new HashMap<>(); 
                while (pacientes.hasNext()) {
                    iter1 = (JSONObject)pacientes.next();
                    Set keys = iter1.keySet();
                    Object[] llave = keys.toArray();
                    String llaveid = String.valueOf((String)llave[0]);
                    procedimientos.put(llaveid,(String)iter1.get(llaveid));
                }
                req = new requerimiento((int) (long) iter.get("id"), (String)iter.get("cargo"), procedimientos);
                listaRequerimientos.add(req);
            }
            obj = parser.parse(new FileReader("data/pacientes.JSON"));
            jsonObject = (JSONObject) obj;
            JSONArray pacientes = (JSONArray) jsonObject.get("Paciente");
            Iterator iteratorPac = pacientes.iterator();
            while (iteratorPac.hasNext()) { 
                iter = (JSONObject)iteratorPac.next();
                Iterator iteratorDP =( (JSONArray) iter.get("datos personales")).iterator();
                iter1 = (JSONObject)iteratorDP.next();
                JSONArray arreglo = (JSONArray) iter.get("enfermedades");
                for(int i = 0; i < arreglo.size(); i++){
                    enfermedades.add((String)arreglo.get(i));
                }
                Iterator iteratorTRA =( (JSONArray) iter.get("tratamientos/procedimientos")).iterator();
                iter3 =(JSONObject)iteratorTRA.next();
                arreglo = (JSONArray) iter3.get("asignados");
                for(int i = 0; i < arreglo.size(); i++){
                    procAsignados.add((String)arreglo.get(i));
                }
                iter3 = (JSONObject)iteratorTRA.next();
                arreglo = (JSONArray) iter3.get("completados");
                for(int i = 0; i < arreglo.size(); i++){
                    procCompletados.add((String)arreglo.get(i));
                }
                Iterator iteratorEXA =( (JSONArray) iter.get("examenes")).iterator();
                iter4 = (JSONObject)iteratorEXA.next();
                arreglo = (JSONArray) iter4.get("realizados");
                for(int i = 0; i < arreglo.size(); i++){
                    examRealizados.add((String)arreglo.get(i));
                }
                iter4 = (JSONObject)iteratorEXA.next();
                arreglo = (JSONArray) iter4.get("no realizados");
                for(int i = 0; i < arreglo.size(); i++){
                    examNorealizados.add((String)arreglo.get(i));
                }
                Iterator iteratorMED =( (JSONArray) iter.get("medicamentos")).iterator();
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("recetados");
                for(int i = 0; i < arreglo.size(); i++){
                    medRecetados.add((String)arreglo.get(i));
                }
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("suministrados");
                for(int i = 0; i < arreglo.size(); i++){
                    medSuministrados.add((String)arreglo.get(i));
                }
                pac = new paciente((String)iter1.get("nombre"),(String) iter1.get("rut"),Integer.parseInt((String)iter1.get("edad")),enfermedades,procAsignados,procCompletados,examRealizados,examNorealizados,medRecetados,medSuministrados);
                listaPacientes.add(pac);
            }            
        } catch (Exception e) {
			e.printStackTrace();
		}

        try {
            server.runServer();
        } catch (Exception e) {}

        scanner.nextLine();

        coord = false;

        while (!listaRequerimientos.isEmpty()) {
            // Sincronizar doctores
            currReq = listaRequerimientos.remove(0);
            doctor doc = listaDoctores.get(currReq.id-1);
            server.setDoc(doc);
            coord = cliente.heartbeat(doc.experiencia + doc.estudios);
            Iterator it = currReq.procedimientos.entrySet().iterator();
            
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }

            String dummy = scanner.nextLine();
        }

        server.close();
    }
    
}