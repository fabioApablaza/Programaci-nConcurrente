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
public class Test {
    public static void main(String[] args) {

		Escritor letterA = new Escritor('A', 0);
		Thread t1 = new Thread(letterA);
		Escritor letterB = new Escritor('B', 1);
		Thread t2 = new Thread(letterB);
		Escritor letterC = new Escritor('C', 2);
		Thread t3 = new Thread(letterC);

		try {
			t1.start();
			t2.start();
			t3.start();
			t1.join();
			t2.join();
			t3.join();

		} catch (InterruptedException e) {

		}
	}
}
