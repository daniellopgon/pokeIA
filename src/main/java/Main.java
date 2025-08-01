import clases.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static ficheros.Datos.cargarEfectosDesdeJson;
import static ficheros.Datos.cargarPokemonsDesdeJson;

public class Main {
    public static void main(String[] args) {

        List<Pokemon> pokemons = null;
        List<Efecto> efectos = null;

        try {
            pokemons = cargarPokemonsDesdeJson("src/main/resources/info.json");
            efectos = cargarEfectosDesdeJson("src/main/resources/efectos.json");

//            for (Efecto efecto : efectos) {
//                System.out.println("Efecto: " + efecto.getNombre() + ", tipo: " + efecto.getTipo());
//                if (efecto.getEfectoList() == null || efecto.getEfectoList().isEmpty()) {
//                    System.out.println("  ⚠ efectoList vacío o null");
//                } else {
//                    for (AccionEfecto ae : efecto.getEfectoList()) {
//                        System.out.printf("  Acción: %s -> %.2f (%s)%n",
//                                ae.getEstadistica(), ae.getValor(), ae.getModificador());
//                    }
//                }
//            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo JSON: " + e.getMessage());
        }

        if (pokemons != null) {
            EstadisticaCombate estadisticaCombate = new EstadisticaCombate();
            Combate combate = new Combate();
            Scanner sc = new Scanner(System.in);
            DatosRival datos = new DatosRival();
            Calculo calculo = new Calculo();

            System.out.println();
            Map<String, Map<String, Integer>> topTiers = estadisticaCombate.estadisticasGenerales(pokemons);
            estadisticaCombate.estadisticasTotales(topTiers);
            System.out.println();
            List<Pokemon> pokemonsRival = datos.obtenerDatosRival(sc, pokemons);
            List<Pokemon> miEquipo = datos.obtenerDatosRival(sc, pokemons);
            assert efectos != null;
            datos.addListaAccionEfectoAlEquipo(pokemonsRival, efectos);
            combate.equipar(pokemonsRival);
            datos.addListaAccionEfectoAlEquipo(miEquipo, efectos);
            combate.equipar(miEquipo);
            estadisticaCombate.imprimirDatos(pokemonsRival);
            System.out.println();
            estadisticaCombate.imprimirDatos(miEquipo);
            System.out.println();
            datos.obtenerEstrategia(pokemonsRival);
            System.out.println();
            calculo.calcularProximoMovimientoEquipoRival(pokemonsRival);
            System.out.println();
            calculo.calcularProximoMovimientoEquipoRival(miEquipo);
        } else {
            System.out.println("No se pudo cargar la lista de pokemons.");
        }
    }
}
