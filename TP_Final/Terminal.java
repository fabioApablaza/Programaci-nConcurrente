package TP_Final;

/**
 *
 * @author Mariano
 */
public class Terminal {

    private shopping shop;
    private char letra;
    private Hora reloj;

    public Terminal(char l, Hora r) {
        this.shop = new shopping(l);
        this.letra = l;
        this.reloj = r;
    }

    public void ingresarTerminal(Pasajero unPasajero) {

        System.out.println("Pasajero " + unPasajero.getIdentificador() + " ingresando a la terminal " + this.letra);

        // Primero verificamos si el pasajero tiene el tiempo suficiente para que pueda pasar por el shopping, en caso
        // contrario se va a esperar su vuelo para no perderlo.

        if (Integer.parseInt(unPasajero.getPasaje().getHorario()) - this.reloj.getHora() > 1) { 
            this.shop.ingresarAFreeShop(unPasajero);
        }else{ 
            System.out.println("El pasajero "+unPasajero.getIdentificador()+" no entra al shopping");
        }

        System.out.println("Pasajero " + unPasajero.getIdentificador() + " esperando su vuelo en la terminal " + this.letra + " puesto de embarque: " + unPasajero.getPasaje().getPuestoEmbarque());

        synchronized (this.reloj) {
            while (Integer.parseInt(unPasajero.getPasaje().getHorario()) != this.reloj.getHora()) {
                try {
                    this.reloj.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("El PASAJERO " + unPasajero.getIdentificador() + " HA TOMADO SU VUELO CON EXITO.");
    }
}
