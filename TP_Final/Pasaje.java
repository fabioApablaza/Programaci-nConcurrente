package TP_Final;

/**
 *
 * @author Mariano
 */
public class Pasaje {

    private String aerolinea;
    private String destino;
    private String pais;
    private String numVuelo;
    private String horario;
    private char terminal;
    private int numPuestoEmbarque;

    public Pasaje() {
    }

    //segun la aerolinea en la que saco el pasaje se designa el puesto de atencion
    public void setAerolinea(String aer) {
        this.aerolinea = aer;
    }

    public String getAerolinea() {
        return this.aerolinea;
    }

    public void setNumVuelo(String n) {
        this.numVuelo = n;
    }

    public String getNumVuelo() {
        return this.numVuelo;
    }

    public void setHorario(String hora) {
        this.horario = hora;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setDestino(String d) {
        this.destino = d;
    }

    public String getDestino() {
        return this.destino;
    }

    public void setPais(String p) {
        this.pais = p;
    }

    public String getPais() {
        return this.pais;
    }

    public void setTerminal(char term) {
        this.terminal = term;
    }

    public char getTerminal() {
        return this.terminal;
    }

    public void setPuestoEmbarque(int emb) {
        this.numPuestoEmbarque = emb;
    }

    public int getPuestoEmbarque() {
        return this.numPuestoEmbarque;
    }

    public String aCadena() {
        return "Destino " + this.destino + " Pais " + this.pais + " aerolinea " + this.aerolinea + " Numero de Vuelo " + this.numVuelo + " hora " + this.horario;
    }
}