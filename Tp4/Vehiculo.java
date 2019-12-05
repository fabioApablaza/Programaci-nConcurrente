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
public class Vehiculo {
    private String patente;
    private String marca;
    private String modelo;
    protected double kmActual;
    protected double kmFaltantesParaElService;
    public Vehiculo(String pat, String mar, String mod, double kmA, double  kmF){
        this.patente=pat;
        this.marca=mar;
        this.modelo=mod;
        this.kmActual=kmA;
        this.kmFaltantesParaElService=kmF;
    }
    public String getPatente(){
        return this.patente;
    }
}
