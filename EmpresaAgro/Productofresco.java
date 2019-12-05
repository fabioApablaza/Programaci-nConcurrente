
package EmpresaAgro;


public class Productofresco extends Producto{
    private String fechaEnvasado;
    private String paisOrigen;
    public Productofresco(){
        super();
        fechaEnvasado="nunca";
        paisOrigen="Argentina";        
    } 
    public Productofresco(int den,String fechaC,int nroL,String fechaEnv, String paisOr){
        super(den,fechaC,nroL);
        fechaEnvasado=fechaEnv;
        paisOrigen=paisOr;
    }
    public String getfechaEnvasado(){
        return fechaEnvasado;
    }
    public String getPaisOrigen(){
        return paisOrigen;
    }
    public void setfechaEnvasado(String fechaEnv){
        fechaEnvasado=fechaEnv;
    }
    public void setpaisOrigen(String pais){
        paisOrigen=pais;
    }
    @Override
    public String toString(){
        return super.toString()+" Fecha de envasado: "+fechaEnvasado+" Pais de origen: "+paisOrigen;
    }
}
