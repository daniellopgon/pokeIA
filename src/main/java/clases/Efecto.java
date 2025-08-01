package clases;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Efecto {
    private String nombre;
    private transient BiConsumer<Pokemon, String> efectoBi;
    @SerializedName("efectos")
    private List<AccionEfecto> efectoList;
    private String tipo;
    private String tipoMovimiento;

    public Efecto(String nombre, BiConsumer<Pokemon, String> efecto, String tipo, String tipoMovimiento) {

        this.nombre = nombre;
        this.efectoBi = efecto;
        this.efectoList = new ArrayList<>();
        this.tipo = tipo;
        this.tipoMovimiento = tipoMovimiento;
    }


    public Efecto() {
    }


    public String getTipoMovimiento() {
        return tipoMovimiento;
    }


    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public BiConsumer<Pokemon, String> getEfectoBi() {
        return efectoBi;
    }


    public void setEfectoBi(BiConsumer<Pokemon, String> efectoBi) {
        this.efectoBi = efectoBi;
    }


    public List<AccionEfecto> getEfectoList() {
        return efectoList;
    }


    public void setEfectoList(List<AccionEfecto> efectoList) {
        this.efectoList = efectoList;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
