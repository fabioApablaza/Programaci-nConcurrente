package TP_Final;

/**
 *
 * @author Mariano
 */
public class ConducirTren extends Thread{

    private Tren tren;
    private Hora reloj;

    public ConducirTren(Tren t, Hora r) {
        this.tren = t;
        this.reloj = r;
    }

    public void run() {
        while (true) {
            synchronized (this.reloj) { 
                // Verificamos que el tren no se conduzca cuando el aerepuerto este cerrado sin pasajeros.
                while (reloj.getHora() < 6) {
                    try {
                        this.reloj.wait();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            tren.controlarPartidasDeTren();
        }
    }
}
