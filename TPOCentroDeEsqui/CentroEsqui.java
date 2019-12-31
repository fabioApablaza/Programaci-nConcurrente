/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class CentroEsqui {

    private Confiteria unaConfiteria;
    private Lock cerrojoCurso;
    private boolean entradaInstructores, entradaEsquiadores, entradaMedios;
    private Condition instructores;
    private Condition estudiantes;
    private Random aleatorio;
    private MedioElevacion[] medios;
    private Curso[] cursos;

    public CentroEsqui(Confiteria unaConfi) {
        //Constructor
        this.unaConfiteria = unaConfi;
        this.aleatorio = new Random();
        this.cerrojoCurso = new ReentrantLock(true);
        this.estudiantes = cerrojoCurso.newCondition();
        this.instructores = cerrojoCurso.newCondition();
        this.entradaMedios = false;
        this.entradaEsquiadores = false;
        this.entradaInstructores = false;
        this.medios = new MedioElevacion[4];
        this.cursos = new Curso[5];
        this.crearMediosElevacion();
        this.crearCursos();
    }


    public void crearMediosElevacion() {
        //Metodo para crear los medios de elevación en el arreglo de medios
        for (int i = 0; i < medios.length; i++) {
            medios[i] = new MedioElevacion((i + 1), this);

        }
    }

    public void crearCursos() {
        //Metodo para crear las clases donde los esquiadores tomaran cursos
        for (int i = 0; i < cursos.length; i++) {
            cursos[i] = new Curso((i + 1));

        }
    }

    public synchronized void entradaAlCentro() throws InterruptedException {
        //Metodo en el cual los hilos esquiadores esperan la apertura del centro de esqui
        while (!this.entradaEsquiadores) {//los esquiadores esperan hasta que se abra el centro de esqui
            this.wait();
        }
    }
    //Medios de elevacion
    public synchronized void entradaMedios() {
        //Metodo para simular la cola de espera para entrar a los medios
            while (!this.entradaMedios && this.entradaEsquiadores) {
                /* se usan estas dos condiciones para poder liberar a los esquiadores que quedan atrapados */
                try {
                    /*Los esquiadores que quieran usar los medios de elevacion
                    tendran que esperar a que se abra los accesos a los mismos*/
                    this.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        
    }

    public void accederMedio(int idEsquiador, boolean pase, int idMedio) throws InterruptedException {
        //Metodo donde se verifica que los esquiadores tenga el pase para poder acceder a los medios
        if (pase) {//Se verifica si el esquiador tiene un pase para acceder a los molinetes
            medios[idMedio].darAcceso(idEsquiador);
            medios[idMedio].salirMedio(idEsquiador);
        } else {
            System.out.println("El esquiador " + idEsquiador + " no tiene un pase");
        }

    }

    public synchronized void abrirAccesosMedioElevacion() {
        //Modulo para permitir acceder a los medios de elevacion
        System.out.println("Se abre el acceso a los medios de elevacion");
        
        this.entradaMedios = true;
        
        this.notifyAll();

    }

    public void cerrarAccesoMedioElevacion() {
        //Modulo para cerrar el acceso a los medios de elevacion
        System.out.println("Se cierra el acceso a los medios de elevacion");
        this.entradaMedios = false;
    }

    //Curso de esqui
    public int busquedaCurso() throws InterruptedException {
        //Metodo que en el cual los esquiadores buscan algun curso de esqui que no este lleno
        //Si encuentran uno se les da un Identificador que es el numero de aula al cual estan asignados sino se les devuelve el 5
        int claseAsignada = 0, CANTMAX = 4;
        boolean condicionDeSalida = true;
        try {
            this.cerrojoCurso.lock();
            while (claseAsignada < cursos.length && condicionDeSalida) {
                if (cursos[claseAsignada].getCantidadCurso() < CANTMAX) {
                    cursos[claseAsignada].incrementarCantidadCurso();
                    condicionDeSalida = false;
                    if (cursos[claseAsignada].getCantidadCurso() == CANTMAX) {
                        instructores.signal();
                    }
                } else {
                    claseAsignada++;
                }
            }

        } finally {
            cerrojoCurso.unlock();
        }
        return claseAsignada;
    }

    public boolean entrarCurso(int idCurso, int idEsquiador) {
        return cursos[idCurso].contratarCurso(idEsquiador);
    }

    public void salirCurso(int idCurso, int idEsquiador) {
        cursos[idCurso].salirCurso(idEsquiador);
    }

    public void cabinaInstructores(String nombre) {
        //Metodo de simulacion donde los instructores esperan
        try {
            this.cerrojoCurso.lock();
            while (!this.entradaInstructores) {
                this.instructores.await();
            }
            System.out.println("El instructor " + nombre + " esta por dar una clase de esqui");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.cerrojoCurso.unlock();
        }
    }

    public synchronized int buscarNumClase(String nombre) throws InterruptedException, BrokenBarrierException {
        //Metodo en el cual los instructores reciben el numero de clase al que deben acudir
        int claseAsignada = 0, CANTMAX = 4;
        boolean condicionDeSalida = true;
        while (claseAsignada < cursos.length && condicionDeSalida) {
            if (cursos[claseAsignada].getCantidadCurso() == CANTMAX) {
                condicionDeSalida = false;
                /* El instructor incrementa de nuevo el contador de alumnos 
                del curso para que otro instructor no fuera a ir al mismo curso*/
                cursos[claseAsignada].incrementarCantidadCurso();
            } else {
                claseAsignada++;
            }
        }

        return claseAsignada;
    }

    public void terminarCurso(String nombre, int numCurso) {
        cursos[numCurso].terminarCurso(nombre);
    }

//Confiteria
    public void entradaConfiteria(int idEsquiador) throws InterruptedException {
        this.unaConfiteria.entrarConfiteria();//El esquiador entra a la confiteria
        if (this.unaConfiteria.entrarCaja(idEsquiador) == 1) {//El esquiador decide si comprar postre o no
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
            this.unaConfiteria.entrarMostradorPostre(idEsquiador);
        } else {
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
        }
        this.unaConfiteria.salirConfiteria();
    }

    //getters and setters
    public void abrirEntradaInstructores() {
        //El hilo tiempo en determinada hora deja trabajar a los instructores
        this.entradaInstructores = true;
    }

    public void cerrarEntradaInstructores() {
        //El hilo tiempo no deja que actuen los hilos instructores y dar por terminada la jornada laboral de los intructores
        this.entradaInstructores = false;
    }

    public synchronized void abrirEntradaEsquiadores() throws InterruptedException {
        this.entradaEsquiadores = true;
        System.out.println("Se abre el acceso a los esquiadores");
        this.notifyAll();
    }

    public synchronized void cerrarEntradaEsquiadores() {
        /*Metodo en el cual se cierra el acceso al centro de esqui, se muestra la cantidad de personas
        que utilizo cada medio y se restable a cero el contador de personas de cada medio de elevacion*/
        this.entradaEsquiadores = false;
        this.notifyAll();//Esto se hace por si algun esquiador quedo atrapado en la cola de espera de los medios

        System.out.println("Uso de medios de elevacion: ");
        for (int i = 0; i < medios.length; i++) {
            System.out.println("Medio de elevacion N°" + (i + 1) + " : " + this.medios[i].getCantidadPersonas());
            medios[i].resetearContadorpersonas();//Se reestablecen los valores de cada contador de personas a cero
        }
        for (int j = 0; j < cursos.length; j++) {
            cursos[j].reiniciarCantidad();
        }
    }

    public boolean getEntradaInstructores() {
        return this.entradaInstructores;
    }

    public boolean getEntradaEsquiadores() {
        return this.entradaEsquiadores;
    }
    public boolean getEntradaMedios(){
        return this.entradaMedios;
    }
}
