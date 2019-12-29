/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class Silla implements Runnable {

    private int identificacion;
    private MedioElevacion unMedio;

    //Constructor
    public Silla(int id, MedioElevacion unMed) {
        this.identificacion = id;//identificación de la silla
        this.unMedio = unMed;
    }

    public void transportar() {
        //Simulacion del transporte de los esquiadores
        try {
            System.out.println("La silla numero " + identificacion + " esta transportando a esquiadores");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Silla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        //Ejecuccion del hilo silla
        while (true) {
            try {
                unMedio.esperaDeSilla();
            } catch (InterruptedException ex) {
                Logger.getLogger(Silla.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.transportar();
            unMedio.liberarEsquiadores();
        }
    }
}
