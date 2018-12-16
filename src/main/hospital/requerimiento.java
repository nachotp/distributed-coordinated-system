package main.hospital;
import java.util.*;

public class requerimiento {
    private String  cargo;
    private int id;
    private HashMap<String, String> procedimientos; 
    public requerimiento(int ide, String carg, HashMap<String, String> proce) {
        id = ide;
        cargo = carg;
        procedimientos = proce;
    }
}