package TP_Final;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class Aereopuerto {

    public static void main(String[] args) {

        Semaphore control = new Semaphore(0);
        String terminales = "ABC";

        // Arreglo de pasajeros.
        Pasajero[] arrePasajeros = new Pasajero[500]; // Cantidad de pasajeros.

        // Arreglo de aereolineas.
        String[] arreAerolineas = {"AereolineasArgentinas", "Eurowings", "Alitalia", "Avianca", "Aeromexico", "Iberia"}; // Nombres de las aereolineas

        // Arreglo de Puestos de atencion.
        PuestoDeAtencion[] arrePuestosDeAtencion = new PuestoDeAtencion[6]; // cantidad de puestos de atencion
        Hora hs = new Hora(control);

        // Carga de todos los vuelos.
        Vuelo archivosVuelos = new Vuelo(hs);
        archivosVuelos.cargarVuelos();//carga los vuelos en una estructura de datos Map

        // Carga todas las terminales.
        Terminal[] arreTerminal = new Terminal[3]; // Cantidad de terminales
        cargarTerminal(arreTerminal, terminales, hs);

        // Carga todos los puestos de embarques a partir de una estructura Map.
        PuestosDeEmbarques puestos = new PuestosDeEmbarques();
        puestos.cargarPuestosDeEmbarque();

        cargarPuestosDeAtencion(arrePuestosDeAtencion, arreAerolineas, puestos);

        Tren tren = new Tren(hs);
        ConducirTren conductor = new ConducirTren(tren, hs);

        MesaDeInformes mesa = new MesaDeInformes(hs, archivosVuelos);

        Tiempo segundos = new Tiempo(hs);
        segundos.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        Mensaje msn = new Mensaje(hs, archivosVuelos, control);
        msn.start();

        conductor.start();

        cargarPasajeros(mesa, arrePasajeros, arrePuestosDeAtencion, tren, hs, arreTerminal);

    }

    public static void cargarPuestosDeAtencion(PuestoDeAtencion[] arrePuestos, String[] aerolineas, PuestosDeEmbarques pe) {
        for (int i = 0; i < arrePuestos.length; i++) {
            //se inicializan los distintos puestos de atencion, con el nombre de la aerolinea a la que corresponden.
            arrePuestos[i] = new PuestoDeAtencion(aerolineas[i], pe);

        }
    }

    public static void cargarTerminal(Terminal[] arreTerm, String nomTerm, Hora r) {
        for (int i = 0; i < arreTerm.length; i++) {
            // se inicializan las terminales.
            arreTerm[i] = new Terminal(nomTerm.charAt(i), r);

        }
    }

    public static void cargarPasajeros(MesaDeInformes mesa, Pasajero[] arrePasaj, PuestoDeAtencion[] arrePuestos, Tren tren, Hora r, Terminal[] arreTerm) {

        Random selecAero = new Random();	//random para crear pasajes con una aerolinea predeterminada

        for (int i = 0; i < arrePasaj.length; i++) {
            /*
             * a todos los pasajes se le pone la hora 0 de vuelo, luego se setea
             * cuando llega al puesto de atencion para que a partir de ese
             * momento tenga un plazo para desplazarse por el aeropuerto.
             */
            Pasaje unPasaje = new Pasaje();
            arrePasaj[i] = new Pasajero(i, unPasaje, mesa, arrePuestos, tren, arreTerm);
        }

        for (int i = 0; i < arrePasaj.length; i++) {
            arrePasaj[i].start();
            try {
                Thread.sleep(selecAero.nextInt(2000));	// se da un espacio para que no salgan todos los pasajeros juntos
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        for (int i = 0; i < arrePasaj.length; i++) {
            try {
                arrePasaj[i].join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Se terminaron los pasajeros");
    }
}
