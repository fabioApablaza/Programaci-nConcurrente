package TP_Final;


import java.util.*;
import java.io.*;

/**
 *
 * @author Mariano
 */
public class Vuelo {

    private Map<Integer, String[]> vuelos; // <clave,dato> la clave corresponde a un horario y el dato a un arreglo donde van los vuelos
    private Hora reloj;

    public Vuelo(Hora r) {

        this.vuelos = new TreeMap<Integer, String[]>(); // treeMap los ordena de menor a mayor.
        this.reloj = r;
    }

    public void cargarVuelos() {
        //carga todos los vuelos de todas las aerolineas en una estructura de datos similar a la de un TDA Diccionario.

        String texto;       
        int hora = 8;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("D:\\Vuelos1.txt"));
            texto = buffer.readLine();

            while (texto != null) {
                this.vuelos.put(hora, texto.split("-"));
                hora++;
                texto = buffer.readLine();
            }

            buffer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String[] calcularVuelo() {
        // Metodo utilizado para devolver a traves de un aleatorio un vuelo que va a depender de la hora que es.
        int hora, hora2, pos;
        hora = this.reloj.getHora() + 2; //le sumo 2 hs para que tengan tiempo de desplazarse por el aeropuerto.
        Random aleatorio = new Random();
        String[] vuelosHora;
        String[] pasajeVuelos; 

        if (hora == 24) {
            hora2 = 24;
        } else {
            hora2 = aleatorio.nextInt(24 - hora) + hora;
        }

        vuelosHora = this.vuelos.get(hora2);

        pos = aleatorio.nextInt(vuelosHora.length);

        pasajeVuelos = dividirCadenaPasaje(vuelosHora[pos]);

        return pasajeVuelos;
    }

    public ArrayList<String[]> getVuelos(int hora) {
        //Este metodo es invocado por Mensaje y devuelve todos los vuelos correspondientes a la hora ingresada por parametro.
        ArrayList<String[]> ListaVuelo = new ArrayList<String[]>();
        String[] vuelosPorHora = this.vuelos.get(hora);

        for (int i = 0; i < vuelosPorHora.length; i++) {
            //almacena en cada posicion un String[] con todos los campos correspondientes a un pasaje.
            ListaVuelo.add(i, dividirCadenaPasaje(vuelosPorHora[i]));
        }

        return ListaVuelo;
    }

    public String[] dividirCadenaPasaje(String cadena) {
        // Este metodo en cada posicion guarda un campo especifico del pasaje (destino,pais,aereolineas,numeroVuelo,hora)
        String[] cadenaDividida = new String[5];
        for (int i = 0; i < cadenaDividida.length; i++) {
            cadenaDividida[i] = cadena.split(";")[i]; 
        }
        return cadenaDividida;
    }
}
