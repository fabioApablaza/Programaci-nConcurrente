package Tp4;


public class Turno {

    private int id = 1;
    
    public int getId() {
        return this.id;
    }
    public void modifTurno(){
        if(this.id==2||this.id==1){
            this.id+=1;
        }
        else
            this.id=1;
    }
    public synchronized boolean esTurno(int a)throws Exception{
        boolean res=false;
        //System.out.print(id);
        if (a == this.id) {
            res=true;            
            this.modifTurno();
        } 
        
        return res;
    }
    

    public void setId(int id) {
        this.id = id;
    }
}
