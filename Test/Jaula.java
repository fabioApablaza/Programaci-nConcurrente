/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */
public class Jaula {
    private int capacidad;
	private Semaphore sem1 = new Semaphore(1,true);
	private Semaphore sem2 = new Semaphore(1,true);
	private Semaphore sem3 = new Semaphore(1,true);
	
	/*constructor de Jaula*/
	public Jaula(int cap) {
		this.capacidad=cap;
	}
	
	/*llamado a los metodos de actividades*/
	public void actividad(Hamster hams) {
		
		System.out.println("hamster: " + hams.getNombre() + " esta paseando en la jaula");
		hams.disminuirEstados();
	
		try {
		
		sem1.acquire();
			if(hams.getHambre()<30) {
				System.out.println("hamster: " + hams.getNombre() + " esta comiendo\n");
				System.out.println("Ya que su barra de hambre esta en: " + hams.getHambre() + " y eso es bajo\n");
				this.comer(hams);
			}
		sem1.release();

		sem2.acquire();
		if(hams.getAburrimiento()<30) {
				System.out.println("hamster: " + hams.getNombre() + " se esta hamacando\n");
				System.out.println("Ya que su barra de aburrimiento esta en: " + hams.getAburrimiento() + " y eso es bajo\n");
				this.hamacarse(hams);
			}
		sem2.release();

		sem3.acquire();
			if(hams.getEnergia()<30) {
				System.out.println("hamster: " + hams.getNombre() + " esta durmiendo\n");
				System.out.println("Ya que su barra de energia esta en: " + hams.getEnergia() + " y eso es bajo\n");
				this.dormir(hams);
			}
		sem3.release();
		
		}catch(InterruptedException e) {
			System.err.println("Ocurrio un error inesperado");
			e.printStackTrace();
		}
		
		
	}
	
	/*modulos de las actividades realizadas por los hamsters */
	public void hamacarse(Hamster hams) throws InterruptedException {
//		sem.acquire();
		Thread.sleep(2000);
		hams.setAburrimiento(100);
		
	}
	public void comer(Hamster hams) throws InterruptedException {
//		sem.acquire();
		Thread.sleep(5000);
		hams.setHambre(100);
	}
	public void dormir(Hamster hams) throws InterruptedException {
//		sem.acquire();
		Thread.sleep(10000);
		hams.setEnergia(100);
	}
	
	
	
	/*metodos de observacion y modificadores*/
	
	public int getCapacidad() {
		return this.capacidad;
	}
	
	public void setCapacidad(int capacNueva) {
		this.capacidad = capacNueva;
	}
}
