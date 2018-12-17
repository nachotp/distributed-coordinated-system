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
        Server server = new Server(); // nuevo servidor
        Client cliente = new Client(); // nuevo cliente
        Scanner scanner = new Scanner(System.in); 
        JSONParser parser = new JSONParser();
        // definicion de variables varias
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
            Object obj = parser.parse(new FileReader("data/funcionarios.JSON")); // Leer JSON de funcionarios
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray doctors = (JSONArray) jsonObject.get("Doctor"); // Arreglo de doctores
            Iterator iteratorDoc = doctors.iterator();
            JSONArray enfermeros = (JSONArray) jsonObject.get("Enfermero"); // Arreglo de enfermeros
            Iterator iteratorEnf = doctors.iterator();
            JSONArray paramedicos = (JSONArray) jsonObject.get("Paramedico"); // Arreglo de paramedicos
            Iterator iteratorPar = doctors.iterator();
			while (iteratorDoc.hasNext()) { // Leer doctores
                iter = (JSONObject)iteratorDoc.next();
                medico = new doctor((int) (long) iter.get("id"),
                                    (String) iter.get("nombre"),
                                    (String)iter.get("apellido"),
                                    (int) (long)iter.get("experiencia"),
                                    (int) (long) iter.get("estudios"));
                listaDoctores.add(medico);
            }
            while (iteratorEnf.hasNext()) { // Leer enfermeros
                iter = (JSONObject)iteratorEnf.next();
                enf = new enfermero((int) (long) iter.get("id"),
                                    (String) iter.get("nombre"),
                                    (String)iter.get("apellido"),
                                    (int) (long)iter.get("experiencia"),
                                    (int) (long) iter.get("estudios"));

                listaEnfermeros.add(enf);
            }
            while (iteratorPar.hasNext()) { //Leer paramedicos
                iter = (JSONObject)iteratorPar.next();
                param = new paramedico((int) (long) iter.get("id"),
                                       (String) iter.get("nombre"),
                                       (String)iter.get("apellido"),
                                       (int) (long)iter.get("experiencia"),
                                       (int) (long) iter.get("estudios"));

                listaParamedicos.add(param);
            }
            obj = parser.parse(new FileReader("data/requerimientos.JSON")); //Leer JSON de requerimientos
            jsonObject = (JSONObject) obj;
            JSONArray requerimientos = (JSONArray) jsonObject.get("requerimientos"); //Arreglo de requerimientos
            Iterator iteratorReq = requerimientos.iterator();
            while (iteratorReq.hasNext()) {
                iter = (JSONObject)iteratorReq.next();
                Iterator pacientes =( (JSONArray) iter.get("pacientes")).iterator();
                HashMap<String, String> procedimientos = new HashMap<>(); // Hashmap con id de paciente y el procedimiento
                while (pacientes.hasNext()) { //ver pacientes
                    iter1 = (JSONObject)pacientes.next();
                    Set keys = iter1.keySet();
                    Object[] llave = keys.toArray();
                    String llaveid = String.valueOf((String)llave[0]);
                    procedimientos.put(llaveid,(String)iter1.get(llaveid));
                }
                req = new requerimiento((int) (long) iter.get("id"), (String)iter.get("cargo"), procedimientos);
                listaRequerimientos.add(req);
            }
            obj = parser.parse(new FileReader("data/pacientes.JSON")); // Leer JSON de pacientes
            jsonObject = (JSONObject) obj;
            JSONArray pacientes = (JSONArray) jsonObject.get("Paciente");
            Iterator iteratorPac = pacientes.iterator();
            while (iteratorPac.hasNext()) {  // Leer datos personales
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
                for(int i = 0; i < arreglo.size(); i++){ //Agregar procedimientos asignados
                    procAsignados.add((String)arreglo.get(i));
                }
                iter3 = (JSONObject)iteratorTRA.next();  //Agregar procedimientos completados
                arreglo = (JSONArray) iter3.get("completados");
                for(int i = 0; i < arreglo.size(); i++){
                    procCompletados.add((String)arreglo.get(i));
                }
                Iterator iteratorEXA =( (JSONArray) iter.get("examenes")).iterator();
                iter4 = (JSONObject)iteratorEXA.next(); 
                arreglo = (JSONArray) iter4.get("realizados");
                for(int i = 0; i < arreglo.size(); i++){//Agregar examenes realizados
                    examRealizados.add((String)arreglo.get(i));
                }
                iter4 = (JSONObject)iteratorEXA.next();
                arreglo = (JSONArray) iter4.get("no realizados");
                for(int i = 0; i < arreglo.size(); i++){//Agregar no examenes realizados
                    examNorealizados.add((String)arreglo.get(i));
                }
                Iterator iteratorMED =( (JSONArray) iter.get("medicamentos")).iterator();
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("recetados");//Agregar medicamentos recetados
                for(int i = 0; i < arreglo.size(); i++){
                    medRecetados.add((String)arreglo.get(i));
                }
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("suministrados"); //Agregar medicamentos suministrados
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
            server.runServer(); //correr servidor
        } catch (Exception e) {}

        scanner.nextLine();

        wait = false;
        String[] splitter;
        String id;
        String proc;
        HashMap<String, String> logInfo; 
        while (!listaRequerimientos.isEmpty()) { //LLevar a cabo requerimientos
            // Sincronizar doctores
            currReq = listaRequerimientos.remove(0);
            for (Map.Entry<String, String> entry : currReq.procedimientos.entrySet()) { 
                id= entry.getKey();
                proc = entry.getValue();
                splitter = proc.split(" ");
                //Registrar info de log en Hashmap
                logInfo.put("id",id);
                logInfo.put("opcion",splitter[1]);
                switch (splitter[0]) { //ver cual es el procedimiento
                    case "recetar":
                        System.out.println("recetandole al zapo qlo");
                        logInfo.put("accion","1");
                        break;
                    case "suministrar":
                        System.out.println("suministrandole al zapo qlo");
                        logInfo.put("accion","2");
                        break;
                    case "colocar":
                        logInfo.put("accion","3");
                        break;
                    case "solicitar":
                        logInfo.put("accion","4");
                        break;
                    case "pedir":
                        logInfo.put("accion","5");
                        break;
                    case "realizar":
                        logInfo.put("accion","6");
                        break;
                }
                //Verificar el cargo del funcionario y actuar
                if(currReq.cargo.equals("doctor")){
                    doctor doc = listaDoctores.get(currReq.id-1);
                    System.out.println("soy el diostor");
                    cliente.commitProcedure(logInfo); //commit de la accion
                    server.setDoc(doc);
                    cliente.heartbeat(doc.experiencia + doc.estudios);
                }
                else if(currReq.cargo.equals("enfermero")){
                    enfermero nurse = listaEnfermeros.get(currReq.id-1);
                    System.out.println("soy el nursito");
                }
                else{
                    paramedico paramedic = listaParamedicos.get(currReq.id-1);
                    System.out.println("soy el paramedico");
                }
                String dummy = scanner.nextLine();
            
                
            }
            
        }

        server.close();
    }
    
}