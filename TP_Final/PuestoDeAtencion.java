/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Final;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Mariano
 */
public class PuestoDeAtencion {

    private String nombre;
    private Semaphore Scantidad; //controla que la cola no sea mayor a 50 pasajeros
    private Semaphore Spasajero; //va dando lugar para que se atienda de a un pasajero.
    private PuestosDeEmbarques puestos;

    public PuestoDeAtencion(String n, PuestosDeEmbarques p) {
        this.nombre = n;
        this.Scantidad = new Semaphore(50);
        this.Spasajero = new Semaphore(1);
        this.puestos = p;
    }

    public void ingresaPasajero(Pasajero unPasajero, int pos) {

        int num;
        try {
            //controlCantidad simula la capacidad de la sala del puesto de atencion
            this.Scantidad.acquire();

            System.out.println("Pasajero " + unPasajero.getIdentificador() + " ingresando a la cola del puesto de atencion de la aerolinea " + this.nombre);

            //controla que se atienda de a 1 pasajero 
            this.Spasajero.acquire();

            System.out.println("Atendiendo al pasajero " + unPasajero.getIdentificador() + " en el puesto de " + this.nombre);

            this.Scantidad.release();
            Thread.sleep(200);

            //segun la aerolinea se designa la terminal a la que va a salir 
            switch(pos){
                case 0:
                    unPasajero.getPasaje().setTerminal('A');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal A, Puesto de embarque " + num);
                break;
                case 1:
                    unPasajero.getPasaje().setTerminal('A');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal A, Puesto de embarque " + num);
                break;
                case 2:
                    unPasajero.getPasaje().setTerminal('B');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal B, Puesto de embarque " + num);
                break;
                case 3:
                    unPasajero.getPasaje().setTerminal('B');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal B, Puesto de embarque " + num);
                break;
                case 4:
                    unPasajero.getPasaje().setTerminal('C');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal C, Puesto de embarque " + num);
                break;
                case 5:
                    unPasajero.getPasaje().setTerminal('C');
                    num = this.puestos.consultarPuestoEmbarque(Integer.parseInt(unPasajero.getPasaje().getNumVuelo()));
                    unPasajero.getPasaje().setPuestoEmbarque(num);
                    System.out.println("Pasajero " + unPasajero.getIdentificador() + " con destino " + unPasajero.getPasaje().getDestino() + " con la hora " + unPasajero.getPasaje().getHorario() + " Derivado a la terminal C, Puesto de embarque " + num);
                break;                     
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            this.Spasajero.release();
        }
    }

    public String getNombrePuestoAtencion() {
        return this.nombre;
    }
}
