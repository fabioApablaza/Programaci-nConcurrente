package Tp3;

public class CajeraThread implements Runnable {

    private String nombre;
    private Cliente cliente;
    private double venta;
    private long initialTime;

    public CajeraThread() {
        this.nombre = "Nombre Predeterminado";
        this.cliente = new Cliente();
    }

    public CajeraThread(String name, Cliente unCLiente, long ini) {
        this.nombre = name;
        this.cliente = unCLiente;
        this.initialTime = ini;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente unCliente) {
        this.cliente = unCliente;
    }

    public long getInitialTime() {
        return this.initialTime;
    }

    public void setInitialTime(long ini) {
        this.initialTime = ini;
    }
    public double getVenta(){
        return this.venta;
    }
    public void sumarVenta(double v){
        this.venta+=v;
    }
    public void esperarXsegundos(int n){
        Thread t= new Thread();
        try{
            t.sleep(n*1000);
        }catch(Exception e){}
    }
@Override
    public void run() {
        System.out.println("La cajera " + this.nombre+ " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "+ this.cliente.getNombre() + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) + " del cliente "+ this.cliente.getNombre() + "->Tiempo: "+ (System.currentTimeMillis() - this.initialTime)/1000+"seg");
            System.out.println("El producto "+(i+1)+" cuesta "+(cliente.getCarroCompra()[i]*10)+" pesos");
            this.sumarVenta((cliente.getCarroCompra()[i]*10));
        }
        System.out.println("La cajera " + this.nombre + " HA TERMINADO DE PROCESAR " + this.cliente.getNombre()+" EN EL TIEMPO: "+(System.currentTimeMillis()-this.initialTime)/1000+" seg");
        System.out.println("Se suma a la venta el 1% de la venta total");
        this.sumarVenta(this.venta/100);
        System.out.println("La venta total fue de "+this.getVenta()+" pesos");
    }
               
    


}
