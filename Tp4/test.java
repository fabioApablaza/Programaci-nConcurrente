package Tp4;

public class test {

    public static void main(String[] args) {
        Turnon unTurno = new Turnon();
        LetraB a = new LetraB('A', unTurno,0);
        LetraB b = new LetraB('B', unTurno,1);
        LetraB c = new LetraB('C', unTurno,2);
        Thread hilo1 = new Thread(a);
        Thread hilo2 = new Thread(b);
        Thread hilo3 = new Thread(c);
        
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
