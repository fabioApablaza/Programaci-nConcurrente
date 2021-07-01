/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

/**
 *
 * @author Fabio Apablaza
 */
public class GestorCinta implements Runnable{
    /*El gestor de cinta representa la cinta que mueve a las aerosillas que
    llevan a los esquiadores a la cima de la montaña.
    
    
    */
    private MedioElevacion unMedio;
    
    public GestorCinta(MedioElevacion unM){
        this.unMedio = unM;
    }
    
    @Override
    public void run() {
    
        try{
        while(true){
            
            unMedio.inicioGestor();//Se verifica el horario de trabajo
            
            unMedio.moverCinta();
            
            Thread.sleep(500);//Se simula el movimiento de la cinta
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
