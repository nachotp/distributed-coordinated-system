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
        Constants cte = new Constants();
        Client cliente = new Client(cte); // nuevo cliente
        Server server = new Server(cliente, cte); // nuevo servidor
        
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
                cte.listaDoctores.add(medico);
            }
            while (iteratorEnf.hasNext()) { // Leer enfermeros
                iter = (JSONObject)iteratorEnf.next();
                enf = new enfermero((int) (long) iter.get("id"),
                                    (String) iter.get("nombre"),
                                    (String)iter.get("apellido"),
                                    (int) (long)iter.get("experiencia"),
                                    (int) (long) iter.get("estudios"));

                cte.listaEnfermeros.add(enf);
            }
            while (iteratorPar.hasNext()) { //Leer paramedicos
                iter = (JSONObject)iteratorPar.next();
                param = new paramedico((int) (long) iter.get("id"),
                                       (String) iter.get("nombre"),
                                       (String)iter.get("apellido"),
                                       (int) (long)iter.get("experiencia"),
                                       (int) (long) iter.get("estudios"));

                cte.listaParamedicos.add(param);
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
                cte.listaRequerimientos.add(req);
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
                    cte.enfermedades.add((String)arreglo.get(i));
                }
                Iterator iteratorTRA =( (JSONArray) iter.get("tratamientos/procedimientos")).iterator();
                iter3 =(JSONObject)iteratorTRA.next();
                arreglo = (JSONArray) iter3.get("asignados");
                for(int i = 0; i < arreglo.size(); i++){ //Agregar procedimientos asignados
                    cte.procAsignados.add((String)arreglo.get(i));
                }
                iter3 = (JSONObject)iteratorTRA.next();  //Agregar procedimientos completados
                arreglo = (JSONArray) iter3.get("completados");
                for(int i = 0; i < arreglo.size(); i++){
                    cte.procCompletados.add((String)arreglo.get(i));
                }
                Iterator iteratorEXA =( (JSONArray) iter.get("examenes")).iterator();
                iter4 = (JSONObject)iteratorEXA.next(); 
                arreglo = (JSONArray) iter4.get("realizados");
                for(int i = 0; i < arreglo.size(); i++){//Agregar examenes realizados
                    cte.examRealizados.add((String)arreglo.get(i));
                }
                iter4 = (JSONObject)iteratorEXA.next();
                arreglo = (JSONArray) iter4.get("no realizados");
                for(int i = 0; i < arreglo.size(); i++){//Agregar no examenes realizados
                    cte.examNorealizados.add((String)arreglo.get(i));
                }
                Iterator iteratorMED =( (JSONArray) iter.get("medicamentos")).iterator();
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("recetados");//Agregar medicamentos recetados
                for(int i = 0; i < arreglo.size(); i++){
                    cte.medRecetados.add((String)arreglo.get(i));
                }
                iter5 = (JSONObject)iteratorMED.next();
                arreglo = (JSONArray) iter5.get("suministrados"); //Agregar medicamentos suministrados
                for(int i = 0; i < arreglo.size(); i++){
                    cte.medSuministrados.add((String)arreglo.get(i));
                }
                pac = new paciente((String)iter1.get("nombre"),(String) iter1.get("rut"),Integer.parseInt((String)iter1.get("edad")), cte.enfermedades, cte.procAsignados,
                        cte.procCompletados, cte.examRealizados, cte.examNorealizados, cte.medRecetados,
                        cte.medSuministrados);
                cte.listaPacientes.add(pac);
            }            
        } catch (Exception e) {
			e.printStackTrace();
		}

        try {
            server.runServer(); //correr servidor
        } catch (Exception e) {}
        System.out.println("Presisone Enter para comenzar...");
        scanner.nextLine();

        boolean wait = false;
        String[] splitter;
        String id;
        String proc;
        HashMap<String, String> logInfo; 
        
        while (!cte.listaRequerimientos.isEmpty()) { //LLevar a cabo requerimientos
            // Sincronizar doctores
            currReq = cte.listaRequerimientos.remove(0);
            Iterator iteri = (currReq.procedimientos.entrySet()).iterator();
           while (iteri.hasNext()) {
                logInfo = new HashMap<>();
                Map.Entry pair = (Map.Entry) iteri.next();
                id= (String)pair.getKey();
                proc = (String)pair.getValue();
                splitter = proc.split(" ");
                //Registrar info de log en Hashmap
                logInfo.put("id",id);
                logInfo.put("opcion",splitter[1]);
                switch (splitter[0]) { //ver cual es el procedimiento
                    case "recetar":
                        logInfo.put("accion","1");
                        break;
                    case "suministrar":
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
                    doctor doc = cte.listaDoctores.get(currReq.id-1);
                    server.setDoc(doc);
                    coord = cliente.heartbeat(doc.experiencia + doc.estudios);
                    while (cte.heartbeat > 0);
                    if (coord) {
                        System.out.println("Asignado como coordinador.");
                    } else {
                        System.out.println("Asignado como seguidor.");
                    }
                }
                else if(currReq.cargo.equals("enfermero")){
                    enfermero nurse = cte.listaEnfermeros.get(currReq.id-1);
                }
                else{
                    paramedico paramedic = cte.listaParamedicos.get(currReq.id-1);
                }
                
                boolean commitSuccess = cliente.commitProcedure(logInfo);
                cte.wait = true;
                if (coord && commitSuccess){
                    cliente.pushProcedure(logInfo);
                    cte.wait = false;
                }

                while (cte.wait);

                String dummy = scanner.nextLine();
            
                
            }
            
        }

        server.close();
    }
    
}