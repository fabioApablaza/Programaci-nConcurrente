/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp4;

import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */
public class Turnon {
    private int id;
    private Semaphore s= new Semaphore(1,true);
    public Turnon(){
        this.id=0;
    }
    public void continuarTurno(){
        this.id=(id+1)%3;
    }
    public boolean esTurno(int d)throws Exception{
        boolean exito=false;
        while(!exito){
            System.out.print(id);
        s.acquire();
        if(id==d){
            exito=true;
            this.continuarTurno();
        }
        s.release();}
        return exito;
    }
}
