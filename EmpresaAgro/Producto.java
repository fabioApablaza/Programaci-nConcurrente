package EmpresaAgro;

public class Producto {

    private int denominacion;
    private String fechaCaducidad;
    private int nroLote;

    public Producto() {
        this.denominacion = -500;
        this.fechaCaducidad = "Nunca";
        this.nroLote = 0;
    }

    public Producto(int den, String fechaC, int nroL) {
        this.denominacion = den;
        this.fechaCaducidad = fechaC;
        this.nroLote = nroL;
    }
    public int getDenominacion(){
        return denominacion;
    }
    public String getFechaCaducidad(){
        return fechaCaducidad;
    }
    public int getNroLote(){
        return nroLote;
    }
    public void setDenominacion(int den){
        this.denominacion=den;
    }
    public void setFechaCaducidad(String fechaC){
        this.fechaCaducidad=fechaC;
    }
    public void setNroLote(int nroL){
        this.nroLote=nroL;
    }
    @Override
    public String toString(){
        return "denominacion: "+denominacion+" fecha de caducidad: "+fechaCaducidad+" Nro de Lote: "+nroLote;
    }

}
