package clases;

import java.util.*;
import java.util.List;


public class EstadisticaCombate {
    private Map<String, Integer> objetosCount;
    private Map<String, Integer> habilidadesCount;
    private Map<String, Integer> movimientosCount;
    private Map<String, Integer> estrategiaCount;

    public EstadisticaCombate() {

        this.objetosCount = new HashMap<>();
        this.habilidadesCount = new HashMap<>();
        this.movimientosCount = new HashMap<>();
        this.estrategiaCount = new HashMap<>();
    }


    public void estadisticasTotales(Map<String, Map<String, Integer>> conteos) {

        System.out.println("----- Objetos Más Repetidos -----");
        System.out.printf("%-25s %10s%n", "Objeto", "Veces");
        System.out.println("---------------------------------------------");
        conteos.getOrDefault("objetos", new HashMap<>()).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%-25s %10d%n", entry.getKey(), entry.getValue()));

        System.out.println("\n----- Habilidades Más Repetidas -----");
        System.out.printf("%-25s %10s%n", "Habilidad", "Veces");
        System.out.println("---------------------------------------------");
        conteos.getOrDefault("habilidades", new HashMap<>()).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%-25s %10d%n", entry.getKey(), entry.getValue()));

        System.out.println("\n----- Movimientos Más Repetidos -----");
        System.out.printf("%-25s %10s%n", "Movimiento", "Veces");
        System.out.println("---------------------------------------------");
        conteos.getOrDefault("movimientos", new HashMap<>()).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%-25s %10d%n", entry.getKey(), entry.getValue()));

        System.out.println("\n----- Estrategias Más Repetidas -----");
        System.out.printf("%-25s %10s%n", "Estrategia", "Veces");
        System.out.println("---------------------------------------------");
        conteos.getOrDefault("estrategias", new HashMap<>()).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%-25s %10d%n", entry.getKey(), entry.getValue()));
    }


    public void imprimirDatos(List<Pokemon> pokemons) {

        System.out.printf("%-15s %-5s %-7s %-8s %-9s %-15s %-17s %-10s %-10s%n",
                "Nombre", "Vida", "Ataque", "Defensa", "Velocidad", "AtaqueEsp", "DefensaEsp", "Tipo 1", "Tipo 2");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (Pokemon p : pokemons) {

            double vida = p.getEstadistica().getVida();
            double ataque = p.getEstadistica().getAtaque();
            double defensa = p.getEstadistica().getDefensa();
            double velocidad = p.getEstadistica().getVelocidad();
            double ataqueEspecial = p.getEstadistica().getAtaqueEspecial();
            double defensaEspecial = p.getEstadistica().getDefensaEspecial();

            String tipo1 = "";
            String tipo2 = "";

            if (!p.getTipos().isEmpty()) {
                tipo1 = p.getTipos().get(0);
                if (p.getTipos().size() > 1) {
                    tipo2 = p.getTipos().get(1);
                }
            }

            System.out.printf("%-15s %-7.2f %-9.2f %-10.2f %-11.2f %-17.2f %-19.2f %-10s %-10s%n",
                    p.getNombre(),
                    vida, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial,
                    tipo1, tipo2);
        }
    }


    public Map<String, Map<String, Integer>> estadisticasGenerales(List<Pokemon> topTiers) {

        Map<String, Integer> objetosCount = new HashMap<>();
        Map<String, Integer> habilidadesCount = new HashMap<>();
        Map<String, Integer> movimientosCount = new HashMap<>();
        Map<String, Integer> estrategiaCount = new HashMap<>();


        for (Pokemon pokemon : topTiers) {

            String estrategia = pokemon.getEstrategia();
            if (estrategia != null && !estrategia.isEmpty()) {
                estrategiaCount.put(estrategia, estrategiaCount.getOrDefault(estrategia, 0) + 1);
            }
            HashMap<String, List<Efecto>> efectos = pokemon.getEfectos();

            if (efectos != null) {

                for (Map.Entry<String, List<Efecto>> entry : efectos.entrySet()) {

                    String tipoEfecto = entry.getKey();
                    List<Efecto> listaEfectos = entry.getValue();

                    if (listaEfectos != null) {

                        for (Efecto e : listaEfectos) {

                            String nombre = e.getNombre();

                            if (nombre != null && !nombre.isEmpty()) {
                                String tipoMinuscula = tipoEfecto.toLowerCase();

                                if (tipoMinuscula.equals("objeto") || tipoMinuscula.equals("objetos")) {
                                    objetosCount.put(nombre, objetosCount.getOrDefault(nombre, 0) + 1);

                                } else if (tipoMinuscula.equals("habilidad") || tipoMinuscula.equals("habilidades")) {
                                    habilidadesCount.put(nombre, habilidadesCount.getOrDefault(nombre, 0) + 1);

                                } else if (tipoMinuscula.equals("movimiento") || tipoMinuscula.equals("movimientos")) {
                                    movimientosCount.put(nombre, movimientosCount.getOrDefault(nombre, 0) + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        Map<String, Map<String, Integer>> resultado = new HashMap<>();

        resultado.put("objetos", objetosCount);
        resultado.put("habilidades", habilidadesCount);
        resultado.put("movimientos", movimientosCount);
        resultado.put("estrategias", estrategiaCount);

        return resultado;
    }

}





