/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tp5;
import java.util.Random;
/**
 *
 * @author user
 */
public class Agente implements Runnable{
    private SalaFumadoresLock sala;
    private Random r;
    public Agente(SalaFumadoresLock sala){
        this.sala = sala;
        r= new Random();
    }
 public void run (){
     while(true){
         sala.colocar(r.nextInt(3)+1);
     } } 
}
