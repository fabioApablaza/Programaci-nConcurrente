/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp4.hamsters;

/**
 *
 * @author user
 */
public class Hamster implements Runnable{
    private String nombre;
    private Actividades act;
    public Hamster(String nom, Actividades a){
        this.nombre=nom;
        this.act=a;
    }
    public String getNombre(){
        return this.nombre;
    }
    @Override
    public void run() {
        while(true){
            act.Comer(this);
            act.Rueda(this);
            act.Hamaca(this);            
        }
    }
    
}
