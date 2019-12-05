/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO2;

/**
 *
 * @author user
 */
public class Producto {

    private String nombreProducto;
    private boolean oferta = false;
    private boolean habilitado=true;
    private double precio;    
    private int stock;
    private int dia=1;

    public Producto(String nombre, double prec, int st) {
        this.nombreProducto = nombre;
        this.precio = prec;
        this.stock = st;
    }
    public int getDia(){
        return this.dia;
    }
    public void aumentarDias(){
        if(this.dia==7){
            this.dia=1;
        }
        else{
            this.dia++;
        }
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int st) {
        this.stock = st;
    }

    public void aumentarStock(int st) {
        this.stock += st;
    }

    public void disminuirStock() {
        if (this.stock > 0) {
            this.stock--;
        }
    }

    public String getNombreProducto() {
        return this.nombreProducto;
    }

    public void setNombreProducto(String nombreProd) {
        this.nombreProducto = nombreProd;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double prec) {
        this.precio = prec;
    }
    public boolean getHabilitacion(){
        return this.habilitado;
    }
    public void setHabilitado(){
        if(this.habilitado==true){
            this.habilitado=false;
        }
        else
            this.habilitado=true;
    }

    public boolean getOferta() {
        return this.oferta;
    }

    public void setOferta() {
        if(this.oferta==true){
            this.oferta=false;
        }
        else{
            this.oferta=true;
        }
    }

    @Override
    public String toString() {
        return "Nombre de producto: " + this.nombreProducto + " Oferta: " + this.oferta + " Precio: $" + this.precio+" Habilitado: "+this.habilitado+" Dia:"+this.dia +"Stock: "+this.stock;
    }
}
