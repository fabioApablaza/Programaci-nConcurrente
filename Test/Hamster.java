/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author user
 */

    public class Hamster implements Runnable {
/*Zona de variables*/
	
	private Jaula cautiverio;
	private String nombre, raza;
	private int hambre , energia , aburrimiento;
	
	/*Constructor de Hamster*/
	
	public Hamster(String nomb,String raz,Jaula prision) {
		this.nombre = nomb;
		this.raza = raz;
		this.cautiverio=prision;
		this.hambre = 100;
		this.energia=100;
		this.aburrimiento=100;
	}
	@Override
	public void run() {
		
		while(true) {
			System.out.println("el hamster: " + this.getNombre() + " su estado es el siguiente\n" + this.getEstados() + "\n");
			cautiverio.actividad(this);
			
		}
	}
	
	public void disminuirEstados() {
		long time = System.currentTimeMillis();
		
		while(this.hambre>30 ) {
			if(time >30) {
				this.hambre = this.hambre - (Aleatorio.intAleatorio(10, 30));
				if(this.hambre<0)
					this.hambre=0;
				this.aburrimiento = this.aburrimiento - (Aleatorio.intAleatorio(1, 30));
				if(this.aburrimiento<0)
					this.aburrimiento=0;
				this.energia = this.energia - (Aleatorio.intAleatorio(10, 30));
				if(this.energia<0)
					this.energia=0;
		}
		
		}
	}
	
	/*Metodos de obsevacion y modificacion*/
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}

	public int getHambre() {
		return hambre;
	}

	public void setHambre(int hambre) {
		this.hambre = hambre;
	}

	public int getEnergia() {
		return energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public int getAburrimiento() {
		return aburrimiento;
	}

	public void setAburrimiento(int aburrimiento) {
		this.aburrimiento = aburrimiento;
	}
	
	public String getEstados() {
		return "Hambre: " + this.hambre + "\n" +
				"Energia: " + this.energia + "\n" +
				"Aburrimiento: " + this.aburrimiento;
	}
	
}
