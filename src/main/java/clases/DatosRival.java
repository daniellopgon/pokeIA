package clases;

import java.util.*;
import java.util.stream.Collectors;

public class DatosRival {

    public void obtenerEstrategia(List<Pokemon> equipoRival) {

        if (equipoRival == null || equipoRival.isEmpty()) {
            System.out.println("No hay Pokémon en el equipo rival.");
            return;
        }

        int contadorMax = 0;
        String estrategiaMasComun = "";

        for (Pokemon rival : equipoRival) {
            String estrategiaActual = "";
            if (rival.getEstrategia() != null) {
                estrategiaActual = rival.getEstrategia().toLowerCase();
            }

            if (!estrategiaActual.equals("")) {
                int contador = 0;
                for (Pokemon p : equipoRival) {
                    String est = "";
                    if (p.getEstrategia() != null) {
                        est = p.getEstrategia().toLowerCase();
                    }
                    if (est.equals(estrategiaActual)) {
                        contador++;
                    }
                }

                if (contador > contadorMax) {
                    contadorMax = contador;
                    estrategiaMasComun = estrategiaActual;
                }
            }
        }

        String ventaja = "";

        if (estrategiaMasComun.equals("soporte y control de campo")) {
            ventaja = "Usar Pokémon que limpien efectos de campo y bloqueen cambios.";
        } else if (estrategiaMasComun.equals("sweepers (atacantes)")) {
            ventaja = "Usar Pokémon rápidos y resistentes para aguantar golpes.";
        } else if (estrategiaMasComun.equals("defensores y tanques")) {
            ventaja = "Usar ataques que ignoren defensa o causen daño residual.";
        } else if (estrategiaMasComun.equals("weather & terrain setters")) {
            ventaja = "Cambiar clima o terreno para neutralizar al rival.";
        } else {
            ventaja = "No hay ventaja clara, adaptar según la situación.";
        }

        System.out.println("LA ESTRATEGIA MÁS PROBABLE DEL RIVAL ES: " + estrategiaMasComun);
        System.out.println("LA MEJOR FORMA DE COMBATIRLA ES: " + ventaja);
    }


    public List<Pokemon> obtenerDatosRival(Scanner sc, List<Pokemon> pokemonsTotales) {

        List<Pokemon> equipoRival = new ArrayList<>();

        System.out.println("Introduce el nombre de los 6 Pokémon del equipo:");

        int count = 0;
        while (count < 6) {
            System.out.print("Pokémon #" + (count + 1) + ": ");
            String nombreBuscado = sc.nextLine().trim();

            Pokemon rival = null;
            boolean encontrado = false;

            for (int i = 0; i < pokemonsTotales.size() && !encontrado; i++) {
                Pokemon p = pokemonsTotales.get(i);
                if (p.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    rival = p;
                    encontrado = true;
                }
            }

            if (rival != null) {
                equipoRival.add(rival);
                count++;
            } else {
                System.out.println("No se encontró ningún Pokémon con ese nombre. Intenta de nuevo.");
            }
        }

        return equipoRival;
    }


    public void obtenerMovimientosObjetosYhabilidadesDelEquipoRival(List<Pokemon> equipoRival) {

        System.out.printf("%-15s %-20s %-25s%n", "Pokémon", "Tipo de Efecto", "Nombre del Efecto");
        System.out.println("---------------------------------------------------------------");

        for (Pokemon pokemon : equipoRival) {
            HashMap<String, List<Efecto>> efectos = pokemon.getEfectos();

            if (efectos == null || efectos.isEmpty()) {
                System.out.printf("%-15s %-20s %-25s%n", pokemon.getNombre(), "-", "-");
            } else {
                boolean primeraFila = true;

                for (String tipoEfecto : efectos.keySet()) {
                    List<Efecto> listaEfectos = efectos.get(tipoEfecto);

                    if (listaEfectos == null || listaEfectos.isEmpty()) {

                        if (primeraFila) {
                            System.out.printf("%-15s %-20s %-25s%n", pokemon.getNombre(), tipoEfecto, "-");
                            primeraFila = false;
                        } else {
                            System.out.printf("%-15s %-20s %-25s%n", "", tipoEfecto, "-");
                        }
                    } else {
                        for (int i = 0; i < listaEfectos.size(); i++) {
                            String nombreEfecto = listaEfectos.get(i).getNombre();
                            if (primeraFila) {
                                System.out.printf("%-15s %-20s %-25s%n", pokemon.getNombre(), tipoEfecto, nombreEfecto);
                                primeraFila = false;
                            } else {
                                System.out.printf("%-15s %-20s %-25s%n", "", tipoEfecto, nombreEfecto);
                            }
                        }
                    }
                }
            }
            System.out.println("---------------------------------------------------------------");
        }
    }


    public Pokemon obtenerPokemon(List<Pokemon> equipo, String nombre) {
        return equipo.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }


    public Efecto obtenerMovimiento(Pokemon pokemon, String nombreMovimiento) {

        HashMap<String, List<Efecto>> listaMovimientos = pokemon.getEfectos();

        return listaMovimientos.values().stream()
                .flatMap(List::stream)
                .filter(m -> m.getNombre().equalsIgnoreCase(nombreMovimiento))
                .findFirst()
                .orElse(null);
    }


    public void addListaAccionEfectoAlEquipo(List<Pokemon> equipo, List<Efecto> efectosCompletos) {

        Map<String, Efecto> mapaEfectos = efectosCompletos.stream()
                .collect(Collectors.toMap(e -> e.getNombre().trim(), e -> e));

        equipo.stream()
                .map(Pokemon::getEfectos)
                .filter(Objects::nonNull)
                .forEach(mapa -> mapa.values().forEach(lista ->
                        lista.replaceAll(e -> {
                            String nombre = e.getNombre().trim();
                            Efecto completo = mapaEfectos.get(nombre);
                            if (completo == null)
                                System.err.println("No se encontró efecto completo para: " + nombre);
                            return completo != null ? completo : e;
                        })
                ));
    }
}
