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
public class Fumador implements Runnable{
    private int id;
    private SalaFumadoresLock sala;
    public Fumador(int id, SalaFumadoresLock sala){
        this.id = id;
        this.sala = sala; 
    } 
    @Override
    public void run(){
        while(true){
            try {
                sala.entrafumar(id);
                System.out.println("Fumador "+id+" está fumando.");
                Thread.sleep(2000);
                sala.terminafumar(id);
            } catch (InterruptedException e) { }
        }
    }
}
