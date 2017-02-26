package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.grafica.*;
import agostiniCamposampiero.deserto.carri.normali.*;
import agostiniCamposampiero.deserto.carri.speciali.CarroTalpa;
import agostiniCamposampiero.deserto.pos.Posizione;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 *
 * @author giaco
 */
public class Deserto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        CarroTalpa x = new CarroTalpa(new Posizione(1,2), 4, true);
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        x.scoperto();
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        CarroLineare y = new CarroLineare(new Posizione(1,2), 4);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
        CarroQuadrato z = new CarroQuadrato(new Posizione(1,2), 4);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        JFrame my = new Start();
        my.setSize(300,245);
        my.setVisible(true);*/
        playSound(true);
 
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
        playSound(false);
        i = in.nextInt();
    }

    private static void playSound(boolean type) {
        String url;
        if (type) url = "src/agostiniCamposampiero/deserto/resources/hit.wav";
        else url = "src/agostiniCamposampiero/deserto/resources/missed.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Errore nel riprodurre l'effetto audio!");
        }
    }
}
