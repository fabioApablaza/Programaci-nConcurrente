package TP_Final;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class MesaDeInformes {

    private Hora hs;
    private Semaphore Singreso;
    private Vuelo vuelosDisponibles;

    public MesaDeInformes(Hora rel, Vuelo v) {
        this.hs = rel;
        this.vuelosDisponibles = v;
        Singreso = new Semaphore(1);
    }

    public int atenderPasajero(Pasajero unPasajero) {

        int pos = -1;
        String aereolinea;
        //Si el aereopuerto se encuentra cerrado los pasajeros se duermen hasta que el mismo se habra.
        synchronized (this.hs) {
            while (hs.getHora() < 6 || hs.getHora() > 22) {
                System.out.println("Aereopuerto cerrado, el pasajero "+unPasajero.getIdentificador()+" espera a que abra el aereopuerto.");
                try {                  
                    this.hs.wait();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        try {
            //controla que se llame de a un pasajero
            Singreso.acquire();

            System.out.println("El pasajero " + unPasajero.getIdentificador() + " comienza a ser atendido...");

            //se le asigna un vuelo en este momento, para asegurar de que los pasajeros tengan tiempo para 
            //moverse dentro del aeropuerto y no pierdan sus vuelos.
            setPasaje(unPasajero);

            //Segun la aereolinea que se les asigno se va a almacenar un numero la cual corresponde a cda una de ellas. 
            aereolinea = unPasajero.getPasaje().getAerolinea();

            if (aereolinea.equalsIgnoreCase("AereolineasArgentinas")) {
                System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de Aerolineas Argentinas");
                pos = 0;
            } else {
                if (aereolinea.equalsIgnoreCase("Eurowings")) {
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de aerolineas Eurowings");
                    pos = 1;
                } else {
                    if (aereolinea.equalsIgnoreCase("Alitalia")) {
                        System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de aerolineas Alitalia");
                        pos = 2;
                    } else {
                        if (aereolinea.equalsIgnoreCase("Avianca")) {
                            System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de aerolineas Avianca");
                            pos = 3;
                        } else {
                            if (aereolinea.equalsIgnoreCase("Aeromexico")) {
                                System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de aerolineas Aeromexico");
                                pos = 4;
                            } else {
                                if (aereolinea.equalsIgnoreCase("Iberia")) {
                                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " derivado al puesto de aerolineas Iberia");
                                    pos = 5;
                                } else {
                                    System.out.println("La aerolinea " + unPasajero.getPasaje().getAerolinea() + " indicada no opera en este aeropuerto");
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Se llama al siguiente pasajero");

            Singreso.release();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return pos;
    }

    private void setPasaje(Pasajero unPasajero) {

        String[] textoDatos;
        // En un arreglo nos va almacenar todos los datos de los vuelos que luego van a ser seteados de acuerdo a la posicion.
        textoDatos = this.vuelosDisponibles.calcularVuelo();

        //en la posicion 0 del arreglo generado por split se guarda el destino
        unPasajero.getPasaje().setDestino(textoDatos[0]);

        //en la posicion 1 del arreglo generado por split se guarda el pais de destino
        unPasajero.getPasaje().setPais(textoDatos[1]);

        //en la posicion 2 del arreglo generado por split se guarda la aerolinea
        unPasajero.getPasaje().setAerolinea(textoDatos[2]);

        //en la posicion 3 del arreglo generado por split se guarda el numero de vuelo
        unPasajero.getPasaje().setNumVuelo(textoDatos[3]);

        //en la posicion 4 del arreglo generado por split se guarda el horario
        unPasajero.getPasaje().setHorario(textoDatos[4]);
    }
}
