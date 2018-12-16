package main.hospital;


public class enfermero {
    private String  nombre;
    private String  apellido;
    private int id;
    private int  experiencia;
    private int estudios;

    public enfermero(int ide, String name, String lastname, int experience, int studies) {
        nombre = name;
        apellido = lastname;
        id = ide;
        experiencia = experience;
        estudios = studies;
    }
}