package main.hospital;
import java.util.*;

public class requerimiento {
    public String  cargo;
    public int id;
    public HashMap<String, String> procedimientos; 
    public requerimiento(int ide, String carg, HashMap<String, String> proce) {
        id = ide;
        cargo = carg;
        procedimientos = proce;
    }
}