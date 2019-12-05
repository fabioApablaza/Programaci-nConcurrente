/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Tp4.Auto;
import Tp4.Surtidor;


/**
 *
 * @author user
 */
public class TestAuto {

    
    public static void main(String[] args) {
        Surtidor s = new Surtidor();
        Auto auto1 = new Auto("ABC123", "Volz", "1990", 0, 1000, s);
        Auto auto2 = new Auto("DEF456", "Volz", "1990", 0, 1000, s);
        Auto auto3 = new Auto("GHI789", "Volz", "1990", 0, 1000, s);
        Auto auto4 = new Auto("JKL321", "Volz", "1990", 0, 1000, s);
        Auto auto5 = new Auto("MNÑ654", "Volz", "1990", 0, 1000, s);
        Thread hilo1 = new Thread(auto1);
        Thread hilo2 = new Thread(auto2);
        Thread hilo3 = new Thread(auto3);
        Thread hilo4 = new Thread(auto4);
        Thread hilo5 = new Thread(auto5);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
        } catch (Exception ex) {
            ;
        }
        System.out.println(s.getNafta());
    }

}
