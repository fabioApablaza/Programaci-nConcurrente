/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class Instructor implements Runnable {

    private String nombre;
    private CentroEsqui unCentro;//objeto compartido

    public Instructor(String nom, CentroEsqui unC) {
        //Constructor
        this.nombre = nom;
        this.unCentro = unC;
    }

    public void enseñar() {
        //Simulación
        try {
            System.out.println("El instructor " + nombre + " esta dando un curso de esqui");
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void run() {
        while (true) {
            int numClase;
            try {
                unCentro.cabinaInstructores(nombre);
                numClase = unCentro.buscarNumClase(nombre);
                if (numClase < 5) {
                    System.out.println("Al instructor " + nombre + " se le asigno el aula n° " + (numClase + 1));
                    enseñar();
                    unCentro.terminarCurso(nombre, numClase);
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
