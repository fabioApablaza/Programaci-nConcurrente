
package Tp3;


public class Cajera{

    private String nombre;
    public Cajera(){
    this.nombre="Nombre predeterminado";
}
    public Cajera(String name){
        this.nombre=name;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String name){
        this.nombre=name;
    }
    public void esperarXsegundos(int n){
        Thread t=new Thread();
        try{
            t.sleep(n*1000);
        }catch(Exception e){}
    }
    public void procesarCompra(Cliente cliente, long timeStamp) {
        System.out.println("La cajera " + this.nombre + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + cliente.getNombre() + " EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000 + " seg");
        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) + " ->Tiempo: "
                    + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        }
        System.out.println("La cajera " + this.nombre
                + " HA TERMINADO DE PROCESAR " + cliente.getNombre() + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - timeStamp) / 1000
                + "seg");
    }

}
