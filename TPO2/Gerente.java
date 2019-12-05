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
public class Gerente implements Runnable {

    private SuperMercado mercado;
    private Random aleatorio = new Random();

    public Gerente(SuperMercado merc) {
        this.mercado = merc;
    }

    public void cambiarProductos() {//modulo  en el que el gerente cambia un producto que esta en oferta por otro que no lo esta
        int i = aleatorio.nextInt(2) + 1, idProducto = aleatorio.nextInt(10) + 1;
        Producto producto1;
        if (i == 1) {//Si el numero es 1, entonces el gerente decide cambiar un producto por otro
            producto1 = mercado.getGondola().recuperar(idProducto);
            System.out.println("El gerente esta trabajando");
            while (producto1.getOferta()) {
                idProducto = aleatorio.nextInt(10) + 1;
                producto1 = mercado.getGondola().recuperar(idProducto);

            }
            mercado.getGondolaDescuento().eliminar(idProducto);
            producto1.setOferta();

            idProducto = aleatorio.nextInt(10) + 1;
            while (!producto1.getOferta()) {
                idProducto = aleatorio.nextInt(10) + 1;
                producto1 = mercado.getGondola().recuperar(idProducto);
            }
            mercado.getGondolaDescuento().insertar(producto1, idProducto);
            producto1.setOferta();
        }
    }

    public void gestionDeStock() {
        int cont = 1;
        ListaProducto productos = mercado.getGondola();
        System.out.println("EL gerente esta revisando el stock de cada producto");
        while (cont <= productos.longitud()) {
            if ((productos.recuperar(cont).getStock() < 2 || productos.recuperar(cont).getDia() == 7) && productos.recuperar(cont).getHabilitacion()) {
                productos.recuperar(cont).setHabilitado();
            }
            cont++;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.cambiarProductos();
                Thread.sleep(4000);
                this.gestionDeStock();
                Thread.sleep(4000);
                
            } catch (InterruptedException e) {
                System.out.println("ocurrio un error");
            }
        }
    }
}
