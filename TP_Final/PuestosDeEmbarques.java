package TP_Final;

import java.util.*;
import java.io.*;

/**
 *
 * @author Mariano
 */
public class PuestosDeEmbarques {

    private Map<Integer, Integer> puestosEmbarque; // (clave,valor) el valor va a ser el numero de embarque.

    public PuestosDeEmbarques() {
        this.puestosEmbarque = new TreeMap<Integer, Integer>();
    }

    public void cargarPuestosDeEmbarque() {
        //carga en una estructura de datos los valores del numero de vuelo (clave) 
        //con el puesto de embarque que le corresponde a dicho vuelo (valor).
        String texto;
        String[] textoDividido;

        try {

            BufferedReader buffer = new BufferedReader(new FileReader("D:\\puestosEmbarque"));
            texto = buffer.readLine();

            while (texto != null) {
                textoDividido = texto.split(";");

                this.puestosEmbarque.put(Integer.parseInt(textoDividido[0]), Integer.parseInt(textoDividido[1]));

                texto = buffer.readLine();
            }

            buffer.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int consultarPuestoEmbarque(int numVuelo) {
        //Le pasamos el numero de vuelo y nos retorna el puesto de embarque que le corresponde.       
        int puesto = this.puestosEmbarque.get(numVuelo);

        return puesto;
    }
}
