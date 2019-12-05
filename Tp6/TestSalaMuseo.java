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
public class TestSalaMuseo {
    public static void main(String[]args){
        GestorSala sala= new GestorSala();
        Persona[] per=new Persona[100];
        per[0] =new Persona(sala,true);
        per[1]=new Persona(sala,true);
        for(int i=2;i<100;i++){
             per[i]= new Persona(sala);
             
        }
        for(int i=0;i<100;i++){
            
        }
        
    }
}
