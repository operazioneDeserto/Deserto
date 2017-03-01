package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import agostiniCamposampiero.deserto.carri.normali.*;
import agostiniCamposampiero.deserto.carri.speciali.*;
import agostiniCamposampiero.deserto.pos.Posizione;
import agostiniCamposampiero.deserto.strategy.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    //Codice
    private final ArrayList<CarroCantiere> armata;
    private final int TIME = 5000;
    private int bullets;
    private int heightDesert;
    private int widthDesert;
    private Strategy strategy;
    private boolean[][] state;
    //Grafica
    private final JFrame pannelloCtrl;
    private final JFrame messaggi;
    private final JTextArea areaMex;   
    private JSlider sliderDiff;
    private JButton saveOptions;
    private JComboBox tipo;
    private JTextField coordXCampo;
    private JTextField coordYCampo;
    private JTextField dimCarro;
    private JTextField coordCarroX;
    private JTextField coordCarroY;
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
        sendMex("**********************************************\n"
               +"         Simulatore di battaglia Deserto         \n"
               +"  **********************************************");
        sendMex("Avviamento simulatore...");
        //carri
        armata = new ArrayList <>();
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
     * Crea l'interfaccia utente del frame Deserto e invoca la creazione di quella deglia altri frame
     */
    private void frameDeserto(){
        JOptionPane.showMessageDialog(this, "Benvenuto nel simulatore di battaglia Deserto.","Deserto",JOptionPane.INFORMATION_MESSAGE);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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
        sendMex("Modulo Deserto avviato con successo...");
        frameMessaggi();
        sendMex("Modulo Messaggi avviato con successo...");
        frameControllo((int)(screenSize.width/10*2)+10,heightGriglia);
        sendMex("Modulo Controllo avviato con successo...");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sendMex("Simulatore avviato con successo alle ore: "+sdf.format(new Date())+".");
    }//frameDeserto
    
    /**
     * Crea l'interfaccia grafica della finestra messaggi
     */
    private void frameMessaggi(){
        messaggi.setTitle("Messaggi dal simulatore");
        areaMex.setEditable(false);
        DefaultCaret caret = (DefaultCaret)areaMex.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messaggi.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JScrollPane scroll = new JScrollPane (areaMex, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messaggi.add(scroll);
        messaggi.setVisible(true);      
    }//frameMessaggi
    
    /**
     * Crea l'interfaccia grafica del frame di controllo
     * @param x ascissa frame
     * @param y ordinata frame
     */
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
        //Etichette difficoltà
        JLabel labelDifficulty1 = new JLabel();
        labelDifficulty1.setText("Facile");
        labelDifficulty1.setFont(new Font("Tahoma",Font.ITALIC, 10));
        JLabel labelDifficulty2 = new JLabel();
        labelDifficulty2.setText("Normale");
        labelDifficulty2.setFont(new Font("Tahoma",Font.ITALIC, 10));
        JLabel labelDifficulty3 = new JLabel();
        labelDifficulty3.setText("Difficile");
        labelDifficulty3.setFont(new Font("Tahoma",Font.ITALIC, 10));
        //Grandezza campo
        JLabel subTitleDim = new JLabel();
        subTitleDim.setText("Dimensioni del campo");
        subTitleDim.setFont(new Font("Tahoma",Font.PLAIN, 11));
        //Campo X
        coordXCampo = new JTextField();
        coordXCampo.setText("CoordX");
        //Campo Y
        coordYCampo = new JTextField();
        coordYCampo.setText("CoordY");
        //Pulsante salva
        saveOptions = new JButton("Salva impostazioni");
        saveOptions.addActionListener(this);
        //Separatore
        JSeparator separatore = new JSeparator(JSeparator.HORIZONTAL);
        //Aggiunta carro
        JLabel subTitleTank = new JLabel();
        subTitleTank.setText("Inserisci un nuovo carro");
        subTitleTank.setFont(new Font("Tahoma",Font.BOLD, 11));
        //Dimensione carro
        dimCarro = new JTextField();
        dimCarro.setText("Dimensione");       
        dimCarro.setEditable(false);
        //Tipo carro
        String[] opzioniCombo = {"CarroLineare", "CarroQuadrato", "CarroTalpa"};
        tipo = new JComboBox(opzioniCombo);
        tipo.setEnabled(false);
        tipo.setSelectedIndex(0);
        //Coordinate carro
        coordCarroX = new JTextField();
        coordCarroX.setText("CoordX");
        coordCarroX.setEditable(false);
        coordCarroY = new JTextField();
        coordCarroY.setEditable(false);        
        coordCarroY.setText("CoordY");
        //Bottone aggiunta carro
        addCarro = new JButton("Aggiungi");
        addCarro.addActionListener(this);
        addCarro.setEnabled(false);
        //Tre bottoni 
        start = new JButton("Start");
        start.addActionListener(this);       
        start.setEnabled(false);
        stop = new JButton("Stop");
        stop.addActionListener(this);
        stop.setEnabled(false);
        exit = new JButton("Exit");
        exit.addActionListener(this); 
        //Layout
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
                                    .addGap(27, 27, 27)
                                    .addComponent(labelDifficulty1)
                                    .addGap(55, 55, 55)
                                    .addComponent(labelDifficulty2)
                                    .addGap(55, 55, 55)
                                    .addComponent(labelDifficulty3))
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
                                .addComponent(saveOptions))
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
                .addGap(6,6,6)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)    
                    .addComponent(labelDifficulty1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDifficulty2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDifficulty3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(subTitleDim)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coordXCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordYCampo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(saveOptions)
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

    /**
     * Gestisce gli eventi generati dai pulsanti
     * @param e evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==saveOptions){
            //Pulsante salvataggio coordinate e difficoltà
            try{
                heightDesert = Integer.parseInt(coordXCampo.getText());
                widthDesert = Integer.parseInt(coordYCampo.getText());
                if(heightDesert>35 || heightDesert<=0 || widthDesert>50 || widthDesert<=0){
                    sendMex("La dimensione del campo deve essere compresa tra 1 e 35 per le X e tra 1 e 50 per le Y!");
                    throw new NumberFormatException();
                }
                switch(sliderDiff.getValue()){
                    case 0:
                        strategy = new EasyStrategy(heightDesert, widthDesert);
                        sendMex("Difficoltà impostata: Facile.");
                        break;
                    case 1:
                        strategy = new MediumStrategy(heightDesert, widthDesert);
                        sendMex("Difficoltà impostata: Normale.");
                        break;
                    case 2:
                        strategy = new EasyStrategy(heightDesert, widthDesert);
                        sendMex("Difficoltà impostata: Difficile.");
                        break;
                    default:
                        sendMex("Non è stato possibile impostare una difficoltà!");
                        break;              
                }
                sendMex("Dimensioni del campo di battaglia: X="+widthDesert+" Y="+heightDesert);
                coordXCampo.setEditable(false);
                coordYCampo.setEditable(false);
                saveOptions.setEnabled(false);
                tipo.setEnabled(true);
                coordCarroX.setEditable(true);
                coordCarroY.setEditable(true);
                dimCarro.setEditable(true);
                addCarro.setEnabled(true);
                start.setEnabled(true);
                stop.setEnabled(true);
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(pannelloCtrl, "Formato delle coordinate del campo sbagliato!","Errore!",JOptionPane.ERROR_MESSAGE);
            }
            
        } else if(e.getSource()==addCarro){
            //Pulsante per aggiunta dei carri all'armata
            try{
                int dim = Integer.parseInt(dimCarro.getText()), x= Integer.parseInt(coordCarroX.getText()), y= Integer.parseInt(coordCarroY.getText());
                String tipoCarro = (String) tipo.getSelectedItem();
                switch (tipoCarro) {
                    case "CarroLineare":
                        armata.add(new CarroLineare(new Posizione(x,y),dim));
                        sendMex("Carro creato!");
                        break;
                    case "CarroQuadrato":
                        armata.add(new CarroQuadrato(new Posizione(x,y),dim));
                        sendMex("Carro creato!");
                        break;
                    case "CarroTalpa":
                        armata.add(new CarroTalpa(new Posizione(x-1,y), dim, false));
                        sendMex("Carro creato!");
                        break;
                    default:
                        sendMex("Non è stato possibile creare il carro!");
                        break;
                }       
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(pannelloCtrl, "Formato dei campi numerici sbagliato sbagliato!","Errore!",JOptionPane.ERROR_MESSAGE);
            }
        } else if(e.getSource()==start){
            //Pulsante per far partire il cannone
            tipo.setEnabled(false);
            coordCarroX.setEditable(false);
            coordCarroY.setEditable(false);
            dimCarro.setEditable(false);
            addCarro.setEnabled(false);
            start.setEnabled(false);
            sendMex("Il simulatore è stato avviato!");
            //blindCannon();
        } else if(e.getSource()==stop){
            //Pulsante per stop
        } else if(e.getSource()==exit) System.exit(0); //Pulsante di uscita
    }//actionPerformed
     
    
    
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
    
    /**
     * Metodo per scrivere messaggi all'interno dell'apposito spazio
     * @param mex   messaggio da scrivere
     */
    private void sendMex(String mex){
        areaMex.append("  "+mex+"\n");
    }//sendMex

}//Deserto
