package main;

import java.util.*;
import main.hospital.*;

public class Constants {
    public List<paciente> listaPacientes;
    public List<doctor> listaDoctores;
    public List<enfermero> listaEnfermeros;
    public List<paramedico> listaParamedicos;
    public List<requerimiento> listaRequerimientos;
    public List<String> enfermedades;
    public List<String> procAsignados;
    public List<String> procCompletados;
    public List<String> examRealizados;
    public List<String> examNorealizados;
    public List<String> medRecetados;
    public List<String> medSuministrados;

    Constants(){
        listaDoctores = new ArrayList<doctor>();
        listaEnfermeros = new ArrayList<enfermero>();
        listaParamedicos = new ArrayList<paramedico>();
        listaRequerimientos= new ArrayList<requerimiento>();
        listaPacientes=new ArrayList<paciente>();
        enfermedades = new ArrayList<String>();
        procAsignados = new ArrayList<String>();
        procCompletados = new ArrayList<String>();
        examRealizados = new ArrayList<String>();
        examNorealizados = new ArrayList<String>();
        medRecetados = new ArrayList<String>();
        medSuministrados = new ArrayList<String>();
    }

}