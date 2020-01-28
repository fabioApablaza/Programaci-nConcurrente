/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;
/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class TestEsqui {
    public static void main(String[]args){
        Confiteria unaConfiteria= new Confiteria();        
        CentroEsqui unCentro= new CentroEsqui(unaConfiteria);
        Thread[] hilos=new Thread[16];
        Instructor[] instructores= new Instructor[5];
        Esquiador[] esquiadores=new Esquiador[10];
        crearInstructores(instructores,unCentro);
        crearEsquiadores(esquiadores,unCentro);        
        Tiempo unT=new Tiempo(unCentro);
        crearHilos(hilos,esquiadores,instructores,unT);
        for(int i=0;i<hilos.length;i++){
            hilos[i].start();
        }
    }
    public static void crearInstructores(Instructor[] instructores,CentroEsqui unCentro){
        String[] nombres={"Carlos","Felipe","Ana","Isabel","Maria"};        
        for(int i=0;i<instructores.length;i++){
            instructores[i]=new Instructor(nombres[i],unCentro,i);
        }
    }
    public static void crearEsquiadores(Esquiador[] esquiadores,CentroEsqui unCentro){
        for(int i=0;i<esquiadores.length;i++){
            esquiadores[i]=new Esquiador((i+1),unCentro,true);
        }
    }
    public static void crearHilos(Thread[] hilos,Esquiador[] esquiadores,Instructor[] instructores,Tiempo unT){
        hilos[0]=new Thread(unT);
        for(int i=1;i<esquiadores.length+1;i++){
            hilos[i]=new Thread(esquiadores[i-1]);
        }
        for(int i=11;i<hilos.length;i++){
            hilos[i]=new Thread(instructores[(i-11)]);
        }
    }
}
