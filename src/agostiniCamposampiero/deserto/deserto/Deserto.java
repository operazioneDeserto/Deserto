package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.*;
import agostiniCamposampiero.deserto.carri.normali.*;
import agostiniCamposampiero.deserto.carri.speciali.*;
import agostiniCamposampiero.deserto.pos.Posizione;
import agostiniCamposampiero.deserto.strategy.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author giaco
 */
public class Deserto extends JFrame implements ActionListener{
    
    //Codice
    private final ArrayList<CarroCantiere> armata;
    private final int TIME = 5000;
    private int bullets;
    private Strategy strategy;
    private final static int HEIGHT = 18;
    private final static int WIDTH = 36;
    private Timer timer;
    private boolean stopped;
    private boolean sabotaged;
    
    
    //Grafica
    private final JFrame pannelloCtrl;
    private final JFrame messaggi;
    private final JTextArea areaMex;   
    private JSlider sliderDiff;
    private JButton saveOptions;
    private JComboBox tipo;
    private JTextField dimCarro;
    private JTextField coordCarroX;
    private JTextField coordCarroY;
    private JTextField cordCarroTalpaX;
    private JTextField cordCarroTalpaY;   
    private JButton addCarro;
    private JButton start;
    private JButton stop;
    private JButton exit;
    private JButton covUncov;

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
        sendMex("Avvio simulatore...");
        //carri
        armata = new ArrayList <>();       
        //colpi del cannone
        bullets=350;
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int heightGriglia=screenSize.height/10*6, widthGriglia=(int)(screenSize.width/10*7.5);
        setBounds(20,20,widthGriglia, heightGriglia);
        setVisible(true);
        setResizable(false);
        add(new Griglia());
        messaggi.setBounds(20,heightGriglia+40,(int)(screenSize.width-40), screenSize.height/10*3);
        pannelloCtrl.setBounds(40+widthGriglia,20,(int)(screenSize.width/10*2)+10, heightGriglia);
        sendMex("Modulo Deserto avviato con successo.");
        frameMessaggi();
        sendMex("Modulo Messaggi avviato con successo.");
        frameControllo((int)(screenSize.width/10*2)+10,heightGriglia);
        sendMex("Modulo Controllo avviato con successo.");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sendMex("Simulatore avviato con successo alle ore: "+sdf.format(new Date())+".");
    }//frameDeserto
    
    /**
     * Crea l'interfaccia grafica della finestra messaggi
     */
    private void frameMessaggi(){
        messaggi.setTitle("Messaggi dal simulatore");
        areaMex.setEditable(false);
        areaMex.setCaretPosition(areaMex.getDocument().getLength());
        messaggi.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JScrollPane scroll = new JScrollPane (areaMex);
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
        pannelloCtrl.setResizable(false);
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
        //Separatore
        JSeparator separatore2 = new JSeparator(JSeparator.HORIZONTAL);
        //Carro Talpa sotterrato/dissotterrato
        JLabel coverUncover = new JLabel();
        coverUncover.setText("Copri/Scopri CarroTalpa");
        coverUncover.setFont(new Font("Tahoma",Font.BOLD, 11));
        //Coordinate carro
        cordCarroTalpaX = new JTextField();
        cordCarroTalpaX.setText("CoordX");
        cordCarroTalpaX.setEditable(false);
        cordCarroTalpaY = new JTextField();
        cordCarroTalpaY.setEditable(false);        
        cordCarroTalpaY.setText("CoordY");
        //Pulsante CoverUncover
        covUncov = new JButton("Copri/Scopri");
        covUncov.setEnabled(false);
        covUncov.addActionListener(this);
        //Tre bottoni 
        start = new JButton("Start");
        start.addActionListener(this);       
        start.setEnabled(false);
        stop = new JButton("Pause");
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
                    .addGap(20,20,20)
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
                                .addGap(60, 60, 60)
                                .addComponent(saveOptions)).addComponent(separatore, GroupLayout.Alignment.CENTER)
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
                        .addComponent(separatore2, GroupLayout.Alignment.CENTER)
                        .addComponent(coverUncover)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(20,20,20)
                            .addComponent(cordCarroTalpaX)
                            .addGap(20,20,20)
                            .addComponent(cordCarroTalpaY)
                                .addGap(20,20,20)
                            .addComponent(covUncov))
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
                .addGap(6,6,6)
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
                    
                .addGap(10, 10, 10)
                .addComponent(separatore2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addComponent(coverUncover)
                    .addGap(15,15,15)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cordCarroTalpaX)
                    .addComponent(cordCarroTalpaY)
                    .addComponent(covUncov))
                .addGap(20,20,20)   
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
                switch(sliderDiff.getValue()){
                    case 0:
                        strategy = new EasyStrategy(HEIGHT, WIDTH);
                        sendMex("Difficoltà impostata: Facile.");
                        break;
                    case 1:
                        strategy = new MediumStrategy(HEIGHT, WIDTH);
                        sendMex("Difficoltà impostata: Normale.");
                        break;
                    case 2:
                        strategy = new DifficultStrategy(HEIGHT, WIDTH);
                        sendMex("Difficoltà impostata: Difficile.");
                        break;
                    default:
                        sendMex("Non è stato possibile impostare una difficoltà!");
                        break;              
                }
                sendMex("Dimensioni del campo di battaglia: X="+WIDTH+" Y="+HEIGHT);
                saveOptions.setEnabled(false);
                tipo.setEnabled(true);
                coordCarroX.setEditable(true);
                coordCarroY.setEditable(true);
                dimCarro.setEditable(true);
                addCarro.setEnabled(true);
                start.setEnabled(true);
                repaint();
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
                        if (tankPropertyControl(dim, x, y, tipoCarro)){
                            armata.add(new CarroLineare(new Posizione(x,y),dim));
                            sendMex("Carro creato!");
                        } 
                        else{
                            JOptionPane.showMessageDialog(pannelloCtrl, "Carro non valido!","Errore!",JOptionPane.ERROR_MESSAGE);
                            sendMex("Parametri del carro non accettabili.");
                        }
                        break;
                    case "CarroQuadrato":
                        if (tankPropertyControl(dim, x, y, tipoCarro)){
                            armata.add(new CarroQuadrato(new Posizione(x,y),dim));
                            sendMex("Carro creato!");
                        } 
                        else{
                            JOptionPane.showMessageDialog(pannelloCtrl, "Carro non valido!","Errore!",JOptionPane.ERROR_MESSAGE);
                            sendMex("Parametri del carro non accettabili.");
                        }
                        break;
                    case "CarroTalpa":
                        if (tankPropertyControl(dim, x, y, tipoCarro)){
                            armata.add(new CarroTalpa(new Posizione(x,y),dim,true));
                            sendMex("Carro creato!");
                        } 
                        else{
                            JOptionPane.showMessageDialog(pannelloCtrl, "Carro non valido!","Errore!",JOptionPane.ERROR_MESSAGE);
                            sendMex("Parametri del carro non accettabili.");
                        }
                        break;
                    default:
                        sendMex("Non è stato possibile creare il carro!");
                        break;
                }       
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(pannelloCtrl, "Formato dei campi numerici sbagliato sbagliato!","Errore!",JOptionPane.ERROR_MESSAGE);
            }
            repaint();
        } else if(e.getSource()==covUncov){
            try{
                int x=Integer.parseInt(cordCarroTalpaX.getText()), y=Integer.parseInt(cordCarroTalpaY.getText());
                Posizione pos = new Posizione(x,y);
                CarroCantiere carro=null;
                for(CarroCantiere tmp:armata) if(tmp.present(pos)) carro=tmp;
                if(carro!=null && carro.getClass()==CarroTalpa.class){
                    CarroTalpa tmp = (CarroTalpa) carro;
                    tmp.changeHidden();
                }
                else sendMex("Non esiste un CarroTalpa in queste coordinate!");
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(pannelloCtrl, "Formato delle coordinate del campo sbagliato!","Errore!",JOptionPane.ERROR_MESSAGE);
            }
            repaint();
        } else if(e.getSource()==start){
            //Pulsante per far partire il cannone
            if(stopped){
                timer = new Timer(TIME, this);
                timer.start();
                stopped = false;
                stop.setEnabled(true);
                start.setEnabled(false);
                blindCannon();
            } else {
                tipo.setEnabled(false);
                coordCarroX.setEditable(false);
                coordCarroY.setEditable(false);
                cordCarroTalpaX.setEditable(true);
                cordCarroTalpaY.setEditable(true);
                covUncov.setEnabled(true);
                dimCarro.setEditable(false);
                addCarro.setEnabled(false);
                start.setEnabled(false);
                timer = new Timer(TIME, this);
                stopped = false;
                stop.setEnabled(true);
                timer.start();
                sendMex("Il simulatore è stato avviato!");
                blindCannon();
            }
        } else if(e.getSource()==timer){
            blindCannon();
        }
        else if(e.getSource()==stop){
            stopped = true;
            timer.stop();
            start.setEnabled(true);
            stop.setEnabled(false);
        } else if(e.getSource()==exit) System.exit(0); //Pulsante di uscita
    }//actionPerformed
    
    /**
     * Cannone che spara seguendo la strategia Strategy
     */
    public void blindCannon (){
        if (bullets>0 && !armata.isEmpty()){
            
            timer = new Timer(TIME, this);
            timer.start();
            Posizione hit=strategy.nextHit();
            int result = 1;
            for(CarroCantiere tmp:armata){
                result = tmp.fuoco(hit);
                strategy.hitFeedback(result);
                if(result<0){
                    playSound(true);
                    sendMex("Fuoco in: "+hit.toString()+" . Il carro è stato distrutto!");
                    armata.remove(tmp);
                    break;
                }
                else if (result==0){
                    playSound(true);
                    sendMex("Fuoco in: "+hit.toString()+" . Il carro è stato colpito!");
                    break;
                } 
            }
            if(result>0){
                playSound(false);
                sendMex("Fuoco in: "+hit.toString()+" . Il colpo non è andato a segno!");
            }
            double attack=0;
            for (CarroCantiere tmp:armata) attack+=tmp.sapperAttack();
            if(attack>=(Math.random()*100)){
                timer = new Timer(60000, this);
                timer.start();
                bullets-=12;
                sendMex("I guastatori hanno sabotato con successo il cannone!");
            }
            bullets--;
        }
        else{
            if(bullets==0) sendMex("I carri sono resistiti al bombardamento!");
            else sendMex("Il cannone ha distrutto tutti i carri!");
            timer.stop();
            stop.setEnabled(false);
        }
    }//blindCannon
    
    /**
     * Controlla le proprietà inserite al carro
     * @param dim dimensione del carro
     * @param x cordinata x della posizione del carro nel campo
     * @param y coordinata y della posizione del carro nel campo
     * @param tipoCarro
     * @return 
    */
    private boolean tankPropertyControl (int dim, int x, int y, String tipoCarro){
        if (x<=0 || x>WIDTH || y<=0 || y>HEIGHT) return false;
        double size = (double) dim;
        if (tipoCarro.equals("CarroQuadrato")){
            if (dim%Math.sqrt(size)!=0) return false;
            if (((int)Math.sqrt(size) + x-1)>WIDTH || ((int)Math.sqrt(size) + y-1)>HEIGHT || dim <=0) return false;
            Posizione pos = new Posizione(x,y);
            int lato=(int)Math.sqrt(dim);
            for (int i = 0; i <lato; i++) {
                for (int j = 0; j <lato; j++) {
                    for(CarroCantiere tmp:armata) if(tmp.present(pos)) return false;
                    pos = new Posizione(++x,y);
                }
                pos= new Posizione(x-=lato,++y);
            }
            return true;
        }
        else if ((dim + x-1)>WIDTH || dim<=0) return false;
        Posizione pos = new Posizione(x,y);
        for(int i=0; i<dim;i++){
            for(CarroCantiere tmp:armata) if(tmp.present(pos)) return false;
            pos=new Posizione(++x,y);
        }
        return true;
    }//tankPropertyControl
    
    /**
     * Metodo per scrivere messaggi all'interno dell'apposito spazio
     * @param mex   messaggio da scrivere
     */
    private void sendMex(String mex){
        areaMex.append("  "+mex+"\n");
    }//sendMex
    
    
    class Griglia extends JPanel {
  
        private final static int HEIGHT = 18;
        private final static int WIDTH = 36;

        /**
         * Disegna la grafica della griglia
         * @param g grafica
         */
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x=(int)(screenSize.width/10*7.5-70)/WIDTH, y=(screenSize.height/10*6-70)/HEIGHT;
            try {                
                BufferedImage image = ImageIO.read(new File("src/agostiniCamposampiero/deserto/resources/sand.png"));
                g2.drawImage(image,40,25,WIDTH*x,HEIGHT*y,this);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            for(int i=1; i<=WIDTH; i++){
                if(i<10) g2.drawString("0"+i,18+i*x,20);
                else g2.drawString(""+i,18+i*x,20);
            }
            for(int i=1; i<=HEIGHT; i++){
                if(i<10) g2.drawString("0"+i,20,20+i*y);
                else g2.drawString(""+i,20,20+i*y);
            }
            for(CarroCantiere tmp:armata) tmp.draw(g2, x, y);
            g2.setColor(Color.BLACK);
            for(int i=0; i<WIDTH; i++){
                for(int j=0;j<HEIGHT; j++){
                    Rectangle rect = new Rectangle(40+(i*x),25+(j*y),x,y);
                    g2.draw(rect);
                }
            }          
        }//paintComponent
        
    }//Griglia

}//Deserto