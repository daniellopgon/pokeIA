package clases;

public class Estadistica {
    private double vida;
    private double ataque;
    private double defensa;
    private double velocidad;
    private double ataqueEspecial;
    private double defensaEspecial;

    public Estadistica() {
    }

    public Estadistica(double vida, double ataque, double defensa, double velocidad, double ataqueEspecial, double defensaEspecial) {

        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
    }


    public double getVida() {
        return vida;
    }


    public void setVida(double vida) {
        this.vida = vida;
    }


    public double getAtaque() {
        return ataque;
    }


    public void setAtaque(double ataque) {
        this.ataque = ataque;
    }


    public double getDefensa() {
        return defensa;
    }


    public void setDefensa(double defensa) {
        this.defensa = defensa;
    }


    public double getVelocidad() {
        return velocidad;
    }


    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }


    public double getAtaqueEspecial() {
        return ataqueEspecial;
    }


    public void setAtaqueEspecial(double ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }


    public double getDefensaEspecial() {
        return defensaEspecial;
    }


    public void setDefensaEspecial(double defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

}
