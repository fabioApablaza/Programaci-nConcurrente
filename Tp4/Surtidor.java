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
public class Surtidor {
    private double nafta=1000;
    public Surtidor(){
        
    }
    public synchronized double getNafta(){
        return this.nafta;
    }
    public synchronized void cargarNafta(double cant){
        this.nafta=nafta-cant;
    }
}
