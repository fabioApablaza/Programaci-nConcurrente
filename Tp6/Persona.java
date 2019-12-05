/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp6;

/**
 *
 * @author user
 */
public class Persona implements Runnable {

    private boolean esJuv = false;
    private GestorSala unGestor;

    public Persona(GestorSala unGes) {
        this.unGestor = unGes;

    }

    public Persona(GestorSala unGes, boolean am) {
        this.unGestor = unGes;
        this.esJuv = am;
    }

    @Override
    public void run() {
        while (true) {
            if (esJuv) {
                try {
                    unGestor.entrarSalaJuvilado();
                    Thread.sleep(2000);
                    unGestor.salirSala();
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    unGestor.entrarSala();
                    Thread.sleep(2000);
                    unGestor.salirSala();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
