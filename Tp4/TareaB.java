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
public class TareaB implements Runnable{
    private Valor1 unValor;
    public TareaB(Valor1 v){
        this.unValor=v;
    }
    @Override
    public void run(){
        int valor= unValor.getValor();
        
        valor*=2;
        unValor.setValor(valor);
    }
}
