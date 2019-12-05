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
public class Auto extends Vehiculo implements Runnable {

    private Surtidor sur;
    private double tanque = 100;
    public boolean service = false;

    public Auto(String pat, String mar, String mod, double kmA, double kmF, Surtidor s) {
        super(pat, mar, mod, kmA, kmF);
        this.sur = s;
    }

    public void desplazarse(double km) {
        this.kmActual += km;
    }

    public void disminuirTanque(double cant) {
        if (this.tanque >= cant) {
            tanque -= cant;
        } else {
            tanque = 0;
        }
    }

    public synchronized void recorrer(double km) {
        this.desplazarse(km);
        this.disminuirTanque(km);
        if (!service) {
            if (this.kmActual >= this.kmFaltantesParaElService) {
                System.out.println(this.getPatente() + " necesita ir al service");
                service = true;
            }
            if (this.tanque == 0) {
                System.out.println(this.getPatente()+ " Tiene el tanque vacio");
                service = true;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
               
                if (sur.getNafta() >= 100) {
                    sur.cargarNafta(100);
                    this.tanque=100;
                }
                System.out.println("El surtidor tiene: "+sur.getNafta());
            }
        }
        else{
            System.out.println(this.getPatente()+" Esta en el service");
            try{
            Thread.sleep(2000);
            }catch(Exception e){}
            
            System.out.println(this.getPatente()+"se le hizo el service");
            this.service=false;
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            recorrer(10);
        }
    }
}
