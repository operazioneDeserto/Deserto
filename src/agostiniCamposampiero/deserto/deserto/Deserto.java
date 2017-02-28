package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author giaco
 */
public class Deserto extends JFrame implements ActionListener {
    
    private final JFrame pannelloCtrl;
    private final JFrame messaggi;
    private final JTextArea areaMex;
    private ArrayList<CarroCantiere> armata;
    private ArrayList<JButton> opzioni;
    private static int difficulty;
    private final int TIME = 5000;
    private int bullets;


    /**
     * Costruttore non parametrico
     * Il costruttore costruisce la finestra iniziale
     */
    public Deserto() {
        areaMex = new JTextArea();
        messaggi = new JFrame();
        pannelloCtrl = new JFrame();
        frameDeserto();
        frameMessaggi();
        frameControllo();
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
    
    /**
     * Crea l'interfaccia utente
     */
    private void frameDeserto(){
        JOptionPane.showMessageDialog(this, "Benvenuto nel simulatore di battaglia Deserto.","Deserto",JOptionPane.INFORMATION_MESSAGE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deserto");
        getContentPane().setLayout(null);
        JLabel backgroundDeserto = new JLabel();
        backgroundDeserto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agostiniCamposampiero/deserto/resources/sand.png"))); // NOI18N
        getContentPane().add(backgroundDeserto);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int heightGriglia=screenSize.height/10*6, widthGriglia=(int)(screenSize.width/10*7.5);
        setBounds(20,20,widthGriglia, heightGriglia);
        setVisible(true);
        setResizable(false);
        backgroundDeserto.setSize(widthGriglia, heightGriglia); 
        messaggi.setBounds(20,heightGriglia+40,(int)(screenSize.width-40), screenSize.height/10*3);
        pannelloCtrl.setBounds(40+widthGriglia,20,(int)(screenSize.width/10*2)+10, heightGriglia);
    }
    
    private void frameMessaggi(){
        messaggi.setTitle("Messaggi dal simulatore");
        areaMex.setEditable(false);
        DefaultCaret caret = (DefaultCaret)areaMex.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messaggi.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JScrollPane scroll = new JScrollPane (areaMex, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messaggi.add(scroll);
        messaggi.setVisible(true);      
    }
    
    private void frameControllo(){
        pannelloCtrl.setVisible(true);
        pannelloCtrl.setResizable(false);
        pannelloCtrl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pannelloCtrl.setTitle("Pannello");
        JPanel controllo = new JPanel();
        controllo.setLayout(new GridLayout(8,1));
        opzioni = new ArrayList<>();
        for(int i=0; i<8; i++) opzioni.add(new JButton());
        for(JButton tmp : opzioni){
            tmp.addActionListener(this);
            controllo.add(tmp);
        }
        pannelloCtrl.add(controllo);
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        areaMex.append("\n"+"cazzo");
    }

}//Deserto
