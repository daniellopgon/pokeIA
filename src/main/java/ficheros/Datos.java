package ficheros;

import clases.Efecto;
import clases.Pokemon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Datos {

    public static List<Pokemon> cargarPokemonsDesdeJson(String rutaJson) throws IOException {

        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Pokemon>>() {}.getType();

        try (FileReader reader = new FileReader(rutaJson)) {
            return gson.fromJson(reader, tipoLista);
        }
    }


    public static List<Efecto> cargarEfectosDesdeJson(String rutaJson) throws IOException {

        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Efecto>>() {}.getType();

        try (FileReader reader = new FileReader(rutaJson)) {
            return gson.fromJson(reader, tipoLista);
        }
    }

}
