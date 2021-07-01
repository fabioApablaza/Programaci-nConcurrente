/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
TODO: 
6:30 marca un error en centro de Esqui
16:30 define los medios de elevación

TODO:
|1|- Rediseñar el metodo para acceder a los medios de elvación.
|2|- Verificar las clase curso de Sky
|3|- Utilizar una sola herramienta en la confitería

 */
package TPOCentroDeEsqui;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class CentroEsqui {

    private Confiteria unaConfiteria;
    private Semaphore mutex[];// Permite sincronizar el acceso a diferentes variables.
    private boolean entradaInstructores, entrada, entradaMedios;//determinan el horario de entrada del centro de esquí, instructores y a los medios de elvación.
    private Random aleatorio;//Utilizada para la toma de decisiones.
    private MedioElevacion[] medios; //El centro de esqui tiene un arreglo de 4 medios de elevacion, cada uno con una cantidad fija de esquiadores por silla.
    private Curso[] cursos; //El centro de esqui tiene un arreglo de 5 cursos donde los esquiadores podran tomar lecciones tanto de esqui como de Snowboard.
    private int CANTIDADSEMAFOROS = 4;

    /* El arreglo mutex que es de tipo Semaphore es de longitud 4 ya que:
    Posicion 0 - Es para permitir a los hilos esquiadores buscar un curso de Sky de a un hilo por vez.
    Posicion 1 - Es para sincronizar a los hilos esquiadores con el hilo tiempo para acceder a la variable entradaMedios.
    Posicion 2 - Es para sincronizar a los hilos esquiadores con el hilo tiempo para acceder a la variable entradaInstructores.
    Posicion 3 - Es para permitir a los hilos esquiadores buscar un curso de Snowboard de a un hilo por vez.
     */
    public CentroEsqui(Confiteria unaConfi) {
        //Constructor
        this.unaConfiteria = unaConfi;
        this.aleatorio = new Random();
        this.mutex = new Semaphore[CANTIDADSEMAFOROS];
        this.entradaMedios = false;
        this.entrada = false;
        this.entradaInstructores = false;
        this.medios = new MedioElevacion[4];
        this.cursos = new Curso[5];
        this.crearMediosElevacion();
        this.crearCursos();
        this.crearArregloSemaforos();
    }

    private void crearArregloSemaforos() {
        //Metodo para crear los semaforos de un solo permiso para sincronizar el acceso a datos críticos
        for (int i = 0; i < mutex.length; i++) {
            mutex[i] = new Semaphore(1, true);

        }
    }

    private void crearMediosElevacion() {
        //Metodo para crear los medios de elevación en el arreglo de medios
        for (int i = 0; i < medios.length; i++) {
            medios[i] = new MedioElevacion((i + 1), this);

        }
    }

    private void crearCursos() {
        //Metodo para crear las clases donde los esquiadores tomaran cursos
        for (int i = 0; i < cursos.length; i++) {
            cursos[i] = new Curso((i + 1));

        }
    }

    

    public synchronized void entradaAlCentro() {
        //Metodo para simular la cola de espera para entrar al centro
        while (!this.entrada) {

            try {

                this.wait();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    //Metodos para los medios de elevacion
    public int accederMedio(int idEsquiador, int idMedio) throws InterruptedException {
        //Metodo donde se verifica que los esquiadores tenga el pase para poder acceder a los medios
        int pos = 0;

        

            //Los esquiadores deben esperar su turno en la cola para acceder al medio que ha elegido
            medios[idMedio].darAceesoMedio();

            //Los esquiadores ingresan al medio para pasar por los molinetes
            medios[idMedio].darAcceso(idEsquiador);

            pos = medios[idMedio].sentarseSilla();

        

        return pos;
    }

    public void salirMedio(int idEsquiador, int idMedio, int pos) {
        try {

            medios[idMedio].salirMedio(idEsquiador, pos);

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirAccesosMedioElevacion() {
        //Modulo para permitir acceder a los medios de elevacion
        System.out.println("Se abre el acceso a los medios de elevacion");

        try {

            this.mutex[1].acquire();
            this.entradaMedios = true;
            this.mutex[1].release();

            //Esta variable se usa para tener una garantía de que los hilos esquiadores no se queden
            //en los metodos de los medios de elevación
            /*Se sincroniza el acceso a la variable entrada medio para que ningún esquiador acceda a ella mientras
            el hilo tiempo la este modificando*/
        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < medios.length; i++) {
            
            medios[i].abrirMedio(); //Asigna a las variables de entrada con false y despierta a los hilos esquiadores que estan dormidos
        }
    }

    public void cerrarAccesoMedioElevacion() {
        //Modulo para cerrar el acceso a los medios de elevacion
        System.out.println("Se cierra el acceso a los medios de elevacion");

        try {
            /*Se sincroniza el acceso a la variable entrada medio para que ningún esquiador acceda a ella mientras
            el hilo tiempo la este modificando*/

            this.mutex[1].acquire();
            this.entradaMedios = false;
            this.mutex[1].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for (int i = 0; i < medios.length; i++) {
             //Cambia los valores de las variables booleanas "entrada" de los medios para no permitir que ingresen más esquiadores
            medios[i].cerrarMedio();
        }
    }

    
    public int busquedaCursoSky() throws InterruptedException {
        //Metodo que en el cual los esquiadores buscan algun curso de Sky que no este lleno
        //Si encuentran uno se les da un Identificador que es el numero de aula al cual estan asignados sino se les devuelve el 5
        int claseAsignada = 0, CANTMAX = 4, CLASES = 3;
        boolean condicionDeSalida = true;
        try {
            mutex[0].acquire();//

            while (claseAsignada < CLASES && condicionDeSalida) {

                //Se verifica que haya lugar en el curso y además se verifica que en dicho curso el instructor este disponible para dar clase
                if (cursos[claseAsignada].getCantidadCurso() < CANTMAX && !cursos[claseAsignada].getActividadInstructor()) {
                    cursos[claseAsignada].incrementarCantidadCurso();
                    condicionDeSalida = false;

                } else {
                    claseAsignada++;
                }
            }

            //Si el esquiador no encontró un curso vacio o casí vacio significa que la variable de claseAsignada es 3 
            //como 3 no corresponde a un curso de Sky entonces se debe asignar a dicha variable el valor 5 determinando que el esquiador no pudo encontrar un curso disponible
            //Con un valor 5 se realizan las posteriores verificaciones y no se ejecutan los metodos que corresponden a los esquiadores que si encontraron un curso disponible
            if (claseAsignada == 3) {
                claseAsignada = 5;
            }

        } finally {
            mutex[0].release();
        }
        return claseAsignada;
    }

    public int busquedaCursoSnowboard() throws InterruptedException {
        //Metodo que en el cual los esquiadores buscan algun curso de esqui que no este lleno
        //Si encuentran uno se les da un Identificador que es el numero de aula al cual estan asignados sino se les devuelve el 5
        int claseAsignada = 3, CANTMAX = 4;
        boolean condicionDeSalida = true;
        try {
            //Se deja pasar de a 1 esquiador por vez
            mutex[3].acquire();

            //Primero se verifica que el valor de la clase no sobrepase la longitud del arreglo 
            //condicionDeSalida sirve para salir del bucle cuando se haya encontrado un curso disponible
            while (claseAsignada < cursos.length && condicionDeSalida) {

                //Se verifica que haya lugar en el curso y además se verifica que en dicho curso el instructor este disponible para dar clase
                if (cursos[claseAsignada].getCantidadCurso() < CANTMAX && !cursos[claseAsignada].getActividadInstructor()) {
                    cursos[claseAsignada].incrementarCantidadCurso();
                    condicionDeSalida = false;
                } else {
                    claseAsignada++;
                }
            }
            //Como el número de retorno será 3, 4 y 5 no será necesario hacer la última verificación del anterior método
        } finally {
            mutex[3].release();
        }
        return claseAsignada;
    }

    public boolean entrarCurso(int idCurso, int idEsquiador) {
        return cursos[idCurso].contratarCurso(idEsquiador);
    }

    public void salirCurso(int idCurso, int idEsquiador) {
        cursos[idCurso].salirCurso(idEsquiador);
    }

    public int cabinaInstructores(int idCurso, String nombre) {

        return cursos[idCurso].cabina(nombre);
    }

    public void terminarCurso(String nombre, int numCurso) {
        cursos[numCurso].terminarCurso(nombre);
    }

//Confiteria
    public void entradaConfiteria(int idEsquiador) throws InterruptedException {
        this.unaConfiteria.entrarConfiteria();//El esquiador entra a la confiteria
        
        if (this.unaConfiteria.entrarCaja(idEsquiador) == 1) {//El esquiador decide si comprar postre o no
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
            this.unaConfiteria.entrarMostradorPostre(idEsquiador);

        } else {
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
        }
    }

    public void salirConfiteria() {
        this.unaConfiteria.salirConfiteria();
    }

    //getters and setters    
    public void inicioHorarioInstructores() {
        //El hilo tiempo determinada el comienzo del horario laboral de los instructores
        try {
            /*Se sincroniza el acceso a la variable entrada medio para que ningún esquiador acceda a ella mientras
            el hilo tiempo la este modificando*/

            this.mutex[2].acquire();
            this.entradaInstructores = true;
            this.mutex[2].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void finHorarioInstructores() {
        //El hilo tiempo da por terminada la jornada laboral de los intructores

        try {
            /*Se sincroniza el acceso a la variable entrada medio para que ningún esquiador acceda a ella mientras
            el hilo tiempo la este modificando*/

            this.mutex[2].acquire();
            this.entradaInstructores = false;
            this.mutex[2].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void abrirEntrada() throws InterruptedException {

        
        this.entrada = true;
        

        System.out.println("Se abre el acceso a los esquiadores e instructores");
        this.notifyAll();//Se despierta a los hilos esquiadores e instructores que estan dormidos en el metodo en entradaAlCentro()
    }

    public synchronized void cerrarEntrada() {
        /*Metodo en el cual se cierra el acceso al centro de esqui, se muestra la cantidad de personas
        que utilizo cada medio y se restable a cero el contador de personas de cada medio de elevacion*/

 /* Se sincroniza el acceso a esta variable por miedo de que algun 
        esquiador acceda a ella mientras el hilo tiempo la este modificando
         */
       

            
            this.entrada= false;
            

       

        System.out.println("Uso de medios de elevacion: ");
        for (int i = 0; i < medios.length; i++) {
            System.out.println("Medio de elevacion N°" + (i + 1) + " : " + this.medios[i].getCantidadPersonas());
            medios[i].resetearContadorpersonas();//Se reestablecen los valores de cada contador de personas a cero
        }
        for (int j = 0; j < cursos.length; j++) {
            cursos[j].reiniciar();
        }
    }

    public boolean getHorarioInstructores() {
        boolean entradaInstructor = false;

        /* Se sincroniza el acceso a esta variable por miedo de que algun 
        esquiador acceda a ella mientras el hilo tiempo la este modificando
         */
        try {

            this.mutex[2].acquire();
            entradaInstructor = this.entradaInstructores;
            this.mutex[2].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entradaInstructor;
    }

    public synchronized boolean getEntrada() {
        
        return this.entrada;
    }

    public boolean getEntradaMedios() {
        /*Se sincroniza el acceso a la variable entrada medio para que ningún esquiador acceda a ella mientras
            el hilo tiempo la este modificando*/
        boolean entradaMedio = false;
        try {

            this.mutex[1].acquire();
            entradaMedio = this.entradaMedios;
            this.mutex[1].release();

        } catch (InterruptedException ex) {
            Logger.getLogger(CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entradaMedio;
    }
}
