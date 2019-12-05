/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp6;

import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */
public class SaludoSemaforo {

    private Semaphore guardia= new Semaphore(1,true);
    private Semaphore guardia1=new Semaphore(0);
    private int cont=0;
    public void llego(){
        try{
        guardia.acquire();
        this.cont++;
        guardia.release();
        }catch(InterruptedException e){}
    }
    public int getcont(){
        
        return cont;
    }
    public void esperarJefe(String empleado) {
        
        try {
            
            guardia1.acquire();
                      
            System.out.println(empleado + "> Buenos dias jefe!");
            guardia1.release();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    public void saludoJefe() {
        guardia1.release();
        System.out.println("JEFE> Buenos dias!");
    }

}
