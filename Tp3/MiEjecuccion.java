
package Tp3;


public class MiEjecuccion implements Runnable {
    @Override
    public void run(){
    ir();
}
    public void ir(){
        hacerMas();
    }
    public void hacerMas(){
        System.out.println("En la pila");
    }
}
