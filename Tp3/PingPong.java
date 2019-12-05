
package Tp3;


public class PingPong extends Thread{
    private String cadena;
    private int delay;
    public PingPong(String cartel,int cantMs){
        cadena=cartel;
        delay=cantMs;
    }
    public void run(){
        for(int i=1; i<delay*10;i++){
            System.out.println(cadena+" ");
            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){
                
            }
            
        }
    }
}
