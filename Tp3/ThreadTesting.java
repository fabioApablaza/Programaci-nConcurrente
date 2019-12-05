package Tp3;


public class ThreadTesting {

    public static void main(String[] args) {
        try {
            MiEjecuccion miHilo = new MiEjecuccion();
            Thread hilo= new Thread(miHilo);
            hilo.start();
            hilo.join();
            System.out.println("En el main");
        } catch (InterruptedException ex) {
            
        }
    }
}
