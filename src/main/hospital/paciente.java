package main.hospital;
import java.util.*;
public class paciente{
    private String  nombre;
    private String  rut;
    public static boolean locked;
    private int  edad;
    public List<String> enfermedades;
    public List<String> procAsignados;
    public List<String> procCompletados;
    public List<String> examRealizados;
    public List<String> examNorealizados;
    public List<String> medRecetados;
    public List<String> medSuministrados;
    public paciente(String name, String iden, int age, List<String> enf, List<String> procA,List<String> procC, List<String> examR, List<String> examN, List<String> medR, List<String> medS) {
        nombre = name;
        rut = iden;
        edad = age;
        enfermedades = enf;
        procAsignados = procA;
        procCompletados = procC;
        examRealizados = examR;
        examNorealizados = examN;
        medRecetados = medR;
        medSuministrados = medS;
        locked = false;
    }
}