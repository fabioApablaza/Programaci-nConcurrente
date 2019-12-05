/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO2;
import java.util.Random;

/**
 *
 * @author user
 */
public class SuperMercado {
    private ListaProducto productos= new ListaProducto();
    private ListaProducto productosConDescuento=new ListaProducto();
    private Random aleatorio=new Random();
     
     
    public void cargarGondolas(){
        int STOCK=10;
        Producto producto;
        String[] nombresProductos = {"Gaseosa", "Yerba", "Azucar", "Aceite", "Leche","Harina","Vino","Yogur","Jabon","Agua Mineral","Helado","Cafe"};
        for(int i=0;i<nombresProductos.length;i++){            
            productos.insertar(new Producto(nombresProductos[i],(aleatorio.nextInt(10)+1)*10,STOCK), productos.longitud()+1);
        }
        for(int i=0;i<3;i++){
            producto =productos.recuperar(aleatorio.nextInt(8)+1);
            producto.setOferta();
            productosConDescuento.insertar(producto, productosConDescuento.longitud()+1);
        }
    }
    public ListaProducto getGondola(){
        return this.productos;
    }
    public ListaProducto getGondolaDescuento(){
        return this.productosConDescuento;
    }
    
    
    
    public synchronized boolean buscarProductoEnOferta(Producto producto){
        boolean existeProducto=false;
        int i=1, longitud=this.productosConDescuento.longitud();
        while(i<=longitud&&!existeProducto){
            if(this.productosConDescuento.recuperar(i)==producto&&this.productosConDescuento.recuperar(i).getHabilitacion()){
                existeProducto=true;
            }
            i++;
        }
        return existeProducto;
    }
    public synchronized Producto buscarProducto(int id){        
        Producto producto=productos.recuperar(id);
        if(producto.getStock()==0){
            producto=null;
        }
        return producto;
    }
    public synchronized void sacarProductoDeLaGondola(Producto producto){           
        
        producto.disminuirStock();
        
    }
}
