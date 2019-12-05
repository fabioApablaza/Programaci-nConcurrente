/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp5;

/**
 *
 * @author user
 */
public class SyncObject {

    public static void main(String[] args) {
       final DualSynch ds = new DualSynch();
// solo por cuestiones prácticas se trabaja de esta forma
        Thread hilo = new Thread() {
            @Override
            public void run() {
                ds.f();
            }
        };
        hilo.start();
        ds.g();
    }

}
