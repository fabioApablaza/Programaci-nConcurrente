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
public class Valor1 {
    private int valor=3;
    
    public Valor1(){
    }
    public int getValor(){
        return this.valor;
    }
    public void setValor(int v){
        this.valor=v;
    }
    public void incrementar(){
        this.valor++;
    }
    public void multiplicar(){
        this.valor*=2;
    }
}
