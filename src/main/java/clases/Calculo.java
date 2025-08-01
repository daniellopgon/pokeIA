package clases;

import indices.TipoEnum;

import java.util.*;
import java.util.stream.Collectors;

public class Calculo {
    private DatosRival datos;

    public double calcularDanio(Pokemon atacante, String movimiento) {

        double ataque = 0;
        double valor;

        Efecto movimientoElegido = datos.obtenerMovimiento(atacante, movimiento);

        if (movimientoElegido.getTipoMovimiento().equals("especial".trim().toLowerCase())) {

            valor = movimientoElegido.getEfectoList().stream()
                    .mapToDouble(v -> v.getValor())
                    .sum();

            ataque = valor + atacante.getEstadistica().getAtaqueEspecial();

        } else if (movimientoElegido.getTipoMovimiento().equals("fisico".trim().toLowerCase())) {

            valor = movimientoElegido.getEfectoList().stream()
                    .mapToDouble(v -> v.getValor())
                    .sum();

            ataque = valor + atacante.getEstadistica().getAtaque();

        }
        return ataque;
    }


    public String calcularEfectos(Pokemon atacante, String movimiento) {

        String estado = "";

        Efecto movimientoElegido = datos.obtenerMovimiento(atacante, movimiento);

        if (movimientoElegido.getTipoMovimiento().equals("estado".trim().toLowerCase())) {

            estado = movimientoElegido.getEfectoList().stream()
                    .map(e -> e.getEstado())
                    .findFirst()
                    .orElse(null);
        }

        return estado;

    }


    public double calcularPuntosMovimientoEstado(String efecto) {

        double puntuacion = 0;

                switch (efecto) {

                    case "Estado alterado":
                        puntuacion += 2;
                        break;
                    case "Reducir estadisticas":
                        puntuacion += 2;
                        break;
                    case "Aumentar estadisticas":
                        puntuacion += 2;
                        break;
                    case "Cambiar estado campo":
                        puntuacion += 3;
                        break;
                    case "Redirigir ataques":
                        puntuacion += 3;
                        break;
                    case "Cambiar habilidades":
                        puntuacion += 3;
                        break;
                    case "Forzar cambios":
                        puntuacion += 2;
                        break;
                    case "Anulación de objeto":
                        puntuacion += 2;
                        break;
                    default:
                        System.out.println("No existe");
                        break;
                }

            return puntuacion;
    }


    public String calcularCambios(List<Pokemon> miEquipo, List<Pokemon> equipoRival) {

        StringBuilder resultado = new StringBuilder();

        for (Pokemon atacante : miEquipo) {
            for (Pokemon defensor : equipoRival) {

                List<String> ventajas = atacante.getTipos().stream()
                        .flatMap(tipoAt -> defensor.getTipos().stream()
                                .map(tipoDef -> tipoAt + " vs " + tipoDef + " = " + calcularVentaja(tipoAt, tipoDef)))
                        .collect(Collectors.toList());

                String nombreMovimiento = atacante.getEfectos().keySet().stream()
                        .findFirst()
                        .orElse("movimiento_por_defecto");

                List<String> danios = List.of("Daño esperado = " + calcularDanio(atacante, nombreMovimiento));

                List<String> estados = List.of("Efecto esperado = " + calcularEfectos(atacante, nombreMovimiento));

                resultado.append("Atacante: ").append(atacante.getNombre())
                        .append(" -> Defensor: ").append(defensor.getNombre()).append("\n");

                resultado.append("Ventajas: ").append(ventajas).append("\n");
                resultado.append("Daño: ").append(danios).append("\n");
                resultado.append("Estado: ").append(estados).append("\n\n");
            }
        }

        return resultado.toString();
    }


    public List<String> calcularVentaja(String tipo1, String tipo2) {

        Map<TipoEnum, List<String>> debilidades = Map.ofEntries(

                Map.entry(TipoEnum.Acero, List.of("Fuego", "Lucha", "Tierra")),
                Map.entry(TipoEnum.Agua, List.of("Planta", "Electrico")),
                Map.entry(TipoEnum.Bicho, List.of("Fuego", "Volador", "Roca")),
                Map.entry(TipoEnum.Dragon, List.of("Hielo", "Dragon", "Hada")),
                Map.entry(TipoEnum.Electrico, List.of("Tierra")),
                Map.entry(TipoEnum.Fantasma, List.of("Fantasma", "Siniestro")),
                Map.entry(TipoEnum.Fuego, List.of("Agua", "Roca", "Tierra")),
                Map.entry(TipoEnum.Hada, List.of("Veneno", "Acero")),
                Map.entry(TipoEnum.Hielo, List.of("Fuego", "Lucha", "Roca", "Acero")),
                Map.entry(TipoEnum.Lucha, List.of("Volador", "Psiquico", "Hada")),
                Map.entry(TipoEnum.Normal, List.of("Lucha")),
                Map.entry(TipoEnum.Planta, List.of("Fuego", "Hielo", "Veneno", "Volador", "Bicho")),
                Map.entry(TipoEnum.Psiquico, List.of("Bicho", "Fantasma", "Siniestro")),
                Map.entry(TipoEnum.Roca, List.of("Agua", "Planta", "Lucha", "Tierra", "Acero")),
                Map.entry(TipoEnum.Siniestro, List.of("Lucha", "Bicho", "Hada")),
                Map.entry(TipoEnum.Tierra, List.of("Agua", "Planta", "Hielo")),
                Map.entry(TipoEnum.Veneno, List.of("Tierra", "Psiquico")),
                Map.entry(TipoEnum.Volador, List.of("Electrico", "Hielo", "Roca"))
        );

        TipoEnum t1 = TipoEnum.valueOf(tipo1);
        TipoEnum t2 = TipoEnum.valueOf(tipo2);

        Set<String> debilidadTotal = new HashSet<>();
        if (debilidades.containsKey(t1)) debilidadTotal.addAll(debilidades.get(t1));
        if (debilidades.containsKey(t2)) debilidadTotal.addAll(debilidades.get(t2));

        return new ArrayList<>(debilidadTotal);
    }


    public void calcularProximoMovimientoEquipoRival(List<Pokemon> equipoRival) {

        String mejorMovimiento = "";

        System.out.println("EL MOVIMIENTO DEL EQUIPO RIVAL PUEDEN SER:");

        for (Pokemon rival : equipoRival) {

            if (mejorMovimiento.equals("movimiento".trim().toLowerCase())) {

                mejorMovimiento = rival.getEfectos().keySet().stream()
                        .filter(t -> t.equals("ataque") || t.equals("especial"))
                        .max(Comparator.comparingDouble(mov -> calcularDanio(rival, mov)))
                        .orElse("Desconocido");

                System.out.println("- " + rival.getNombre() + ": puede usar " + mejorMovimiento);

            } else {

                mejorMovimiento = rival.getEfectos().keySet().stream()
                        .filter(t -> t.equals("estado"))
                        .max(Comparator.comparing(mov -> calcularEfectos(rival, mov)))
                        .orElse("Desconocido");

            }
        }
    }


    public void calcularMejorMovimiento(ArrayList<Pokemon> equipoRival, ArrayList<Pokemon> miEquipo) {

        String mejorMovimiento = null;
        double mayorImpacto = Double.MIN_VALUE;
        double danio = 0;
        String efecto = "";

        for (Pokemon atacante : miEquipo) {

            for (String movimiento : atacante.getEfectos().keySet()) {

                for (Pokemon defensor : equipoRival) {

                    if (movimiento.equals("ataque") || movimiento.equals("especial")) {

                        danio = calcularDanio(atacante, movimiento);

                    } else {

                        efecto = calcularEfectos(atacante, movimiento);
                    }

                    if (danio > mayorImpacto) {

                        mayorImpacto = danio;
                        mejorMovimiento = atacante.getNombre() + " usa " + movimiento + " contra " + defensor.getNombre();
                    }
                }
            }
        }

        System.out.println("EL MOVIMIENTO QUE TIENES QUE HACER ES: " + mejorMovimiento);
        System.out.println("EL MOVIMIENTO QUE TIENES QUE HACER ES: " + efecto);
    }
}
