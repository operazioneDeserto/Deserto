package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import agostiniCamposampiero.deserto.carri.normali.CarroLineare;
import agostiniCamposampiero.deserto.carri.normali.CarroQuadrato;
import agostiniCamposampiero.deserto.carri.speciali.CarroTalpa;
import agostiniCamposampiero.deserto.pos.Posizione;
import agostiniCamposampiero.deserto.strategy.*;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author giaco
 */
public class Deserto extends JFrame implements ActionListener{
    
    private final JFrame pannelloCtrl;
    private final JFrame messaggi;
    private final JTextArea areaMex;
    private ArrayList<CarroCantiere> armata;
    private final int TIME = 5000;
    private int bullets;
    private JSlider sliderDiff;
    private JButton save;
    private JComboBox tipo;
    private JButton addCarro;
    private JButton start;
    private JButton stop;
    private JButton exit;



    /**
     * Costruttore non parametrico
     * Il costruttore costruisce la finestra iniziale
     */
    public Deserto() {
        areaMex = new JTextArea();
        messaggi = new JFrame();
        pannelloCtrl = new JFrame();
        
        //carri
        armata = new ArrayList <CarroCantiere>();
        Posizione pos = new Posizione (2, 2);
        CarroQuadrato c1 = new CarroQuadrato (pos, 9);
        pos = new Posizione (5, 10);
        CarroLineare c2 = new CarroLineare (pos, 5);
        pos = new Posizione (8, 16);
        CarroTalpa c3 = new CarroTalpa (pos, 4, true);
        pos = new Posizione (13, 20);
        CarroTalpa c4 = new CarroTalpa (pos, 6, false);
        armata.add(c1);
        armata.add(c2);
        armata.add(c3);
        armata.add(c4);
        
        //colpi del cannone
        bullets=150;
        
        frameDeserto();
        blindCannon();
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
        frameMessaggi();
        frameControllo((int)(screenSize.width/10*2)+10,heightGriglia);
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
    
    private void frameControllo(int x, int y){
        pannelloCtrl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pannelloCtrl.setTitle("Pannello");
        //Titolo
        JPanel controllo = new JPanel();
        JLabel title = new JLabel();
        title.setText("Pannello di controllo");
        title.setFont(new Font("Tahoma",Font.PLAIN, 18));
        //Difficoltà
        JLabel subTitleDiff = new JLabel();
        subTitleDiff.setText("Difficoltà del simulatore");
        subTitleDiff.setFont(new Font("Tahoma",Font.PLAIN, 11));
        //Slider difficoltà
        sliderDiff = new JSlider();
        sliderDiff.setMaximum(2);
        sliderDiff.setMinimum(0);
        sliderDiff.setValue(1);
        //Grandezza campo
        JLabel subTitleDim = new JLabel();
        subTitleDim.setText("Dimensioni del campo");
        subTitleDim.setFont(new Font("Tahoma",Font.PLAIN, 11));
        //Campo X
        JTextField coordXCampo = new JTextField();
        coordXCampo.setText("   X   ");
        //Campo Y
        JTextField coordYCampo = new JTextField();
        coordYCampo.setText("   Y   ");
        //Pulsante salva
        save = new JButton("Salva impostazioni");
        save.addActionListener(this);
        //Separatore
        JSeparator separatore = new JSeparator(JSeparator.HORIZONTAL);
        //Aggiunta carro
        JLabel subTitleTank = new JLabel();
        subTitleTank.setText("Inserisci un nuovo carro");
        subTitleTank.setFont(new Font("Tahoma",Font.BOLD, 11));
        //Dimensione carro
        JTextField dimCarro = new JTextField();
        dimCarro.setText("Dimensione");       
        //Tipo carro
        String[] opzioniCombo = {"Carrolineare", "CarroQuadrato", "CarroTalpa"};
        tipo = new JComboBox(opzioniCombo);
        tipo.setSelectedIndex(0);
        //Coordinate carro
        JTextField coordCarroX = new JTextField();
        coordCarroX.setText("Coord X");
        JTextField coordCarroY = new JTextField();
        coordCarroY.setText("Coord Y");
        //Bottone aggiunta carro
        addCarro = new JButton("Aggiungi");
        addCarro.addActionListener(this);
        //Tre bottoni 
        start = new JButton("Start");
        start.addActionListener(this);        
        stop = new JButton("Stop");
        stop.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this); 

        GroupLayout layout = new GroupLayout(controllo);
        controllo.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(separatore, GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(sliderDiff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(title)
                                    .addComponent(subTitleDiff)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(subTitleDim))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(coordXCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(coordYCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(save))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(subTitleTank))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dimCarro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(coordCarroX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(coordCarroY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)                                            
                                        .addComponent(addCarro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exit)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addGap(27, 27, 27)
                .addComponent(subTitleDiff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderDiff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(subTitleDim)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coordXCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordYCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatore, javax.swing.GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subTitleTank)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dimCarro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coordCarroX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordCarroY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCarro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))                
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start)
                    .addComponent(stop)
                    .addComponent(exit))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pannelloCtrl.add(controllo);
        pannelloCtrl.setVisible(true);
        
    }//frameControllo

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
    
    
    public void blindCannon (){
        int height = 15, width = 15;
        String s="";
        int c=0;
        DifficultStrategy strat = new DifficultStrategy (height, width);       
        while (bullets>0 && !armata.isEmpty()){
            Posizione hit=strat.nextHit();
            s = "sabbia";
            while(s.equals("sabbia") && c<= armata.size()-1){
                int result=armata.get(c).fuoco(hit);
                if (result<0) s = "distrutto";
                else if (result == 0) s="colpito";
                if(s.equals("colpito")){
                    playSound(true);
                    if (armata.get(c).distrutto()) armata.remove(c);
                }
                else playSound(false);
                c++;
            }
            
            System.out.println("fuoco in " + hit.toString() + ": " + s);
            c = 0;
            bullets--;
            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        if (bullets > 0) {
            System.out.print("il cannone cieco ti ha distrutto!");
        } else {
            System.out.print("sei sfuggito alla furia del cannone cieco!");
        }
    }


}//Deserto
