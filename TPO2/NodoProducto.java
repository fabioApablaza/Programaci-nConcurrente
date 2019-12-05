package TPO2;




public class NodoProducto {
    private Producto elem;
    private NodoProducto enlace;
    public NodoProducto(Producto elem, NodoProducto enlace){
        this.elem = elem;
        this.enlace= enlace;
    }
    public NodoProducto(Producto elemN){
        this.elem=elemN;
        this.enlace=null;
    }
    public Producto getElem(){
        return elem;
    }
    public void setElem(Producto elem){
        this.elem=elem;
    }
    public NodoProducto getEnlace(){
        return enlace;
    }
    public void setEnlace(NodoProducto enlaceN){
        this.enlace=enlaceN;
    }
}
