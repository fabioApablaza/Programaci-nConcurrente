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

    public class Escritor implements Runnable {

	
	private char letter;
	private Turno turn;
	private int numero;
	private double tiempo;

	public Escritor(char letra, int num) {
		this.letter = letra;
		this.turn = new Turno();
		this.numero = num;
		this.tiempo = System.currentTimeMillis();
	}
        @Override
	public void run() {
		int veces=3, cont = 0, cant = 0;

		String s = "";
				
		try {
			while (cont < (veces*3)) {
				if (this.numero == this.turn.getTurno()) {
					while (cant <= this.numero) {
						s += this.getLetter();
						cant++;
					}
					System.out.print(s);
				}
				s = "";
				cont++;
				cant = 0;
			}
		} catch (Exception e) {
		}

	}

	public Turno getTurn() {
		return turn;
	}

	public void setTurn(Turno turn) {
		this.turn = turn;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}
}
