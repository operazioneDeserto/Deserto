package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import agostiniCamposampiero.deserto.grafica.*;
import agostiniCamposampiero.deserto.carri.normali.*;
import agostiniCamposampiero.deserto.carri.speciali.*;
import agostiniCamposampiero.deserto.pos.Posizione;
import agostiniCamposampiero.deserto.strategy.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author giaco
 */
public class Deserto {
    
    private ArrayList<CarroCantiere> armata;
    private static int difficulty;

    /**
     * Costruttore non parametrico
     * Il costruttore costruisce la finestra iniziale
     */
    public Deserto() {
        Start start = new Start();
        start.setVisible(true);
        start.setSize(295, 280);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Deserto();
    }//main
    
    /**
     * Avvia il simulatore di battaglia
     */
    private static void starSimulator(){
        Grafica x = new Grafica();
        x.setVisible(true);
    }//starSimulator
    
    /**
     * Riproduce un effetto sonoro specificato
     * @param type identificatore dell'effetto sonoro da riprodurre
     */
    private void playSound(boolean type) {
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
        starSimulator();
    }//setDifficulty

}//Deserto
