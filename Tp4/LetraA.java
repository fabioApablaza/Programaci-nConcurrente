package Tp4;

public class LetraA implements Runnable {

    private char letra;
    private int id;
    private Turno unTurno;

    public LetraA(char a, Turno unTurno, int id) {
        this.letra = a;
        this.unTurno = unTurno;
        this.id = id;
    }

    @Override
    public void run() {
        int i = 0;
        while(true){
        try {
            while (!unTurno.esTurno(this.id)) {
                while (i < this.id) {
                    System.out.print(this.letra);
                    i++;
                }
            }
        } catch (Exception ex) { }

    }}

}
