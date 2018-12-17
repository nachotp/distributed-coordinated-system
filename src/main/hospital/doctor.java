package main.hospital;

import java.util.*;

public class doctor {
    public String  nombre;
    public String  apellido;
    public int id;
    public int  experiencia;
    public int estudios;

    public doctor(int ide, String name, String lastname, int experience, int studies) {
        nombre = name;
        apellido = lastname;
        id = ide;
        experiencia = experience;
        estudios = studies;
    }
}