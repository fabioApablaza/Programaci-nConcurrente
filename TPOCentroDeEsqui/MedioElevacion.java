/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
/*
TODO: 
    |1|-Verificar que los hilos esquiadores que esperen en los CountDownLatch sea la cantidad correcta, ya que puede ocurrir que los hilos esquiadores liberen 
      los permisos antes de sentarse y eso permitiría que más hilos esquiadores esperen.
    


 */
public class MedioElevacion {

    private int identificacion;// Identifiacion del medio de elevacion
    private int cantidadPersonas;// Variable para contar cuantos esquiadores pasaron por este medio de elevacion
    private int posicion;//Sirve para marcar en que posición del arreglo de CountDownLatch deben esperar los hilos esquiadores
    private int CANTIDADSEMAFOROS = 2;
    private boolean entrada;// variable para permitir a los esquiadores entrar al medio de elevacion
    private Semaphore molinete;// Semaforo que simulara a los molinetes que dejaran pasar a los n esquiadores
    private Semaphore mutex[];//arreglo de semaforos de un solo permiso c/u.
    private GestorCinta unGestor;
    private CyclicBarrier barrera;
    private CountDownLatch barreraSalida[];
    // private CyclicBarrier barreraGestor;
    private Thread hiloGestor;

    /* El arreglo mutex que es de tipo Semaphore es de longitud 2 ya que:
    Posicion 0 - Sincroniza el incremento de la variable cantidad de personas.
    Posicion 1 - Es para sincronizar el acceso a la variable posicion. A esta variable la acceden tanto los hilos esquiadores y el hilo gestor de la cinta.
    
     */
    //Contructor
    public MedioElevacion(int id, CentroEsqui unCent) {
        this.identificacion = id;
        this.cantidadPersonas = 0;
        this.molinete = new Semaphore(id, true);
        this.mutex = new Semaphore[CANTIDADSEMAFOROS];
        this.barrera = new CyclicBarrier(id + 1);//La cantidad de permisos es la cantidad máxima de esquiadores más el hilo gestor
        this.unGestor = new GestorCinta(this);
        this.posicion = 0;
        this.entrada = true;
        this.barreraSalida = new CountDownLatch[4];
        crearHiloGestor();
        crearSillas();
        crearSemaforos();

    }

    // Que hacen los esquiadores cuando entran al medio de elevacion?
    // 1) Si hay gente para entrar a ese medio esperan
    // 2) Pasan por el molinete
    // 3) Esperan a la aerosilla y se sientan en la aerosilla
    // 4) Dejan que la aerosilla los lleve
    // 6) Se van a esquiar
    
    private void crearSillas() {
        //Se crean los contadores para sincronizar a los esquiadores dependiendo en la posicion de la cinta en que esten
        for (int i = 0; i < this.barreraSalida.length; i++) {
            this.barreraSalida[i] = new CountDownLatch(2);
        }

    }

    private void crearHiloGestor() {
        //Se crea el hilo gestor de la cinta
        hiloGestor = new Thread(unGestor);
        hiloGestor.start();
    }

    private void crearSemaforos() {
        //Se crean los semaforos de un solo permiso para sincronizar el acceso a datos críticos
        for (int i = 0; i < this.mutex.length; i++) {
            this.mutex[i] = new Semaphore(1, true);
        }
    }

    public synchronized void inicioGestor() {
        try {
            //El hilo gestor de la cinta es iniciado en el horario de apertura de los medios de elevacion por el hilo tiempo
            
            if(entrada){//Se verifica si es el horario de los medios de elevacion
          
                this.wait();
            }
            
       } catch (InterruptedException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        }// catch (BrokenBarrierException ex) {
         //   Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public synchronized void darAceesoMedio() {
        //Entrada al medio de elevación

        try {

            //System.out.println("El esquiador "+id+" esta en la cola para entrar al medio "+identificacion);
            while (entrada) {//Si no es el horario de entrada para los medios de elevacion entonces los esquiadores deben esperar

                this.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void darAcceso(int idEsquiador) {

        try {
            System.out.println("El esquiador " + idEsquiador + " esta queriendo acceder a los molinetes del medio numero " + this.identificacion);
            //Simulación de los molinetes de cada medio de elevación
            this.molinete.acquire();//Se dejan pasar n esquiadores 
            System.out.println("El esquiador " + idEsquiador + " paso por los molinetes del medio numero " + this.identificacion);

            //Se sincroniza el incremento a la variable de la cantidad de personas que pasan por el medio
            this.mutex[0].acquire();

            this.cantidadPersonas++;

            this.mutex[0].release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int sentarseSilla() {
        /*
        Por este método los hilos esquiadores reciben la posición del arreglo de CountDownLatch
        donde tienen que esperar, esto simularia ser la silla que pasa a recogerlos
         */

        int MITADARREGLO = barreraSalida.length/2, pos=0;
        try {

            /*Se sincroniza el acceso a la variable posicion:
                1-Se restringe el acceso a un esquiador por vez.
                2-Se sincroniza el acceso entre los esquiadores y el hilo gestor de la cinta.
             */
            this.mutex[1].acquire();

            /*
            Se calcula la posicion del arreglo de CountDownLatch donde deben esperar los hilos esquiadores de acuerdo a la variable posicion.
            Siempre se calcula la posicion opuesta por donde el hilo gestor de la cinta esta liberando a esquiadores.
             */
            if (posicion >= MITADARREGLO) {
                pos = posicion - MITADARREGLO;
            } else {
                pos = posicion + MITADARREGLO;
            }

            this.mutex[1].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pos;
    }

    public void salirMedio(int idEsquiador, int pos) throws InterruptedException {
        //Los esquiadores esperan a que el hilo silla termina de transportarlos

       

        try {

            /*
            Los hilos esquiadores esperan a que el hilo gestor de la cinta cambie el valor asignado a la "posicion"
            de esta manera los hilos esquiadores podrán liberar los permisos del molinete y esperar en el CountDownLatch correspondiente 
            sin correr el riesgo de que otros hilos esquiadores crucen el molinete y sean asignados a la misma posicion del CountDownLatch
             */
            barrera.await();

        } catch (BrokenBarrierException ex) {
            
        }
        
        System.out.println("El esquiador " + idEsquiador + " del medio N° " + identificacion + " con silla n° " + posicion);
        this.molinete.release();//liberan los permisos de los molinetes para que otros esquiadores puedan entrar al medio

        //System.out.println("El esquiador " + idEsquiador + " del medio N° " + identificacion + " salio con posicion " + posicion);

        this.barreraSalida[pos].await();//los esquiadores esperan aqui

        System.out.println("El esquiador " + idEsquiador + " salio del medio N° " + identificacion);

    }

    //Metodos para el gestor de cinta
    public void moverCinta() throws InterruptedException {
        //La cinta se mueve y de acuerdo a la posición en que este algunos esquiadores estaran esperando en las aerosillas y otros en la cima de la montaña

        

        try {

            barrera.await(1000, TimeUnit.MILLISECONDS);

            //Decrementa el contador del countDownLactch donde los esquiadores esperan si el contador llega a 0 entonces
            //se libera a los esquiadores de acuerdo a la posicion en que esten en la cola
            this.barreraSalida[posicion].countDown();

        } catch (BrokenBarrierException ex) {
            
        } catch (TimeoutException ex) {
            //Se crea de nuevo la barrera
            this.barrera = new CyclicBarrier(identificacion + 1);
        }

       
        this.mutex[1].acquire();

        //El gestor de la cinta actualiza la posicion de la cinta
        if (this.posicion == (this.barreraSalida.length - 1)) {
            posicion = 0;
        } else {
            posicion++;
        }

        this.mutex[1].release();

    }

    // getters and setters
    public int getCantidadPersonas() {
        return this.cantidadPersonas;
    }

    public void resetearContadorpersonas() {
        this.cantidadPersonas = 0;
    }

    public synchronized void abrirMedio() {
        //El hilo tiempo abre este medio asignando a la variable entrada el valor verdadero para luego despertar a los hilos que estan dormidos
        
       
            
            
            this.entrada = false;
            
            
        
            this.notifyAll();
            
       
    }

    public synchronized void cerrarMedio() {
        /*
        El hilo tiempo cierra este medio asignando a la variable entrada el valor falso 
         */

        this.entrada = true;
    }

}
