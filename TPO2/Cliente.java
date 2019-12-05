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
public class Cliente implements Runnable {

    private String nombre;
    private ListaProducto carro;//Carrito de compras del Cliente
    private Random r = new Random();
    ;
    private SuperMercado mercado;//Recurso Compartido
    private Caja[] cajas;
    private DiaNormal unDia;

    public Cliente(String nom, SuperMercado merc, Caja[] caj, DiaNormal dia) {//Constructor de Cliente
        this.nombre = nom;
        this.mercado = merc;
        this.cajas = caj;
        this.unDia = dia;
        this.carro = new ListaProducto();

    }

    public String getNombre() {
        return this.nombre;
    }

    public ListaProducto getCarro() {
        return this.carro;
    }

    public void BuscarProductos() {
        boolean existeProducto;
        Producto producto;
        int cantidadDeProductos = r.nextInt(5) + 1, idProducto, opcion;
        for (int i = 0; i < cantidadDeProductos; i++) {
            try {
                idProducto = r.nextInt(5) + 1;
                opcion = r.nextInt(2) + 1;
                if (opcion == 1) {//EL cliente decidio comrpar el producto en oferta

                    producto = mercado.buscarProducto(idProducto);
                    existeProducto = mercado.buscarProductoEnOferta(producto);
                    if (!existeProducto) {
                        producto = null;
                    }
                    Thread.sleep(1000);
                } else {//El cliente decidio comprar el producto sin la oferta
                    producto = mercado.buscarProducto(idProducto);
                    Thread.sleep(1000);
                }
                this.añadirProductosAlCarro(producto);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void añadirProductosAlCarro(Producto producto) {
        if (producto != null) {
            carro.insertar(producto, carro.longitud() + 1);
            mercado.sacarProductoDeLaGondola(producto);
            System.out.println(this.nombre + " puso en su carrito de compras " + producto.getNombreProducto());
        }

    }

    public void aLaCaja() {
        Caja caja;
        if (this.unDia.getHoraPico()) {
            caja = cajas[r.nextInt(9)];
            caja.escanearProductos(this);
        }
        else{
            caja = cajas[r.nextInt(3)];
            caja.escanearProductos(this);
        }

    }

    @Override
    public void run() {

        while (true) {
            try {
                if (this.unDia.getAbierto()) {
                    this.BuscarProductos();
                    System.out.println(this.nombre + " a finalizado su compra, se esta dirigiendo a la caja");
                    Thread.sleep(5000);
                    this.aLaCaja();
                } else {
                    Thread.sleep(5000);
                }

            } catch (InterruptedException ex) {
                System.out.println("Ocurrio un error inesperado");
            }
        }

    }

}
