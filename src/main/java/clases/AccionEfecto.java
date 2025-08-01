package clases;

public class AccionEfecto {
    private String estadistica;
    private String modificador;
    private double valor;
    private String objetivo;
    private String estado;
    private String tipo;


    public AccionEfecto(String estadistica, String modificador, double valor, String objetivo, String estado, String tipo) {

        this.estadistica = estadistica;
        this.modificador = modificador;
        this.valor = valor;
        this.objetivo = objetivo;
        this.estado = estado;
        this.tipo = tipo;
    }


    public AccionEfecto() {
    }


    public String getEstadistica() {
        return estadistica;
    }


    public void setEstadistica(String estadistica) {
        this.estadistica = estadistica;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getModificador() {
        return modificador;
    }


    public void setModificador(String modificador) {
        this.modificador = modificador;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }


    public String getObjetivo() {
        return objetivo;
    }


    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }

}
