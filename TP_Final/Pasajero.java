package TP_Final;

/**
 *
 * @author Mariano
 */
public class Pasajero extends Thread{

    private Pasaje pasaje;
    private MesaDeInformes mesa;
    private PuestoDeAtencion[] puestos;
    private Tren tren;
    private int id;
    private Terminal[] terminales;

    public Pasajero(int ident, Pasaje pas, MesaDeInformes m, PuestoDeAtencion[] p, Tren t, Terminal[] v) {
        this.id = ident;
        this.pasaje = pas;
        this.mesa = m;
        this.puestos = p;
        this.tren = t;
        this.terminales = v;
    }

    public void run() {
        
         // va a almacenar la posicion del puesto de atencion al que el pasajero se tiene que dirigir
         
        int pos;
        // Primero vamos a setear la aereolinea, destino, pais, numero de vuelo y el horario, devolviendonos un numero
        // que depende de la aereolinea la cual se le haya asignado al pasajero.
        pos = mesa.atenderPasajero(this); 
        
        //Luego vamos a setear el puesto de embarque y la terminal a partir del numero que obtuvimos anteriormente.
        puestos[pos].ingresaPasajero(this,pos);
        
        //Una ves que tiene todos sus datos cargados se sube al tren.
        this.tren.subirAlTren(this);
        
        // Una ves que el tren llega a las terminales correspondientes el pasajero ingresan a la misma.
        switch (this.getPasaje().getTerminal()) {
            case 'A':
                this.terminales[0].ingresarTerminal(this);
                break;
            case 'B':
                this.terminales[1].ingresarTerminal(this);
                break;
            case 'C':
                this.terminales[2].ingresarTerminal(this);
                break;
        }
    }

    public Pasaje getPasaje() {
        return this.pasaje;
    }

    public int getIdentificador() {
        return this.id;
    }
}
