/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author user
 */
public class SalaFumadoresLock {
    private int ingrediente=0;
    private Lock unLock= new ReentrantLock(true);
    private Condition estalleno= unLock.newCondition();    
    private Condition colocar= unLock.newCondition();
    private boolean fumando= false;
    public void entrafumar(int id){
        unLock.lock();
        try{
        while(ingrediente!=id||fumando){
            try{
            System.out.println("El fumador "+id+" no puede fumar todavia ");
            estalleno.await();
            }catch(InterruptedException e){}
        }
        System.out.println("El fumador "+id+" se esta haciendo su cigarro");
        this.ingrediente=0;
        fumando=true;
        }
        finally{
        unLock.unlock();
        }
    }
    public void terminafumar(int id){
        unLock.lock();
        try{
        this.fumando=false;
        System.out.println("EL fumador "+id+" termino de fumar");
        colocar.signal();
        }finally{
            unLock.unlock();
        }
    }
    public void colocar(int c){
        unLock.lock();
        try{
        while(ingrediente!=0||fumando){
            try{
            System.out.println("No se puede dejar ingredientes en la mesa");
            colocar.await();
            }catch(InterruptedException e){}
        }
        
        this.ingrediente=c;
        System.out.println("El agente acaba de dejar el ingrediente para el fumador "+c);
        estalleno.signalAll();
        }finally{
            unLock.unlock();
        }
        
    }
}
