
package Tp3;

public class Cliente {

    private String nombre;
    private int[] carroCompra;
public Cliente(){
    this.nombre="Nombre predeterminado";
}
public Cliente(String name, int[]obj){
    this.nombre=name;
    this.carroCompra=obj;
}
public String getNombre(){
    return this.nombre;
}
public void setNombre(String name){
    this.nombre=name;
}
public int[] getCarroCompra(){
    return this.carroCompra;
}
public void setCarroCompra(int[] obj){
    this.carroCompra=obj;
}
}
