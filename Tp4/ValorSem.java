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
public class ValorSem {
    private int valor=3;
    private Semaphore sem=new Semaphore(1,true);
    public ValorSem(){
    }
    public int getValor(){
        return this.valor;
    }
    public void selValor(int v){
        try{
        sem.acquire();
        this.valor=v;
        sem.release();
        }catch(Exception e){}
    }
    public void incrementar(){
        this.valor++;
    }
    public void multiplicar(){
        this.valor*=2;
    }
}
