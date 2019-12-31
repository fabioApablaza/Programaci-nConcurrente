/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class Esquiador implements Runnable {

    private int id;//identificador del esquiador
    private CentroEsqui unCentro; //Objeto Compartido
    private Random desicion;//variable la cual simula la desicion del equiador por cual medio de elevacion desea subir
    private boolean pase;//variable tarjeta que habilita al esquiador a usar los molinetes

    public Esquiador(int id, CentroEsqui unC, boolean pass) {
        //Constructor
        this.id = id;
        this.unCentro = unC;
        this.pase = pass;
        this.desicion = new Random();
    }

    public void esquiando() {
        //simulacion del esquiador esquiando
        try {
            System.out.println("El esquiador " + id + " esta esquiando");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void descansar() {
        //simulacion del esquiador descansando
        try {
            System.out.println("El esquiador " + id + " esta descansando");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cursoEsqui() throws InterruptedException {
        int numClase = 0;
        boolean cursoCompleto = true;
        numClase = unCentro.busquedaCurso();
        System.out.println("Esquiador " + id + " con clase " + (numClase + 1));
        if (numClase != 5) {
            cursoCompleto = unCentro.entrarCurso(numClase, id);
            if (cursoCompleto) {
                unCentro.salirCurso(numClase, id);
            }
        }
    }

    public void comer() {
        //Simulacion, el esquiador comiendo su comida
        try {
            System.out.println("El esquiador " + id + " esta comiendo su comida");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int decision;
        try {
            while (true) {
                decision = desicion.nextInt(3)+1;
                
                unCentro.entradaAlCentro();//Los esquiadores entran al centro de esqui

                switch (1) {
                    case 1: {//los esquiadores deciden subir por el primer medio de elevacion
                        //Mediante un Random se calcula un numero entre el 0 y 3 para acceder a cualquier medio
                        decision = desicion.nextInt(4);
                        unCentro.entradaMedios();//Cola de espera para los medios
                        /*Esto se hace para que no quede ningun esquiador 
                        atrapado despues de que se cierran los medios de elevacion*/
                        if (unCentro.getEntradaMedios()) {
                            unCentro.accederMedio(id, pase, 0);
                            if (pase) {
                                esquiando();
                                descansar();
                            }

                        }
                        break;
                    }
                    case 2: {//los esquiadores deciden tomar un curso de esqui
                        if (unCentro.getEntradaInstructores()) {
                            cursoEsqui();
                        }
                        break;
                    }
                    case 3: {//los esquiadores deciden ir a la confiteria
                        unCentro.entradaConfiteria(id);
                        this.comer();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
