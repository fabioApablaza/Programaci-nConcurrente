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
    private int idCurso; //El curso en el que le toca enseñar

    public Instructor(String nom, CentroEsqui unC, int idC) {
        //Constructor
        this.nombre = nom;
        this.unCentro = unC;
        idCurso=idC;
    }

    public void enseñar() {
        //Simulación
        try {
            System.out.println("El instructor " + nombre + " esta dando un curso de esqui");
            Thread.sleep(3000);
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
                unCentro.entradaAlCentro();//Los instructores esperan en la entrada con los esquiadores
                //unCentro.cabinaInstructores(nombre);
                //numClase = unCentro.buscarNumClase(nombre);
                numClase=unCentro.cabinaInstructores(idCurso,nombre);
                System.out.println(nombre+" cantClase: "+numClase);
                if (numClase == 4) {
                    enseñar();
                    unCentro.terminarCurso(nombre, idCurso);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Instructor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
