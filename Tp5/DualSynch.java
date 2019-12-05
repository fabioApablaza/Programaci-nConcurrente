/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp5;

/**
 *
 * @author user
 */
public class DualSynch {

    private Object syncObject = new Object();
    int dato=5;
    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            dato=dato*4;
            Thread.yield();
            System.out.println("f()"+dato);
            
        }
    }

    public void g(){
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                dato=dato+20;
                System.out.println("g()"+dato);
                if(i==3){
                    try{
                    this.wait();
                    }catch(Exception e){}
                }
            }
        }
    }

    
}
