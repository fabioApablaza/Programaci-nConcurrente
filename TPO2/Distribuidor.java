/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO2;

import java.util.Random;

/**
 *
 * @author user
 */
public class Distribuidor implements Runnable {

    private int id;
    private SuperMercado mercado;
    private ListaProducto carga;
    private Random aleatorio;
    private DiaNormal Dia;

    public Distribuidor(SuperMercado merc, int ident, DiaNormal unDia) {
        this.mercado = merc;
        this.id = ident;
        this.Dia = unDia;
        this.aleatorio= new Random();
        this.carga=new ListaProducto();
    }

    public void generarCarga() {
        int STOCK = 10;
        String[] nombresProductos = {"Gaseosa", "Yerba", "Azucar", "Aceite", "Leche", "Harina", "Vino", "Yogur", "Jabon", "Agua Mineral", "Helado", "Cafe"};
        for (int i = 0; i < 4; i++) {
            carga.insertar(new Producto(nombresProductos[aleatorio.nextInt(11)], (aleatorio.nextInt(10) + 1) * 10, STOCK), carga.longitud() + 1);
        }
    }

    public void entregaDeProductos() {
        for (int i = 1; i <= carga.longitud(); i++) {
            for (int j = 1; j <= mercado.getGondola().longitud(); j++) {
                if (carga.recuperar(i).getNombreProducto().equalsIgnoreCase(mercado.getGondola().recuperar(j).getNombreProducto())) {
                    mercado.getGondola().recuperar(j).aumentarStock(10);
                }
            }
        }
    }

    @Override
    public void run() {
        this.generarCarga();
        while (true) {
            try {
            if (this.Dia.getDistribuidores()) {
                    
                    this.entregaDeProductos();
                    Thread.sleep(10000);
                
            } else {
                System.out.println("EL distribuidor no pudo entrar al local");
                Thread.sleep(10000);
            }
            } catch (InterruptedException ex) {
                    System.out.println("Ocurrio un error inesperado");
                }

        }

    }
}
