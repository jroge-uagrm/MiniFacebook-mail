
package MiniFB;

import Comunicaciones.Analizador;
import javax.mail.MessagingException;

/**
 *
 * @author Jhafeth
 */
public class Servicio extends Thread {

    private boolean isRunning;

    public Servicio() {
        this.isRunning = false;
    }

    public void iniciarAnalizador() {
        System.out.println("-- ANALIZADOR INCIADO --");
        this.isRunning = true;
        this.start();
    }

    public void deteneraAnalizador() {
        this.isRunning = false;
        this.stop();
        System.out.println("-- ANALIZADOR DETENIDO --");
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        Analizador a = new Analizador();
        a.setStat();
        int statAnterior = a.getStat();
        while (true) {
            try {
                Thread.sleep(5000);
                if (statAnterior < a.getStat()) {//nuevo correo?
                    statAnterior++;
                    a.setStat(statAnterior);
                    a.setFrom();
                    a.separar();
                    a.analizar();
                }
                a.setStat();
            } catch (InterruptedException | MessagingException e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
