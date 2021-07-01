/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.Random;
//import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Apablaza
 */
public class Confiteria {

    private static final int CAPACIDAD= 100; //CAPACIDAD MAXIMA DE LA CONFITERIA
    private int cantidadComensales ;// Cantidad de comensales que entraran a la confiteria
    private Random decisiones ;// Variable aleatoria que simulara las decisiones
    private ReentrantLock cerrojo[];//Simula el mostrador de comida rapida 1 y 2, la caja y el mostrador de postres

    public Confiteria() {
        cantidadComensales = 0;
        decisiones = new Random();
        cerrojo = new ReentrantLock[4];
        crearCerrojos();
    }
    
    private void crearCerrojos(){
        //Módulo para crear locks
        /* El arreglo cerrojos que es de tipo ReentrantLock es de longitud 4 ya que:
            Posicion 0 - Es para permitir a los hilos esquiadores la simulacion en caja de a un esquiador por vez.
            Posicion 1 - Es para sincronizar a los hilos esquiadores en el mostrador de comida 1.
            Posicion 2 - Es para sincronizar a los hilos esquiadores en el mostrador de comida 2.
            Posicion 3 - Es para sincronizar a los hilos esquiadores en el mostrador de postres.
     */
        for(int i=0; i<cerrojo.length;i++){
            cerrojo[i]= new ReentrantLock(true);
        }
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
        this.cerrojo[0].lock();//Un solo esquiador por vez paga por caja
        postre = decisiones.nextInt(2);//Variable que decide si el esquiador compra o no un postre
        if (postre == 1) {
            System.out.println("El esquiador " + idEsquiador + " decidio comprar postre");
        }
        System.out.println("El esquiador " + idEsquiador + " esta pagando en la caja");
        Thread.sleep(200);
        this.cerrojo[0].unlock();
        return postre;
    }

    public void entrarMostradorComidaRapida(int idEsquiador) throws InterruptedException {
        //Metodo para simular el retiro de la comida rapida del mostrador
        if (decisiones.nextInt(2) == 0) {//El esquiador decide de cual mostrador quiere sacar su comida 
            cerrojo[1].lock();
            System.out.println("El esquiador " + idEsquiador + " esta sacando su comida del primer mostrador");
            Thread.sleep(200);
            cerrojo[1].unlock();
        } else {
            cerrojo[2].lock();
            System.out.println("El esquiador " + idEsquiador + " esta sacando su comida del segundo mostrador");
            Thread.sleep(200);
            cerrojo[2].unlock();
        }
    }

    public void entrarMostradorPostre(int idEsquiador) throws InterruptedException {
        //Metodo para simular el retiro del postre del mostrador
        cerrojo[3].lock();
        System.out.println("El esquiador " + idEsquiador + " esta sacando un postre del mostrador");
        Thread.sleep(200);       
        cerrojo[3].unlock();
    }

}
