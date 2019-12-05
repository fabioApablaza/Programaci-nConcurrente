package TP_Final;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class Tren {

    private Semaphore terminalA;//Controlan los pasajeros que van a la terminal A
    private Semaphore terminalB;//Controlan los pasajeros que van a la terminal B
    private Semaphore terminalC;//Controlan los pasajeros que van a la terminal C
    private Semaphore capacidadTren;//Controla la capacidad de los que se suben al tren
    private int capacidadA;
    private int capacidadB;
    private int capacidadC;
    private final int capacidadTotal = 20;
    private int cantPasajeros;
    private Hora reloj;

    public Tren(Hora r) {
        this.terminalA = new Semaphore(0);
        this.terminalB = new Semaphore(0);
        this.terminalC = new Semaphore(0);
        this.capacidadTren = new Semaphore(capacidadTotal); // capacidad maxima del tren
        this.capacidadA = 0;
        this.capacidadB = 0;
        this.capacidadC = 0;
        this.cantPasajeros = 0;
        this.reloj = r;
    }

    public void conducir() {
        try {
            System.out.println();
            System.out.println("INICIO DEL RECORRIDO DEL TREN");
            System.out.println();
            Thread.sleep(300);

            //si hay algun pasajero que va a la terminal A sino pasa a la siguiente terminal,
            //lo mismo con el resto de las terminales.
            // Verificamos si hay pasajeros que 
            if (this.capacidadA > 0) {
                System.out.println("        Arribando a la terminal 'A' ");
                this.terminalA.release(this.capacidadA);
                // El valor del semaforo cambia de 0 a la cantidad de pasajeros de la terminal A
                Thread.sleep(300);
            }
            if (this.capacidadB > 0) {
                System.out.println("        Arribando a la terminal 'B' ");
                this.terminalB.release(this.capacidadB);
                Thread.sleep(300);
            }
            if (this.capacidadC > 0) {
                System.out.println("        Arribando a la terminal 'C' ");
                this.terminalC.release(this.capacidadC);
                Thread.sleep(300);
            }
            System.out.println();
            System.out.println("FIN DEL RECORRIDO DEL TREN, REGRESA AL COMIENZO DEL CAMINO");
            System.out.println();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            this.capacidadTren.release(this.cantPasajeros);
            this.cantPasajeros = 0;
        }

    }

    public void subirAlTren(Pasajero unPasajero) {
        //Simular la subida de los pasajeros al tren
        try {

            this.capacidadTren.acquire();//contola la cantidad de pasajeros a viajar
            switch (unPasajero.getPasaje().getTerminal()) {
                case 'A':
                    this.capacidadA++; // incrementa la cantidad
                    this.terminalA.acquire();//aca se traban todos los pasajeros hasta que el conductor los libere
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " bajando en terminal A");
                    this.capacidadA--;
                    break;

                case 'B':
                    this.capacidadB++;
                    this.terminalB.acquire();
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " bajando en la terminal B");
                    this.capacidadB--;
                    break;

                case 'C':
                    this.capacidadC++;
                    this.terminalC.acquire();
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " bajando en la terminal C");
                    this.capacidadC--;
                    break;

            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void controlarPartidasDeTren() {
        // Controla la partida del tren.
        // Si la cantidad de pasajeros es igual a la capacidad total del tren, se va al metodo conducir.
        // Si transcurren 18 segundos y no se llega a la capacidad total del tren sale del bucle.
        long inicio = System.currentTimeMillis();
        long fin = System.currentTimeMillis();
        final int segundosEspera = 18000;

        synchronized (this.reloj) {
            while (this.cantPasajeros != this.capacidadTotal && ((fin - inicio) < segundosEspera)) {
                try {
                    this.reloj.wait();
                    this.cantPasajeros = this.capacidadA + this.capacidadB + this.capacidadC;
                    fin = System.currentTimeMillis();
                    // Nos arroja una cierta cantidad de segundos que se va a ir restando
                    // con inicio para poder obtener los segundos que van transcurriendo.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
            }
        }
        // Debe existir al menos un pasajero dentro del tren para que este pueda arrancar.
        if (this.capacidadA > 0 || this.capacidadB > 0 || this.capacidadC > 0) {
            conducir();
        }
    }
}
