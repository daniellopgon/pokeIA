package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pokemon {
    private String nombre;
    private HashMap<String, List<Efecto>> efectos;
    private Estadistica estadistica;
    private List<String> tipos;
    private String estrategia;
    private String estado;

    public Pokemon(String nombre, Estadistica estadistica, String estrategia, String estado) {

        this.nombre = nombre;
        this.estadistica = estadistica;
        this.efectos = new HashMap<>();
        this.tipos = new ArrayList<>();
        this.estrategia = estrategia;
        this.estado = estado;
    }


    public Pokemon() {
        this.efectos = new HashMap<>();
        this.tipos = new ArrayList<>();
    }


    public void addEfecto(String tipo, Efecto efecto) {

        tipo = tipo.toLowerCase();

        if (tipo.equals("objeto")) {
            if (efectos.containsKey(tipo)) {
                System.out.println("Ya tiene un objeto asignado.");
                return;
            }
            efectos.put(tipo, new ArrayList<>());
            efectos.get(tipo).add(efecto);

        } else if (tipo.equals("habilidad")) {
            if (efectos.containsKey(tipo)) {
                System.out.println("Ya tiene una habilidad asignada.");
                return;
            }
            efectos.put(tipo, new ArrayList<>());
            efectos.get(tipo).add(efecto);

        } else if (tipo.equals("movimiento")) {
            efectos.putIfAbsent(tipo, new ArrayList<>());
            if (efectos.get(tipo).size() >= 4) {
                System.out.println("No puede tener más de 4 movimientos.");
                return;
            }
            efectos.get(tipo).add(efecto);

        } else {
            System.out.println("Tipo no válido.");
        }
    }


    public HashMap<String, List<Efecto>> getEfectos() {
        return efectos;
    }


    public List<String> getTipos() {
        return tipos;
    }


    public String getEstrategia() {
        return estrategia;
    }


    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }


    public void addTipo(String tipo) {
        if (tipos.size() <= 2) {
            tipos.add(tipo);
        }
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Estadistica getEstadistica() {
        return estadistica;
    }


    public void setEstadistica(Estadistica estadistica) {
        this.estadistica = estadistica;
    }

}
