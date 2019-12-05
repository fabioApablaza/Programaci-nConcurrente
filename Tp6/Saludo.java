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
public class Saludo {
    
    private boolean saludodelJefe = false;// variable que indica que el jefe no saludo todavia    
    private int cont=0;
    public synchronized void llego(){
        this.cont++;        
    }
    public synchronized int getcont(){
        return this.cont;
    }
    public synchronized void esperarJefe(String empleado) {
        
        while (!saludodelJefe) {
            try {
                System.out.println(empleado + " llego pero su jefe no saludo todavia");
                
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
        System.out.println(empleado + "> Buenos dias jefe!");
    }
    
    public synchronized void saludoJefe() {
        
        

        System.out.println("JEFE> Buenos dias!");
        this.saludodelJefe = true;
        notifyAll();
    }

}
