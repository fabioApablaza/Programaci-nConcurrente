
package Tp3;


public class test {
    public static void main(String[]args){
        PingPong t1= new PingPong("Ping",33);
        PingPong t2= new PingPong("Pong",10);
        t1.start();
        t2.start();
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            
        }
        
    }
}
