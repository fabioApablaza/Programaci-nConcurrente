package TP_Final;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class Mensaje extends Thread{

    private Hora reloj;
    private Vuelo listaDeVuelos;
    private Semaphore control;

    public Mensaje(Hora r, Vuelo v, Semaphore c) {
        this.reloj = r;
        this.listaDeVuelos = v;
        this.control = c;
    }
    
    public void run() {
        ArrayList<String[]> vuelos;
        while (true) {
            try {
                this.control.acquire();
                // A partir de la hora que es nos va a devolver todos los vuelos con sus datos para luego ser mostrados.
                vuelos = this.listaDeVuelos.getVuelos(this.reloj.getHora());
                
                for (int i = 0; i < vuelos.size(); i++) {

                    System.out.println("Partida del vuelo " + vuelos.get(i)[3] + " con destino " + vuelos.get(i)[0] 
                                    + "-" + vuelos.get(i)[1] + " Aerolinea: " + vuelos.get(i)[2] + " de la hora: " 
                                    + vuelos.get(i)[4]);
                }

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
