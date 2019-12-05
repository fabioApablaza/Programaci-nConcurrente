/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp6;

/**
 *
 * @author user
 */
public class GestorSala {
    private int cantMaxpersonas=50;
    private int cantLimiteMaxTemp=35;
    private int cantLimiteMinTemp=35;
    private int cantPersonas=0;
    private int cantJuv=0;
    public synchronized void entrarSala(){
        while(cantPersonas>cantMaxpersonas&&cantJuv>0){
            try{            
            wait();
            }catch(InterruptedException e){}
        }        
        this.cantPersonas++;
    }
    public synchronized void salirSala(){        
        this.cantPersonas--;
        System.out.println(this.cantPersonas);
        notifyAll();
    }
    public synchronized void entrarSalaJuvilado(){
        cantJuv++;
        while(cantPersonas>cantMaxpersonas){
            try{
                wait();
            }catch(InterruptedException e){}
        }
    }
    public synchronized void notificarTemperatura(int temperatura){
        if(temperatura>30){
            this.cantMaxpersonas=this.cantLimiteMaxTemp;
        }
        else
            this.cantMaxpersonas=50;
    }
}
