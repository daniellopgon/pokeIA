package clases;

import java.util.*;
import java.util.function.BiConsumer;

public class Combate {
    private List<Pokemon> equipoRival;
    private List<Pokemon> miEquipo;
    private Map<String, BiConsumer<Pokemon, Double>> aumentadores;
    private Map<String, BiConsumer<Pokemon, Double>> reductores;
    private Map<String, BiConsumer<Pokemon, Double>> incrementadores;
    private Calculo calculo;
    private DatosRival datos;


    public Combate() {

        this.miEquipo = new ArrayList<>();
        this.equipoRival = new ArrayList<>();

        this.aumentadores = new HashMap<>();
        aumentadores.put("vida", (pokemon, valor) -> pokemon.getEstadistica().setVida(pokemon.getEstadistica().getVida() + valor));
        aumentadores.put("ataque", (pokemon, valor) -> pokemon.getEstadistica().setAtaque(pokemon.getEstadistica().getAtaque() + valor));
        aumentadores.put("ataqueEspecial", (pokemon, valor) -> pokemon.getEstadistica().setAtaqueEspecial(pokemon.getEstadistica().getAtaqueEspecial() + valor));
        aumentadores.put("defensaEspecial", (pokemon, valor) -> pokemon.getEstadistica().setDefensaEspecial(pokemon.getEstadistica().getDefensaEspecial() + valor));
        aumentadores.put("defensa", (pokemon, valor) -> pokemon.getEstadistica().setDefensa(pokemon.getEstadistica().getDefensa() + valor));
        aumentadores.put("velocidad", (pokemon, valor) -> pokemon.getEstadistica().setVelocidad(pokemon.getEstadistica().getVelocidad() + valor));

        this.incrementadores = new HashMap<>();
        incrementadores.put("vida", (pokemon, valor) -> pokemon.getEstadistica().setVida(pokemon.getEstadistica().getVida() * valor));
        incrementadores.put("ataque", (pokemon, valor) -> pokemon.getEstadistica().setAtaque(pokemon.getEstadistica().getAtaque() * valor));
        incrementadores.put("ataqueEspecial", (pokemon, valor) -> pokemon.getEstadistica().setAtaqueEspecial(pokemon.getEstadistica().getAtaqueEspecial() * valor));
        incrementadores.put("defensaEspecial", (pokemon, valor) -> pokemon.getEstadistica().setDefensaEspecial(pokemon.getEstadistica().getDefensaEspecial() * valor));
        incrementadores.put("defensa", (pokemon, valor) -> pokemon.getEstadistica().setDefensa(pokemon.getEstadistica().getDefensa() * valor));
        incrementadores.put("velocidad", (pokemon, valor) -> pokemon.getEstadistica().setVelocidad(pokemon.getEstadistica().getVelocidad() * valor));

        this.reductores = new HashMap<>();
        reductores.put("vida", (pokemon, valor) -> pokemon.getEstadistica().setVida(pokemon.getEstadistica().getVida() - valor));
        reductores.put("ataque", (pokemon, valor) -> pokemon.getEstadistica().setAtaque(pokemon.getEstadistica().getAtaque() - valor));
        reductores.put("ataqueEspecial", (pokemon, valor) -> pokemon.getEstadistica().setAtaqueEspecial(pokemon.getEstadistica().getAtaqueEspecial() - valor));
        reductores.put("defensaEspecial", (pokemon, valor) -> pokemon.getEstadistica().setDefensaEspecial(pokemon.getEstadistica().getDefensaEspecial() - valor));
        reductores.put("defensa", (pokemon, valor) -> pokemon.getEstadistica().setDefensa(pokemon.getEstadistica().getDefensa() - valor));
        reductores.put("velocidad", (pokemon, valor) -> pokemon.getEstadistica().setVelocidad(pokemon.getEstadistica().getVelocidad() - valor));

    }


    public void aplicarEfectos(Pokemon pokemon, List<Pokemon> equipo, AccionEfecto accion) {

        List<Pokemon> objetivos;

        if ("equipo".equals(accion.getObjetivo())) {
            objetivos = equipo;
        } else {
            objetivos = List.of(pokemon);
        }

        Map<String, BiConsumer<Pokemon, Double>> mapaModificadores;

        if ("aumenta".equals(accion.getModificador())) {
            mapaModificadores = aumentadores;
        } else if ("incrementa".equals(accion.getModificador())) {
            mapaModificadores = incrementadores;
        } else {
            mapaModificadores = reductores;
        }

        BiConsumer<Pokemon, Double> modificador = mapaModificadores.get(accion.getEstadistica());

        if (modificador != null) {
            for (Pokemon objetivo : objetivos) {
                modificador.accept(objetivo, accion.getValor());
            }
        }
    }


    public void equipar(List<Pokemon> equipo) {

        System.out.println(">> Ejecutando método equipar()");

        for (Pokemon pokemon : equipo) {
            System.out.println("  Procesando Pokémon: " + pokemon.getNombre());

            for (List<Efecto> listaEfectos : pokemon.getEfectos().values()) {

                for (Efecto efecto : listaEfectos) {
                    System.out.println("    Efecto detectado: " + efecto.getNombre() + ", tipo: " + efecto.getTipo());

                    String tipo = efecto.getTipo();

                    if (efecto.getTipo() == null) {
                        System.out.println("    Tipo nulo encontrado, asignando 'movimiento'");
                        efecto.setTipo("movimiento");
                    }

                    if (tipo != null && efecto.getTipo().equalsIgnoreCase("objeto") ||
                            efecto.getTipo().equalsIgnoreCase("habilidad")) {

                        System.out.println("    Aplicando efecto de tipo: " + efecto.getTipo());

                        List<AccionEfecto> acciones = efecto.getEfectoList();
                        if (acciones != null) {

                            for (AccionEfecto accion : acciones) {
                                System.out.println("      Acción: " + accion.getEstadistica() + ", modifica en: " + accion.getValor());
                                aplicarEfectos(pokemon, equipo, accion);
                            }
                        } else {
                            System.out.println("      No hay acciones para este efecto.");
                        }
                    }
                }
            }
        }
    }


    public void atacar(String nombreAtacante, String nombreReceptor, String nombreMovimiento) {

        Pokemon atacante = datos.obtenerPokemon(miEquipo, nombreAtacante);
        Pokemon receptor = datos.obtenerPokemon(equipoRival, nombreReceptor);
        Efecto movimiento = datos.obtenerMovimiento(atacante, nombreMovimiento);

        if (movimiento == null || atacante == null) {
            System.out.println("Movimiento o atacante no encontrado.");
            return;
        }

        String objetivo = movimiento.getEfectoList().stream()
                .map(m -> m.getObjetivo().trim().toLowerCase())
                .findFirst()
                .orElse(null);

        if (!movimiento.getTipoMovimiento().equalsIgnoreCase("estado")) {

            switch (objetivo.trim().toLowerCase()) {
                case "usuario" -> atacante.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento));
                case "enemigo" -> receptor.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento));
                case "mi equipo" ->
                        miEquipo.forEach(p -> p.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento)));
                case "equipo rival" ->
                        equipoRival.forEach(p -> p.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento)));
                case "todos" -> {
                    miEquipo.forEach(p -> p.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento)));
                    equipoRival.forEach(p -> p.getEstadistica().setVida(calculo.calcularDanio(atacante, nombreMovimiento)));
                }
                default -> System.out.println("Objetivo desconocido: " + objetivo);
            }

        } else if (movimiento.getTipoMovimiento().equalsIgnoreCase("estado")) {

            switch (objetivo.trim().toLowerCase()) {
                case "usuario" -> atacante.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento));
                case "enemigo" -> receptor.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento));
                case "mi equipo" ->
                        miEquipo.forEach(p -> p.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento)));
                case "equipo rival" ->
                        equipoRival.forEach(p -> p.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento)));
                case "todos" -> {
                    miEquipo.forEach(p -> p.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento)));
                    equipoRival.forEach(p -> p.setEstado(calculo.calcularEfectos(atacante, nombreMovimiento)));
                }
                default -> System.out.println("Objetivo desconocido: " + objetivo);
            }

        }

    }


    public void elegirElMejorEquipo(ArrayList<Pokemon> miEquipo, String estrategia) {

        List<Pokemon> seleccion = switch (estrategia.toLowerCase()) {

            case "soporte y control de campo" -> {
                double puntos = miEquipo.stream()
                        .flatMap(p -> p.getEfectos().values().stream())
                        .flatMap(List::stream)
                        .flatMap(e -> e.getEfectoList().stream())
                        .map(AccionEfecto::getEstado)
                        .filter(estado -> estado.equals("Cambiar estado campo") ||
                                estado.equals("Estado alterado") ||
                                estado.equals("Reducir estadisticas"))
                        .mapToDouble(calculo::calcularPuntosMovimientoEstado)
                        .sum();

                yield miEquipo.stream().limit(4).toList();

            }

            case "sweepers (atacantes)" -> {
                miEquipo.stream()
                        .sorted(Comparator.comparingDouble(p -> -(p.getEstadistica().getDefensa() + p.getEstadistica().getDefensaEspecial() +
                                p.getEstadistica().getVelocidad() + p.getEstadistica().getVida())))
                        .limit(4)
                        .toList();

                yield miEquipo.stream().limit(4).toList();

            }

            case "defensores y tanques" -> {
                miEquipo.stream()
                        .flatMap(p -> p.getEfectos().values().stream())
                        .flatMap(List::stream)
                        .flatMap(e -> e.getEfectoList().stream())
                        .map(AccionEfecto::getEstado)
                        .filter(es -> es.equals("Estado alterado") ||
                                es.equals("Reducir estadisticas") ||
                                es.equals("Anulación de objeto"))
                        .mapToDouble(calculo::calcularPuntosMovimientoEstado)
                        .sum();

                yield miEquipo.stream().limit(4).toList();
            }


            case "weather & terrain setters" -> {
                miEquipo.stream()
                        .flatMap(p -> p.getEfectos().values().stream())
                        .flatMap(List::stream)
                        .flatMap(e -> e.getEfectoList().stream())
                        .map(AccionEfecto::getEstado)
                        .filter(es -> es.equals("Cambiar estado campo") ||
                                es.equals("Forzar cambios") ||
                                es.equals("Redirigir ataques"))
                        .mapToDouble(calculo::calcularPuntosMovimientoEstado)
                        .sum();

                yield miEquipo.stream().limit(4).toList();

            }

            default -> miEquipo.subList(0, Math.min(4, miEquipo.size()));
        };

        System.out.println("EL EQUIPO QUE TIENES QUE ELEGIR ES:");
        seleccion.forEach(p -> System.out.println("- " + p.getNombre()));
    }

}
