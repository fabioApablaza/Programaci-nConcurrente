/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza
 */
public class MedioElevacion {

    private int identificacion;// Identifiacion del medio de elevacion
    private CentroEsqui unCentroEsqui;//Recurso Compartido
    private Semaphore molinete;// Semaphoro que simulara a los molinetes que dejaran pasar a los n esquiadores
    private int cantidadPersonas;// Variable para contar cuantos esquiadores pasaron por este medio de elevacion
    private ReentrantLock cerrojo;// Cerrojo para sincronizar la suma de los esquiadores que usaron este medio de elevacion
    private CyclicBarrier barreraEntrada;
    private CyclicBarrier barreraSalida;
    private boolean condicionSilla;

    //Contructor
    public MedioElevacion(int id, CentroEsqui unCent) {
        this.identificacion = id;
        this.unCentroEsqui = unCent;
        this.cantidadPersonas = 0;
        this.condicionSilla = true;
    }

    public synchronized void esperaDeSilla() throws InterruptedException {
        //El hilo silla esperara aqui hasta que sus lugares esten ocupados por esquiadores
        while (this.condicionSilla) {
            this.wait();
        }
        System.out.println("La silla del medio de elevacion numero " + this.identificacion + " esta transportando a los esquiadores");
    }

    public void liberarEsquiadores() {
        //Despues de simular el transporte de los esquiadores las sillas deberan liberar a los esquiadores de la espera
        try {
            this.barreraSalida.await();//Los esquiadores transportados por la silla esperaran aqui hasta que el hilo silla los libere
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

            this.molinete.acquire();//Se dejan pasar n esquiadores                
            System.out.println("El esquiador " + idEsquiador + " paso por los molinetes del medio numero " + this.identificacion);

            this.cerrojo.lock();//sincronizo la suma de la cantidad de esquiadores pasan por los molinetes
            this.cantidadPersonas++;

            if (this.molinete.availablePermits() == 0) {//Se verifica que esten ocupados todos los lugares de la silla, para poder despertar al hilo silla
                this.condicionSilla = false;
                this.notify();
            }
            this.cerrojo.unlock();

            barreraEntrada.await(2000, TimeUnit.MILLISECONDS);//Barrera en la cual esperan los esquiadores               

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en el medio " + this.identificacion + " y los esquiadores se fueron");
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
    }

    public void salirMedio() throws InterruptedException {
        //Los esquiadores esperan a que el hilo silla termina de transportarlos
        try {
            this.barreraSalida.await();//los esquiadores esperan aqui
            this.molinete.release();//liberan los permisos de los molinetes para que otros esquiadores puedan pasar
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

    public void abrirMedio(int permisos) {
        this.molinete = new Semaphore(permisos, true);
        this.barreraEntrada = new CyclicBarrier(permisos);
        this.barreraSalida = new CyclicBarrier(permisos + 1);//se suma uno ya que se debe contar ademas de los N esquiadores al hilo Silla

    }

    public void cerrarMedio() {
        this.molinete.drainPermits();
    }
}
