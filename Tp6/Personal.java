
package Tp6;


public class Personal extends Thread {

    private String nombre;
    private SaludoLock saludo;
    private boolean esJefe;
    private int numEmp;

    Personal(SaludoLock s, String n) {
        esJefe = false;
        nombre = n;
        saludo = s;
    }

    Personal(SaludoLock s, String n, int x) {
        esJefe = true;
        nombre = n;
        saludo = s;
        numEmp = x;
    }

    @Override
    public void run() {

        if (esJefe) {
            
            while (saludo.getcont()<numEmp) {
                try {
                    System.out.println("(Esperando....)");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            saludo.saludoJefe();

        } else {
            try{
            saludo.llego();
            Thread.sleep(1000);
            saludo.esperarJefe(nombre);
            }catch(InterruptedException e){}

        }
    }

}
