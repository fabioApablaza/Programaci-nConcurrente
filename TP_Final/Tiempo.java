package TP_Final;

/**
 *
 * @author Mariano
 */
public class Tiempo extends Thread {

    private Hora reloj;

    public Tiempo(Hora r) {
        this.reloj = r;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10000); // (cada 10 segundos aumenta una hora transcurrida)
                this.reloj.setHora();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
