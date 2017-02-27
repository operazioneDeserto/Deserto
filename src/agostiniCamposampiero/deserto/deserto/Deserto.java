package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import agostiniCamposampiero.deserto.grafica.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author giaco
 */
public class Deserto {
    
    private ArrayList<CarroCantiere> armata;
    private static int difficulty;
    private final int TIME = 5000;
    private int bullets;

    /**
     * Costruttore non parametrico
     * Il costruttore costruisce la finestra iniziale
     */
    public Deserto() {
        Start start = new Start();
        start.setVisible(true);
        start.setSize(295, 280);
    }//Costruttore

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Deserto();
    }//main
    
    /**
     * Avvia il simulatore di battaglia
     */
    private static void startSimulator(){
        Grafica x = new Grafica();
        

        playSound(true);        
    }//starSimulator
    
    /**
     * Riproduce un effetto sonoro specificato
     * @param type identificatore dell'effetto sonoro da riprodurre
     */
    private static void playSound(boolean type) {
        String url = type?"src/agostiniCamposampiero/deserto/resources/hit.wav":"src/agostiniCamposampiero/deserto/resources/missed.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Errore nel riprodurre l'effetto audio!");
        }
    }//playSound

    /**
     * Setta la difficoltà ed avvia il simulatore
     * @param x difficoltà
     */
    public static void setDifficulty(int x){
        difficulty = x;
        startSimulator();
    }//setDifficulty

}//Deserto
