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
public class TareaA implements Runnable {

    private Valor1 unValor;

    public TareaA(Valor1 v) {
        this.unValor = v;
    }

    @Override
    public void run() {
        int valor = unValor.getValor();
        
            valor++;
            unValor.setValor(valor);
            System.out.println(unValor.getValor());
        

    }
}
