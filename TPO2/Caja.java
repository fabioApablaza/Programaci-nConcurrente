/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO2;

import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */
public class Caja {

    private int idCajera;
    Semaphore s;
    public Caja(int id) {
        this.idCajera = id;
        s=new Semaphore(1,true);
    }

    public synchronized void escanearProductos(Cliente client) {//Modulo encargado de verificar los productos del cliente 
        int i = 1;
        double montoAPagar = 0;
        Producto producto;
        ListaProducto carro = client.getCarro();
        try{
            s.acquire();
        System.out.println("La cajera de la caja N° "+ (this.idCajera+1)+ " esta procesando la compra de " + client.getNombre());
        while (carro.recuperar(i)!=null) {
            producto = carro.recuperar(i);
            System.out.println("La cajera " + this.idCajera + " esta escaneando el producto " + producto.getNombreProducto());
            if (producto.getOferta()) {
                System.out.println("El producto " + producto.getNombreProducto() + " tiene un descuento del 10%");
                montoAPagar = montoAPagar + (producto.getPrecio() - producto.getPrecio() * 1 / 10);
            } else {
                montoAPagar += producto.getPrecio();
            }
            carro.eliminar(i);
        }
        System.out.println("El monto a pagar del cliente "+client.getNombre()+" es de $"+montoAPagar);
        s.release();
        }catch(InterruptedException e){
            System.out.println("Ocurrio un error inesperado");
        }
    }
}
