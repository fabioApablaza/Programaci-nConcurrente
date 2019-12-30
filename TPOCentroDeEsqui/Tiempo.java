/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOCentroDeEsqui;

/**
 *
 * @author Apablaza Fabio FAI - 2039
 */
public class Tiempo implements Runnable {

    private CentroEsqui unCentro;
    private int horaDia;

    public Tiempo(CentroEsqui unC) {
        this.unCentro = unC;
        this.horaDia = 0;
    }

    public int getHoraDia() {
        return this.horaDia;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Thread.sleep(1000);
                this.horaDia++;
                System.out.println("hora: " + horaDia);
                switch (horaDia) {
                    case 9:{
                        System.out.println("Se abre el acceso a los instructores");
                        unCentro.abrirEntradaInstructores();
                        System.out.println("Se abre el acceso a los esquiadores");
                        unCentro.abrirEntradaEsquiadores();                        
                        break;
                    }
                    case 10: {
                        System.out.println("Se abre el acceso a los medios de elevación");
                        this.unCentro.abrirAccesosMedioElevacion();//se abre el acceso a los medios de elevacion
                        break;
                    }
                    case 17: {
                        System.out.println("Se cierra el acceso a los medios de elevación");
                        this.unCentro.cerrarAccesoMedioElevacion();//se cierra el acceso a los medios de elevacion
                        break;
                    }
                    case 15:{
                        System.out.println("Los instructores terminan su jornada laboral");
                        unCentro.cerrarEntradaInstructores();
                        break;
                    }
                    case 22:{
                        System.out.println("Se cierra el complejo");
                        unCentro.cerrarEntradaEsquiadores();
                        break;
                    }
                    case 24: {
                        this.horaDia = 0;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
