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
public class Hilo implements Runnable{
    private SynchronizedCounter dato;
    public Hilo(SynchronizedCounter dat){
        this.dato=dat;
    }
    @Override
    public void run(){
        while(true){
            dato.decrement();
            System.out.println(dato.value());
        }
    }
}
