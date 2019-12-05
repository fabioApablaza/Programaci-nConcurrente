
package Tp5;


public class test {
    public static void main(String[]args){
        SynchronizedCounter dato= new SynchronizedCounter();
        Hilo hilo1= new Hilo(dato);
        Hilo hilo2= new Hilo(dato);
        Thread h1= new Thread(hilo1);
        Thread h2= new Thread(hilo2);
        h1.run();
        h2.run();
        
    }
}
