/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;
import Tp4.hamsters.Actividades;
import Tp4.hamsters.Hamster;
/**
 *
 * @author user
 */
public class TestHamster {
    public static void main(String[]args){
        Actividades act=new Actividades();
        Hamster ham1=new Hamster("Eduardo",act);
        Hamster ham2=new Hamster("Roberto",act);
        Hamster ham3=new Hamster("Ricardo",act);
        Thread hilo1=new Thread(ham1);
        Thread hilo2=new Thread(ham2);
        Thread hilo3=new Thread(ham3);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        try{
            hilo1.join();
            hilo2.join();
            hilo3.join();
        }catch(Exception e){}
        
    }
}
