package TP_Final;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Mariano
 */
public class shopping {

    private Semaphore capacidad; //controla la capacidad del freeshop
    private ReentrantLock caja1; //simula el cobro por caja1
    private ReentrantLock caja2; //simula el cobro por caja2
    private char letra;

    public shopping(char l) {
        this.capacidad = new Semaphore(30); // capacidad maxima de pasajeros del shopping.
        this.caja1 = new ReentrantLock();
        this.caja2 = new ReentrantLock();
        this.letra = l;
    }

    public void ingresarAFreeShop(Pasajero unPasajero) {
        Random aleatorio = new Random();

        try {
            this.capacidad.acquire();

            System.out.println("El pasajero " + unPasajero.getIdentificador() + " ingresa al shopping de la terminal " + this.letra);

            Thread.sleep(300);
            if (aleatorio.nextInt(2) == 0) { //random para ver si pasajero compra o solo mira.

                System.out.println("El pasajero " + unPasajero.getIdentificador() + " decide comprar.");

                if ((aleatorio.nextInt(2) + 1) == 1) {// random para ver por cual caja va a pasar.

                    this.caja1.lock();
                    System.out.println("El pasajero " + unPasajero.getIdentificador() + " pasando por caja 1.");
                    Thread.sleep(200);
                    this.caja1.unlock();

                } else {

                    this.caja2.lock();
                    System.out.println("El pasajero " + unPasajero.getIdentificador() + " pasando por caja 2.");
                    Thread.sleep(200);
                    this.caja2.unlock();

                }
            } else {
                System.out.println("El pasajero " + unPasajero.getIdentificador() + " decide mirar solamente.");
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            this.capacidad.release();
            System.out.println("El pasajero " + unPasajero.getIdentificador() + " sale del shopping de la terminal " + this.letra);
        }
    }
}
