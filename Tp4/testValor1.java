/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp4;

/**
 *
 * @author user
 */
public class testValor1 {
    public static void main(String[] args) {
        Valor1 v = new Valor1();
        TareaA a = new TareaA(v);
        TareaB b = new TareaB(v);
        Thread hilo1 = new Thread(a);
        Thread hilo2 = new Thread(b);
        hilo1.start();
        hilo2.start();
        try {
            hilo1.join();
            hilo2.join();
        } catch (Exception e) {
        }

        
    }
}
