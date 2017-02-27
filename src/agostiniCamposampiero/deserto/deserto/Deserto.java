package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author giaco
 */
public class Deserto extends JFrame {
    
    private final JFrame pannelloCtrl;
    private final JFrame messaggi;
    private final JLabel backgroundDeserto;
    private ArrayList<CarroCantiere> armata;
    private static int difficulty;
    private final int TIME = 5000;
    private int bullets;

    /**
     * Costruttore non parametrico
     * Il costruttore costruisce la finestra iniziale
     */
    public Deserto() {
        backgroundDeserto = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deserto");
        getContentPane().setLayout(null);
        backgroundDeserto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agostiniCamposampiero/deserto/resources/sand.png"))); // NOI18N
        getContentPane().add(backgroundDeserto);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int heightGriglia=screenSize.height/10*6, widthGriglia=(int)(screenSize.width/10*7.5);
        setBounds(20,20,widthGriglia, heightGriglia);
        setVisible(true);
        messaggi = new JFrame();
        pannelloCtrl = new JFrame();
        messaggi.setBounds(20,heightGriglia+20,(int)(screenSize.width-40), screenSize.height/10*3);
        messaggi.setVisible(true);
        pannelloCtrl.setBounds(40+widthGriglia,20,(int)(screenSize.width/10*2)+10, heightGriglia);
        pannelloCtrl.setVisible(true);
        backgroundDeserto.setSize(widthGriglia, heightGriglia);
    }//Costruttore

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Deserto();
    }//main
    
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

}//Deserto
