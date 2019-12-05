
package EmpresaAgro;


public class Recurso {
    static void uso(){
        Thread t=Thread.currentThread();
        System.out.println("en recurso: Soy "+t.getName());
    }
}

