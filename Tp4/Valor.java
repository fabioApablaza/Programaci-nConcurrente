/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp4;

/**
 *
 * @author user
 */
public class Valor {
    private int valor=3;
    public Valor(){
    }
    public synchronized int getValor(){
        return this.valor;
    }
    public synchronized void setValor(int v){
        this.valor=v;
    }
    public void incrementar(){
        this.valor++;
    }
    public void multiplicar(){
        this.valor*=2;
    }
}
