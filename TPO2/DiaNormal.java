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
public class DiaNormal implements Runnable{
    private boolean abierto=false;
    private boolean distribuidores=false;
    private boolean gerente=false;
    private boolean horaPico=false;
    private SuperMercado mercado;
    public DiaNormal(SuperMercado merc){
        this.mercado=merc;
    }
    public boolean getAbierto(){
        return this.abierto;
    }
    public boolean getDistribuidores(){
        return this.distribuidores;
    }
    public boolean getGerente(){
        return this.gerente;
    }
    public boolean getHoraPico(){
        return this.horaPico;
    }
    public void aumentarDiasDeProductos(){
        for(int i=1;i<=mercado.getGondola().longitud();i++){
            mercado.getGondola().recuperar(i).aumentarDias();
        }
    }
    @Override
    public void run(){
        while(true){
            try{
                this.abierto=true;
                System.out.println("Se abre el supermercado");
                this.distribuidores=true;
                Thread.sleep(5000);
                System.out.println("Se llama a los distribuidores");
                Thread.sleep(10000);
                this.horaPico=true;
                System.out.println("Comienza la hora pico");
                Thread.sleep(12000);
                this.horaPico=false;
                System.out.println("Termina la hora pico");
                Thread.sleep(15000);
                this.distribuidores=false;
                System.out.println("No entran mas distribuidores");
                this.abierto=false;
                System.out.println("Se cierra el supermercado");
                Thread.sleep(10000);                
                this.aumentarDiasDeProductos();
                
            }catch(InterruptedException e){
                System.out.println("Error inesperado");
            }
        }
    }
    
}
