package main.hospital;
import java.util.*;
public class paciente{
    private String  nombre;
    private String  rut;
    public Boolean locked;
    private int  edad;
    private List<String> enfermedades;
    private List<String> procAsignados;
    private List<String> procCompletados;
    private List<String> examRealizados;
    private List<String> examNorealizados;
    private List<String> medRecetados;
    private List<String> medSuministrados;
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