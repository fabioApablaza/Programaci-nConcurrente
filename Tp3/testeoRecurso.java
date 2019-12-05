
package Tp3;




public class testeoRecurso{
    public static void main(String[]args){
        ClienteR juan= new ClienteR();
        Thread juan1= new Thread(juan);
        juan1.setName("Juan Lopez");
        ClienteR ines= new ClienteR();
        Thread ines1= new Thread(ines);
        ines1.setName("Ines Garcia");
        juan1.start();        
        ines1.start();
    }
}
