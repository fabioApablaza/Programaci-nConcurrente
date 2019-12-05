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
public class test {

    public static void main(String[] args) {
        //Random aleatorio=new Random();
        //int i=aleatorio.nextInt(2)+1;
        //System.out.println(i);
        SuperMercado mercado = new SuperMercado();
        mercado.cargarGondolas();
        Caja[] cajas = new Caja[10];
        for (int i = 0; i < cajas.length; i++) {
            cajas[i] = new Caja((i + 1));
        }
        DiaNormal unDia = new DiaNormal(mercado);
        Gerente gerente = new Gerente(mercado);
        Distribuidor distri1 = new Distribuidor(mercado, 1, unDia);
        Distribuidor distri2 = new Distribuidor(mercado, 2, unDia);
        Distribuidor distri3 = new Distribuidor(mercado, 3, unDia);        
        Cliente cliente1 = new Cliente("Raul", mercado, cajas, unDia);
        Cliente cliente2 = new Cliente("Roberto", mercado, cajas, unDia);
        Cliente cliente3 = new Cliente("Ernesto", mercado, cajas, unDia);
        Cliente cliente4 = new Cliente("Julia", mercado, cajas, unDia);
        Cliente cliente5 = new Cliente("Laura", mercado, cajas, unDia);
        Thread hilo1 = new Thread(cliente1);
        Thread hilo2 = new Thread(cliente2);
        Thread hilo3 = new Thread(cliente3);
        Thread hilo4 = new Thread(cliente4);
        Thread hilo5 = new Thread(cliente5);
        Thread hilo6 = new Thread(gerente);
        Thread hilo7 = new Thread(unDia);
        Thread hilo8= new Thread(distri1);
        Thread hilo9= new Thread(distri2);
        Thread hilo10= new Thread(distri3);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
        hilo6.start();
        hilo7.start();
        hilo8.start();
        hilo9.start();
        hilo10.start();
    }

}
