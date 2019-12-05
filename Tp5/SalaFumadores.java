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
public class SalaFumadores {
    private int ingrediente=0;
    private boolean fumando= false;
    public synchronized void entrafumar(int id){
        while(ingrediente!=id||fumando){
            try{
            System.out.println("El fumador "+id+" no puede fumar todavia "+this.ingrediente);
            wait();
            }catch(InterruptedException e){}
        }
        System.out.println("El fumador "+id+" se esta haciendo su cigarro");
        this.ingrediente=0;
        fumando=true;
    }
    public synchronized void terminafumar(int id){
        this.fumando=false;
        System.out.println("EL fumador"+id+" termino de fumar");
        notifyAll();
    }
    public synchronized void colocar(int c){
        while(ingrediente!=0||fumando){
            try{
            System.out.println("No se puede dejar ingredientes en la mesa");
            wait();
            }catch(InterruptedException e){}
        }
        System.out.println(this.ingrediente);
        this.ingrediente=c;
        System.out.println(this.ingrediente);
        notifyAll();
    }
}
