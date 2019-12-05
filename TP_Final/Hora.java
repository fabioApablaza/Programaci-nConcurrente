package TP_Final;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class Hora {
    private int hs;
    private Semaphore control; 

    public Hora(Semaphore c){
        this.hs= 5; 
        this.control= c;
    }

    public synchronized void setHora(){
        
        this.hs = this.hs % 24 + 1 ; // Mantengo un numero (la hora) entre 1 y 24
        System.out.println();
        System.out.println("ES LA HORA: "+this.hs);
        System.out.println();
        if(this.hs > 5){
            // Monitores utilizados en las clases MesaDeInforme, Terminal, conducirTren, tre.
            this.notifyAll(); 
        }
        if(this.hs>7){
            this.control.release(); // Semaforo utilizado en la clase Mensaje
        }       
    }

    public synchronized int getHora(){
        return this.hs;
    }
}
