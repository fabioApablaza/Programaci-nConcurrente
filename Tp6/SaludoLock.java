/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author user
 */
public class SaludoLock {

    private Lock s = new ReentrantLock(true);
    private Condition saludo = s.newCondition();
    private int cont = 0;

    public void llego() {
        s.lock();
        try {
            this.cont++;
        } finally {
            s.unlock();
        }
    }

    public int getcont() {
        return this.cont;
    }

    public void esperarJefe(String empleado) {
        
        try {
            s.lock();
            saludo.await();
            System.out.println(empleado + "> Buenos dias jefe!");
        } catch (InterruptedException e) {
        } finally {
            s.unlock();
            
            saludo.signal();
        }
    }

    public void saludoJefe() {
        s.lock();
        try {
            System.out.println("JEFE> Buenos dias!");

            saludo.signal();
        } finally {
            s.unlock();
        }
    }
}
