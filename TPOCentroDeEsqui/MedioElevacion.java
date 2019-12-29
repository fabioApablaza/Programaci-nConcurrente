/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class MedioElevacion {

    private int identificacion;// Identifiacion del medio de elevacion
    private int cantidadPersonas;// Variable para contar cuantos esquiadores pasaron por este medio de elevacion
    private CentroEsqui unCentroEsqui;//Recurso Compartido
    private Semaphore molinete;// Semaphoro que simulara a los molinetes que dejaran pasar a los n esquiadores
    private Semaphore mutex;
    private ReentrantLock cerrojo;// Cerrojo para sincronizar la suma de los esquiadores que usaron este medio de elevacion
    private Condition colaSilla;
    private CyclicBarrier barreraEntrada;
    private CyclicBarrier barreraSalida;
    private Silla[] sillasElevadoras;
    private boolean condicionSilla;
    private Thread[] hilosSillas;

    //Contructor
    public MedioElevacion(int id, CentroEsqui unCent) {
        this.identificacion = id;
        this.cantidadPersonas = 0;
        this.unCentroEsqui = unCent;
        this.cerrojo = new ReentrantLock(true);
        this.colaSilla = cerrojo.newCondition();
        this.condicionSilla = true;
        this.molinete = new Semaphore(id, true);
        this.mutex= new Semaphore(1);
        this.barreraEntrada = new CyclicBarrier(id);
        this.barreraSalida = new CyclicBarrier(id + 1);//se suma uno ya que se debe contar ademas de los N esquiadores al hilo Silla
        this.sillasElevadoras = new Silla[id];
        this.hilosSillas = new Thread[this.identificacion];
        crearSillasElevadoras();
    }

    //Cada medio tiene una cantidad determinadas de sillas elevadoras que dependen de la cantidad de molinetes que tenga    
    private void crearSillasElevadoras() {
        //Metodo para crear los hilos sillas e inicializarlos
        for (int i = 0; i < this.sillasElevadoras.length; i++) {
            this.sillasElevadoras[i] = new Silla((i + 1), this);
            this.hilosSillas[i] = new Thread(this.sillasElevadoras[i]);
            this.hilosSillas[i].start();

        }
    }

    public void esperaDeSilla() throws InterruptedException {
        /*Los hilos silla esperaran aqui hasta que sus lugares esten ocupados por esquiadores
        pero solamente de a una silla por vez*/
        try {
            this.cerrojo.lock();
            while (this.condicionSilla) {
                this.colaSilla.await();
            }
        } finally {
            this.cerrojo.unlock();
        }
        System.out.println("La silla del medio de elevacion numero " + this.identificacion + " esta transportando a los esquiadores");
    }

    public void liberarEsquiadores() {
        /*Posterior de simular el transporte de los esquiadores las sillas deberan liberar a los esquiadores de la espera
        osea la barrera de seguridad se levanta y deja bajar a los esquiadores*/

        try {
            this.barreraSalida.await();//Los esquiadores transportados por la silla esperaran aqui hasta que el hilo silla los libere
            System.out.println("La silla del medio N° "+identificacion+" libero a los esquiadores");
            this.condicionSilla = true;
        } catch (InterruptedException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void darAcceso(int idEsquiador) {

        try {
            System.out.println("El esquiador " + idEsquiador + " esta queriendo acceder a los molinetes del medio numero " + this.identificacion);
            //Simulación de los molinetes de cada medio de elevación
            this.molinete.acquire();//Se dejan pasar n esquiadores                
            System.out.println("El esquiador " + idEsquiador + " paso por los molinetes del medio numero " + this.identificacion);

            this.cerrojo.lock();//sincronizo la suma de la cantidad de esquiadores pasan por los molinetes
            this.cantidadPersonas++;

            if (this.molinete.availablePermits() == 0) {//Se verifica que esten ocupados todos los lugares de la silla, para poder despertar al hilo silla
                this.condicionSilla = false;
                this.colaSilla.signal();
            }
            this.cerrojo.unlock();
            /*Barrera en la cual esperan los esquiadores a que entren */
            barreraEntrada.await(2000, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en el medio " + this.identificacion + " y los esquiadores se fueron");
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
    }

    public void salirMedio(int idEsquiador) throws InterruptedException {
        //Los esquiadores esperan a que el hilo silla termina de transportarlos
        try {
            this.barreraSalida.await();//los esquiadores esperan aqui
            System.out.println("El esquiador "+idEsquiador+" salio del medio N° "+identificacion);
            this.molinete.release();//liberan los permisos de los molinetes para que otros esquiadores puedan salir del medio
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // getters and setters
    public int getCantidadPersonas() {
        return this.cantidadPersonas;
    }

    public void resetearContadorpersonas() {
        this.cantidadPersonas = 0;
    }

    public void cerrarMedio() {
        this.molinete.drainPermits();
    }

}
