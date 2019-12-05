/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp4.hamsters;



/**
 *
 * @author user
 */
public class Actividades {
    public synchronized void Comer(Hamster unHam){
        System.out.println(unHam.getNombre()+" esta comiendo del plato.");
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            
        }
        System.out.println(unHam.getNombre()+" dejo de comer del plato.");
        System.out.println("");
    }
    public synchronized void Hamaca(Hamster unHam){
        System.out.println(unHam.getNombre()+" esta descansando en la hamaca.");
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            
        }
        System.out.println(unHam.getNombre()+" salio de la Hamaca.");
        System.out.println("");
    }
    public synchronized void Rueda(Hamster unHam){
        System.out.println(unHam.getNombre()+" esta corriendo en la rueda.");
        try {
            Thread.sleep(1000);
        } catch (Exception ex) { }
        System.out.println(unHam.getNombre()+" dejo de correr en la hamaca.");
        System.out.println("");
    }
}
