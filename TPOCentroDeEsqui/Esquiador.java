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
    private Random decision;//variable la cual simula la desicion del equiador por cual medio de elevacion desea subir
    private boolean pase;//variable tarjeta que habilita al esquiador a usar los molinetes

    public Esquiador(int id, CentroEsqui unC, boolean pass) {
        //Constructor
        this.id = id;
        this.unCentro = unC;
        this.pase = pass;
        this.decision = new Random();
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
        int numClase = 0, tipoClase;
        boolean cursoCompleto = true;

        /*
        El esquiador toma una decisión dependiendo del valor que se asigne a tipoClase
            Si el valor asignado es: 
                -0 entonces el esquiador quiso tomar una clase de Sky
                -1 entonces el esquiador quiso tomar una clase de Snowboard
         */
        tipoClase = decision.nextInt(2);

        if (unCentro.getHorarioInstructores()) {//Se verifica que los instructores esten en su horario de trabajo

            if (tipoClase == 0) {//El esquiador quiso tomar una clase de Sky

                System.out.println("El esquiador " + id + " quiere tomar una clase de Sky");

                numClase = unCentro.busquedaCursoSky();

            } else {//El esquiador desea tomar una clase de Snowboard

                System.out.println("El esquiador " + id + " quiere tomar una clase de Snowboard");

                numClase = unCentro.busquedaCursoSnowboard();

            }

            if (numClase < 5) {//Se verifica que el esquiador haya encontrado un curso
                System.out.println("Esquiador " + id + " con clase " + (numClase + 1));

                cursoCompleto = unCentro.entrarCurso(numClase, id);

                if (cursoCompleto) {
                    unCentro.salirCurso(numClase, id);
                }

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
        int dec, pos;
        try {

            while (true) {
                dec =decision.nextInt(3)+1;

                unCentro.entradaAlCentro();//Los esquiadores entran al centro de esqui

                /* De nuevo se verifica que el acceso al centro sea correcto, 
                esto se realiza para verificar que no accedan los esquiadores a ultima hora*/
                if (unCentro.getEntrada()) {

                    switch (dec) {
                        case 1: {//los esquiadores deciden subir por el primer medio de elevacion
                            //Mediante un Random se calcula un numero entre el 0 y 3 para acceder a cualquier medio
                            dec = this.decision.nextInt(4);

                            /*Esto se hace para que no quede ningun esquiador 
                        atrapado despues de que se cierran los medios de elevacion*/
                            if (unCentro.getEntradaMedios()) {
                                
                                if (pase) {

                                pos = unCentro.accederMedio(id, dec);

                                Thread.sleep(500);//Los esquiadores esperan a sentarse en la silla y dejan actuar al hilo gestor de la cinta

                                unCentro.salirMedio(id, dec, pos);

                                
                                    esquiando();
                                    descansar();
                                } else {
                                    System.out.println("El esquiador " + id + " no tiene un pase y no puede acceder a ningún medio de elevación");
                                }

                            }

                            break;
                        }
                        case 2: {//los esquiadores deciden tomar un curso de esqui
                            
                            cursoEsqui();
                            
                            break;
                        }
                        case 3: {//los esquiadores deciden ir a la confiteria
                            unCentro.entradaConfiteria(id);
                            this.comer();
                            unCentro.salirConfiteria();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
