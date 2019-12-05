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
public class LetraB implements Runnable{
    private Turnon turno;
    private char letra;
    public int pase;
    public LetraB(char letra,Turnon n,int pase ){
        this.letra=letra;
        this.turno=n;
        this.pase=pase;
    }
    @Override
    public void run(){
        try {
            if(turno.esTurno(pase)){
                System.out.print(this.letra);}
        } catch (Exception ex) {
            
        }
    }
}
