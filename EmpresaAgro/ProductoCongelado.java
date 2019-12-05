
package EmpresaAgro;


public class ProductoCongelado extends Productofresco{
    private int temperatura;
    public ProductoCongelado(){
        super();
        temperatura=0;
    }
    public ProductoCongelado(int den,String fechaC,int nroL,String fechaEnv, String paisOr,int temp){
        super(den,fechaC,nroL,fechaEnv,paisOr);
        temperatura=temp;
    }
    public int getTemperatura(){
        return temperatura;
    }
    public void setTemperatura(int temp){
        temperatura=temp;
    }
    @Override
    public String toString(){
        return super.toString()+" Temperatura: "+temperatura+"°c";
    }
}
