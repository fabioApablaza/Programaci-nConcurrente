/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//TODO:
/*
|1|- Se tiene que verificar que haya un instructor disponible para dar el curso, ya que la implementación esta hecha asumiendo que hay 5 cursos y que cada instructor esta asignado a 1.
|2|- Se tiene que verificar que el instructor sea de Sky o de Snowboard, ya que el esquiador pueda querer una en particular. Además puede ser que el instructor este capacitado para una solamente.

 */
package TPOCentroDeEsqui;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */


public class Curso {

    private int idCurso;
    private int cantidadCurso;
    private boolean instructorActivo;//Variable para determinar si el instructor esta ocioso (false) o esta dando una clase (true)
    private Semaphore mutex;
    private CyclicBarrier barrera;
    private CyclicBarrier barreraSalir;

    //Constructor
    public Curso(int id) {
        this.idCurso = id;
        this.cantidadCurso = 0;
        this.mutex = new Semaphore(1, true);
        this.barrera = new CyclicBarrier(5);
        this.barreraSalir = new CyclicBarrier(5);
    }

    public int cabina(String nombre) {
        try {
            System.out.println("El instructor " + nombre + " esta en la cabina " + idCurso);

            //Se sincroniza el acceso a la variable para que los esquiadores no la accedan mientras el hilo instructor la este modificando
            mutex.acquire();
            instructorActivo = false;//El instructor indica que está esperando a los esquiadores
            mutex.release();

            barrera.await();//El instructor espera hasta que lleguen la cantida de alumnos

            //Se sincroniza el acceso a la variable para que los esquiadores no la accedan mientras el hilo instructor la este modificando
            mutex.acquire();
            instructorActivo = true;//El instructor indica que está por dar una clase
            mutex.release();

            System.out.println("El instructor " + nombre + " esta por dar una clase de esqui");
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException ex) {
            //Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadCurso;
    }

    public boolean contratarCurso(int id) {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desisten de tomar la clase si la cantidad de esquiadores es la menor de la necesaria
        boolean cursoCompleto = true;

        try {
            barrera.await(2000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la clase N° " + idCurso + " y los estudiantes se fueron ");
            barrera = new CyclicBarrier(5);
            this.cantidadCurso = 0;
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cantidadCurso < 4) {
            cursoCompleto = false;
        }

        return cursoCompleto;
    }

    public void salirCurso(int id) {
        //Modulo que simula la salida de los alumnos del curso
        try {
            this.barreraSalir.await();
            System.out.println("El esquiador " + id + " salio del curso de esqui");
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void terminarCurso(String nom) {
        /*Los Intructores terminan de dar el curso y dejan salir a los alumnos de la clase.
        La variable clase es el "aula" o clase que le toco al instructor */
        try {
            System.out.println("El instructor " + nom + " termino de dar el curso de esqui");
            this.barreraSalir.await();
            this.cantidadCurso = 0;
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getActividadInstructor() {
        boolean activo = false;
        try {
            //Se sincroniza el acceso a la variable para que los esquiadores no la accedan mientras el hilo instructor la este modificando
            this.mutex.acquire();
            activo = this.instructorActivo;
            this.mutex.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activo;
    }

    public int getCantidadCurso() {
        return this.cantidadCurso;
    }

    public void incrementarCantidadCurso() {
        this.cantidadCurso++;
    }

    public void reiniciar() {
        this.cantidadCurso = 0;
    }
}
