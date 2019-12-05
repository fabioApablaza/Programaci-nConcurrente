/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author user
 */
public class CentroEsqui {

    private Confiteria unaConfiteria;
    private Lock cerrojo;
    private boolean entradaInstructores;
    private boolean entradaEsquiadores;
    private int cantPrimerClase;
    private int cantSegClase;
    private int cantTercerClase;
    private int cantCuartaClase;
    private int cantQuintaClase;
    private int cantPersonasPrimerElevador;
    private int cantPersonasSegundoElevador;
    private int cantPersonasTercerElevador;
    private int cantPersonasCuartoElevador;
    private Condition intructores;
    private Condition estudiantes;
    private CyclicBarrier primerBarrera;
    private CyclicBarrier segundaBarrera;
    private CyclicBarrier tercerBarrera;
    private CyclicBarrier cuartaBarrera;
    private CyclicBarrier quintaBarrera;
    private CyclicBarrier primerBarreraSalir;
    private CyclicBarrier segundaBarreraSalir;
    private CyclicBarrier tercerBarreraSalir;
    private CyclicBarrier cuartaBarreraSalir;
    private CyclicBarrier quintaBarreraSalir;
    private Random aleatorio;
    private MedioElevacion[] medios;
    private Silla[] sillas;

    public CentroEsqui(Confiteria unaConfi) {
        //Constructor
        this.unaConfiteria = unaConfi;
        this.aleatorio = new Random();
        this.cerrojo = new ReentrantLock(true);
        this.estudiantes = cerrojo.newCondition();
        this.intructores = cerrojo.newCondition();
        this.entradaEsquiadores = false;
        this.entradaInstructores = false;
        this.cantPrimerClase = 0;
        this.cantSegClase = 0;
        this.cantTercerClase = 0;
        this.cantCuartaClase = 0;
        this.cantQuintaClase = 0;
        this.cantPersonasPrimerElevador = 0;//cantidad de esquiadores que ultilizaron el primer medio de elevacion
        this.cantPersonasSegundoElevador = 0;//cantidad de esquiadores que ultilizaron el segundo medio de elevacion
        this.cantPersonasTercerElevador = 0;//cantidad de esquiadores que ultilizaron el tercer medio de elevacion
        this.cantPersonasCuartoElevador = 0;//cantidad de esquiadores que ultilizaron el cuarto medio de elevacion
        this.primerBarrera = new CyclicBarrier(5);
        this.segundaBarrera = new CyclicBarrier(5);
        this.tercerBarrera = new CyclicBarrier(5);
        this.cuartaBarrera = new CyclicBarrier(5);
        this.quintaBarrera = new CyclicBarrier(5);
        this.primerBarreraSalir = new CyclicBarrier(5);
        this.segundaBarreraSalir = new CyclicBarrier(5);
        this.tercerBarreraSalir = new CyclicBarrier(5);
        this.cuartaBarreraSalir = new CyclicBarrier(5);
        this.quintaBarreraSalir = new CyclicBarrier(5);
        this.sillas = new Silla[4];
    }

    public void entradaAlCentro() throws InterruptedException {
        //Metodo en el cual los hilos esquiadores esperan la apertura del centro de esqui
        while (!this.entradaEsquiadores) {//los esquiadores esperan hasta que se abra el centro de esqui
            this.wait();
        }
    }
    public void crearMediosElevacion(){
        
    }

    public synchronized void sillasElevadoras(int id, boolean pase, int idSilla) throws InterruptedException {
        //Metodo de las sillas elevadoras
        if (pase) {//Se verifica si el esquiador tiene un pase para acceder a los molinetes
            switch (idSilla) {//segun la eleccion del medio de elevación
                case 1: {
                    medios[0].darAcceso(id);
                    medios[0].salirMedio();
                    break;
                }
                case 2: {
                    medios[1].darAcceso(id);
                    medios[1].salirMedio();
                    break;
                }
                case 3: {
                    medios[2].darAcceso(id);
                    medios[2].salirMedio();
                    break;
                }
                case 4: {
                    medios[3].darAcceso(id);
                    medios[3].salirMedio();
                    break;
                }
            }
        }

    
    

    public void abrirAccesosMedioElevacion() {
        //Modulo para permitir acceder a los medios de elevacion
        System.out.println("Se abre el acceso a los medios de elevacion");

    }

    public void cerrarAccesoMedioElevacion() {
        //Modulo para cerrar el acceso a los medios de elevacion
        System.out.println("Se cierra el acceso a los medios de elevacion");

    }

    public int entrarCurso() throws InterruptedException {
        //Metodo que en el cual los esquiadores buscan algun curso de esqui que no este lleno
        //Si encuentran uno se les da un Identificador que es el numero de aula al cual estan asignados sino se les devuelve el 0
        int claseAsignada = 0, CANTMAX = 4, PRIMERAULA = 1, SEGUNDAAULA = 2, TERCERAULA = 3, CUARTAAULA = 4, QUINTAAULA = 5;
        try {
            this.cerrojo.lock();
            if (cantPrimerClase < CANTMAX) {
                this.cantPrimerClase++;
                claseAsignada = PRIMERAULA;
                if (cantPrimerClase == CANTMAX) {
                    this.intructores.signal();
                }
            } else {
                if (cantSegClase < CANTMAX) {
                    this.cantSegClase++;
                    claseAsignada = SEGUNDAAULA;
                    if (cantSegClase == CANTMAX) {
                        this.intructores.signal();
                    }
                } else {
                    if (cantTercerClase < CANTMAX) {
                        this.cantTercerClase++;
                        claseAsignada = TERCERAULA;
                        if (cantTercerClase == CANTMAX) {
                            this.intructores.signal();
                            cantTercerClase = 0;
                        }
                    } else {
                        if (cantCuartaClase < CANTMAX) {
                            this.cantCuartaClase++;
                            claseAsignada = CUARTAAULA;
                            if (cantCuartaClase == CANTMAX) {
                                this.intructores.signal();
                            }
                        } else {
                            if (cantQuintaClase < CANTMAX) {
                                this.cantQuintaClase++;
                                claseAsignada = QUINTAAULA;
                                if (cantQuintaClase == CANTMAX) {
                                    this.intructores.signal();
                                }
                            }
                        }
                    }
                }
            }

        } finally {
            cerrojo.unlock();
        }
        return claseAsignada;
    }

    public boolean contratarPrimerClase(int id) throws InterruptedException {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desistan de tomar la clase        
        boolean cursoCompleto = true;
        try {
            if (primerBarrera.isBroken()) {//Se verifica si la barrera se rompio porque no se formó el grupo
                cursoCompleto = false;
            } else {
                primerBarrera.await(2000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase
            }
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la primer clase y los estudiantes se fueron ");
            primerBarrera.reset();
            cantPrimerClase = 0;
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
        return cursoCompleto;
    }

    public boolean contratarSegundaClase(int id) throws InterruptedException {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desistan de tomar la clase        
        boolean cursoCompleto = true;
        try {
            if (segundaBarrera.isBroken()) {//Se verifica si la barrera se rompio porque no se formó el grupo
                cursoCompleto = false;
            } else {
                segundaBarrera.await(2000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase
            }
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la segunda clase y los estudiantes se fueron");
            cantSegClase = 0;
            segundaBarrera.reset();
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
        return cursoCompleto;
    }

    public boolean contratarTercerClase(int id) throws InterruptedException {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desistan de tomar la clase        
        boolean cursoCompleto = true;
        try {
            tercerBarrera.await(2000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase

        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la tercer clase y los estudiantes se fueron ");
            this.cantTercerClase = 0;
            this.tercerBarrera.reset();
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
        return cursoCompleto;
    }

    public boolean contratarCuartaClase(int id) throws InterruptedException {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desistan de tomar la clase        
        boolean cursoCompleto = true;
        try {
            if (cuartaBarrera.isBroken()) {//Se verifica si la barrera se rompio porque no se formó el grupo
                cursoCompleto = false;
            } else {
                cuartaBarrera.await(2000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase
            }
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la cuarta clase y los estudiantes se fueron ");
            cantCuartaClase = 0;
            cuartaBarrera.reset();
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
        return cursoCompleto;
    }

    public boolean contratarQuintaClase(int id) throws InterruptedException {
        //Metodo en el cual los esquiadores que desean tomar clases de esqui esperan a ser atendidos o desistan de tomar la clase        
        boolean cursoCompleto = true;
        try {
            if (quintaBarrera.isBroken()) {//Se verifica si la barrera se rompio porque no se formó el grupo
                cursoCompleto = false;
            } else {
                quintaBarrera.await(6000, TimeUnit.MILLISECONDS);//Los esquiadores esperan un tiempo hasta que este el grupo armado, sino desisten de tomar la clase
            }
        } catch (TimeoutException e) {
            //Codigo cuando el tiempo para armar el grupo finalizo y no estan todos los integrantes
            System.out.println("No hay demasiados integrantes en la cuarta clase y los estudiantes se fueron ");
            cantQuintaClase = 0;
            quintaBarrera.reset();
        } catch (BrokenBarrierException e) {
            //Codigo cuando se pudo armar el grupo
        }
        return cursoCompleto;
    }

    public void salirCurso(int id, int clase) throws InterruptedException {
        //Modulo que simula la silada de los alumnos del curso
        try {
            switch (clase) {
                case 1: {
                    this.primerBarreraSalir.await();
                    break;
                }
                case 2: {
                    this.segundaBarreraSalir.await();
                    break;
                }
                case 3: {
                    this.tercerBarreraSalir.await();
                    break;
                }
                case 4: {
                    this.cuartaBarreraSalir.await();
                    break;
                }
                case 5: {
                    this.quintaBarreraSalir.await();
                    break;
                }
            }
            System.out.println("El esquiador " + id + " salio del curso de esqui");
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void cabinaInstructores(String nombre) {
        //Metodo de simulacion donde los instructores esperan
        try {
            this.cerrojo.lock();
            while (!this.entradaInstructores) {
                this.intructores.await();
            }
            System.out.println("El instructor " + nombre + " esta por dar una clase de esqui");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.cerrojo.unlock();
        }
    }

    public synchronized int buscarNumClase(String nombre) throws InterruptedException, BrokenBarrierException {
        //Metodo en el cual los instructores reciben el numero de clase al que deben acudir
        int claseAsignada = 0;
        if (this.cantPrimerClase == 4) {
            cantPrimerClase++;
            claseAsignada = 1;
            this.primerBarrera.await();
        } else {
            if (this.cantSegClase == 4) {
                cantSegClase++;
                claseAsignada = 2;
                this.segundaBarrera.await();
            } else {
                if (this.cantTercerClase == 4) {
                    cantTercerClase++;
                    claseAsignada = 3;
                    this.tercerBarrera.await();
                } else {
                    if (this.cantCuartaClase == 4) {
                        cantCuartaClase++;
                        claseAsignada = 4;
                        this.cuartaBarrera.await();
                    } else {
                        if (this.cantQuintaClase == 4) {
                            cantQuintaClase++;
                            claseAsignada = 5;
                            this.quintaBarrera.await();
                        }
                    }
                }
            }
        }
        System.out.println("Al instructor " + nombre + " se le asigno el aula n° " + claseAsignada);
        return claseAsignada;
    }

    public void terminarCurso(String nom, int clase) throws InterruptedException {
        /*Los Intructores terminan de dar el curso y dejan salir a los alumnos de la clase.
        La variable clase es el "aula" o clase que le toco al instructor */

        try {
            System.out.println("El instructor " + nom + " termino de dar el curso de esqui");
            switch (clase) {
                case 1: {
                    this.primerBarreraSalir.await();
                    break;
                }
                case 2: {
                    this.segundaBarreraSalir.await();
                    break;
                }
                case 3: {
                    this.tercerBarreraSalir.await();
                    break;
                }
                case 4: {
                    this.cuartaBarreraSalir.await();
                    break;
                }
                case 5: {
                    this.quintaBarreraSalir.await();
                    break;
                }
            }
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

//Confiteria
    public void entradaConfiteria(int idEsquiador) throws InterruptedException {
        this.unaConfiteria.entrarConfiteria();//EL esquiador entra a la confiteria
        if (this.unaConfiteria.entrarCaja(idEsquiador) == 1) {//El esquiador decide si comprar postre o no
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
            this.unaConfiteria.entrarMostradorPostre(idEsquiador);
        } else {
            this.unaConfiteria.entrarMostradorComidaRapida(idEsquiador);
        }
        this.unaConfiteria.salirConfiteria();
    }

    //getters and setters
    public void abrirEntradaInstructores() {
        //EL hilo tiempo en determinada hora deja trabajar a los instructores
        this.entradaInstructores = true;
    }

    public void cerrarEntradaInstructores() {
        //El hilo tiempo no deja que actuen los hilos instructores y dar por terminada la jornada laboral de los intructores
        this.entradaInstructores = false;
    }

    public synchronized void abrirEntradaEsquiadores() throws InterruptedException {
        this.entradaEsquiadores = true;
        System.out.println("Se abre el acceso a los esquiadores");
        this.notifyAll();
    }

    public void cerrarEntradaEsquiadores() {
        /*Metodo en el cual se cierra el acceso al centro de esqui, se muestra la cantidad de personas
        que utilizo cada molinete*/
        this.entradaEsquiadores = false;
        System.out.println("Uso de medios de elevacion: \n"
                + "     Primer medio de elevacion: " + this.cantPersonasPrimerElevador + "\n"
                + "     Segundo medio de elevacion: " + this.cantPersonasSegundoElevador + "\n"
                + "     Tercer medio de elevacion: " + this.cantPersonasTercerElevador + "\n"
                + "     Cuarto medio de elevacion: " + this.cantPersonasCuartoElevador);
        this.cantPersonasPrimerElevador = 0;//Se reestablecen los valores a cero
        this.cantPersonasSegundoElevador = 0;
        this.cantPersonasTercerElevador = 0;
        this.cantPersonasCuartoElevador = 0;
        this.cantPrimerClase = 0;
        this.cantSegClase = 0;
        this.cantTercerClase = 0;
        this.cantCuartaClase = 0;
        this.cantQuintaClase = 0;
        primerBarrera.reset();
        segundaBarrera.reset();
        tercerBarrera.reset();
        cuartaBarrera.reset();
        quintaBarrera.reset();
    }

    public boolean getEntradaInstructores() {
        return this.entradaInstructores;
    }

    public boolean getEntradaEsquiadores() {
        return this.entradaEsquiadores;
    }
}
