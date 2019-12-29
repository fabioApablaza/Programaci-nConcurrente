/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Apablaza
 */
public class Confiteria {

    private static final int CAPACIDAD= 100; //CAPACIDAD MAXIMA DE LA CONFITERIA
    private int cantidadComensales ;// Cantidad de comensales que entraran a la confiteria
    private Random decisiones ;// Variable aleatoria que simulara las decisiones
    private ReentrantLock mostradorComidaRapida1;//Simula el mostrador de comida rapida 1
    private ReentrantLock mostradorComidaRapida2;//Simula el mostrador de comida rapida 2
    private Semaphore caja;//Semaforo binario para controlar el acceso a la caja


    public Confiteria() {
        cantidadComensales = 0;
        decisiones = new Random();
        mostradorComidaRapida1 = new ReentrantLock();
        mostradorComidaRapida2 = new ReentrantLock();
        caja = new Semaphore(1, true);

    }

    public synchronized void entrarConfiteria() throws InterruptedException {
        //Modulo de la entrada a la confiteria
        while (this.cantidadComensales >= CAPACIDAD) {
            this.wait();
        }
        this.cantidadComensales++;
    }

    public synchronized void salirConfiteria() {
        //Metodo de salida de los esquiadores de la confiteria
        this.cantidadComensales--;
        this.notify();
    }

    public int entrarCaja(int idEsquiador) throws InterruptedException {
        //Metodo en el cual los esquiadores simulan pasar por la caja
        int postre;
        this.caja.acquire();//Un solo esquiador por vez paga por caja
        postre = decisiones.nextInt(2);//Variable que decide si el esquiador compra o no un postre
        if (postre == 1) {
            System.out.println("El esquiador " + idEsquiador + " decidio comprar postre");
        }
        System.out.println("El esquiador " + idEsquiador + " esta pagando en la caja");
        Thread.sleep(200);
        this.caja.release();
        return postre;
    }

    public void entrarMostradorComidaRapida(int idEsquiador) throws InterruptedException {
        //Metodo para simular el retiro de la comida rapida del mostrador
        if (decisiones.nextInt(2) == 0) {//El esquiador decide de cual mostrador quiere sacar su comida 
            mostradorComidaRapida1.lock();
            System.out.println("El esquiador " + idEsquiador + " esta sacando su comida del primer mostrador");
            Thread.sleep(200);
            mostradorComidaRapida1.unlock();
        } else {
            mostradorComidaRapida2.lock();
            System.out.println("El esquiador " + idEsquiador + " esta sacando su comida del segundo mostrador");
            Thread.sleep(200);
            mostradorComidaRapida2.unlock();
        }
    }

    public synchronized void entrarMostradorPostre(int idEsquiador) throws InterruptedException {
        //Metodo para simular el retiro del postre del mostrador
        System.out.println("El esquiador " + idEsquiador + " esta sacando un postre del mostrador");
        Thread.sleep(200);       
    }

}
