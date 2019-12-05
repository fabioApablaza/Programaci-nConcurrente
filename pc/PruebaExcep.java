package pc;

import java.util.Random;

public class PruebaExcep {

    public static void edad() {
        int edad;
        try {
            System.out.print("Ingrese su edad: ");
            edad = TecladoIn.readLineInt();
            if (edad < 18) {
                throw new Exception("Es menor de edad");
            }
            edad = TecladoIn.readLineInt();

        } catch (Exception e) {
            //System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void ruleta() {
        int aleatorio = (int) Math.floor(Math.random() * 9);
        int b;
        try {
            System.out.print("Ingrese un numero:");
            b = TecladoIn.readLineInt();
            if (aleatorio != b) {
                throw new Exception("No es el mismo numero");
            }
            System.out.println("Es el mismo numero");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void arreglo() {
        
        int[] a = new int[4];
        try {
            for (int i = 0; i < a.length; i++) {
                System.out.print("Ingrese el numero para la posicion "+(i+1)+" de la coleccion:");
                a[i]=TecladoIn.readLineInt();
            }
            int i=0;
            while(i<6){
                System.out.print(a[i]+" ");
                i++;
            }
            System.out.println("Finalizado");
        } catch (Exception e) {
            System.err.println("Se fue del arreglo");
        }

    }

    public static void main(String[] args) {
        //edad();
        //ruleta();
        arreglo();
    }
}
