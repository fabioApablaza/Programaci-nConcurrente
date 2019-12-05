
package EmpresaAgro;


public class ProductoRefrigerado extends Productofresco{
    private int codigoOrganizacion;
    private int tempMantenimiento;
    public ProductoRefrigerado(){
        super();
        codigoOrganizacion=0;
        tempMantenimiento=0;
    }
    public ProductoRefrigerado(int den,String fechaC,int nroL,String fechaEnv, String paisOr,int cod,int temp){
        super(den,fechaC,nroL,fechaEnv,paisOr);
        codigoOrganizacion=cod;
        tempMantenimiento=temp;
    }
    public int getCodigoOrganizacion(){
        return codigoOrganizacion;
    }
    public int gettempMantenimiento(){
        return tempMantenimiento;
    }
    public void setCodigoOrganizacion(int cod){
        codigoOrganizacion=cod;
    }
    public void setTempMantenimiento(int temp){
        this.tempMantenimiento=temp;
    }
    @Override
    public String toString(){
        return super.toString()+" Codigo de Organizacion: "+codigoOrganizacion+" Temperatura de mantenimiento: "+tempMantenimiento+"°c";
    }
}
