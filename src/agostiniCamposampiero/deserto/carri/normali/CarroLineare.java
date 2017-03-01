package agostiniCamposampiero.deserto.carri.normali;

import agostiniCamposampiero.deserto.carri.CarroCantiere;
import agostiniCamposampiero.deserto.pos.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroLineare extends CarroCantiere{
    
    private final ArrayList<Pezzo> tank;
       
    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero di pezzi del carro
     */
    public CarroLineare(Posizione pos, int num){
        super(pos,num);
        tank = new ArrayList<>();
        for(int i=0; i<num; i++) tank.add(new Pezzo(i));
    }//Costruttore

    /**
     * Restituisce lo stato del carro
     * @return <0 se il carro è distrutto, 0 se danneggiato e >0 se integro
     */
    @Override
    public int stato() {
        if(distrutto()) return -1;
        for(Pezzo tmp:tank) if(!tmp.stato()) return 0;
        return 1;
    }//stato

    /**
     * Verifica se il carro è stato distrutto
     * @return ture se il carro è distrutto, false altrimenti
     */
    @Override
    public boolean distrutto() {
        return (tank.isEmpty());           
    }//distrutto

    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non + stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        return fuoco(pos, false);
    }//fuoco
        
    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @param hidden    true se il carro è nascosto, false altrimenti
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non è stato colpito
     */
    public int fuoco(Posizione pos, boolean hidden) {    
        Posizione tmp = getPos();
        int x=tmp.getX(), y=tmp.getY();
        for(int i=0; i<tank.size(); i++){
            if(pos.equals(tmp)){
                System.out.println("Posizione pezzo colpito: "+tmp.toString());
                if(tank.get(i).hit(hidden)) tank.remove(i);
                if(distrutto()) return -1;
                else return 0;
            }
            System.out.println(tmp);
            tmp = new Posizione(++x,y);
            System.out.println("dopo:"+tmp.toString());
        }
        return 1;
    }//fuoco
    
    /**
     * Attacco dei guastatori
     * @return percentuale di riuscita dell'assalto di una squadra di guastatori
     */
    @Override
    public int sapperAttack (){
        return sapperAttack (false);
    }
    
    /**
     * Attacco dei guastatori
     * @param hidden    valore booleano che indica se il carro è nascosto o no
     * @return percentuale di riuscita dell'assalto di una squadra di guastatori
     */
    public int sapperAttack (boolean hidden){
        if (!hidden) return tank.size()*2;
        return 0;
    }
    
    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        String ris = "\n";
        for(Pezzo tmp:tank) ris+=tmp.toString();
        return "CarroLineare attualmente composto da "+tank.size()+" pezzi." +ris + super.toString();
    }//toString

    /**
     * Disegna il carro nella griglia
     * @param g2 grafica
     * @param dimX  dimensione quadretti X
     * @param dimY  dimensione quadretti Y
     */
    @Override
    public void draw(Graphics2D g2, int dimX, int dimY){
        draw(g2, dimX, dimY, false, false);
    }//draw
    
    /**
     * Disegna la grafica di CarroLineare e CarroTalpa
     * @param g2    grafica
     * @param dimX  dimensione X cella griglia
     * @param dimY  dimensione Y cella griglia
     * @param talpa true se il carro è talpa, false altrimenti
     * @param hidden    true se il carro è nascosto, false altrimenti
     */
    public void draw(Graphics2D g2, int dimX, int dimY, boolean talpa, boolean hidden){
        int x=getPos().getX(), y=getPos().getY();
        for(int i=x; i<tank.size(); i++){
            g2.setColor(Color.BLACK);
            g2.drawString(""+tank.get(i).getNum(),x*dimX+3,y*dimY);
            if(talpa && hidden) g2.setColor(Color.GRAY);
            else if(talpa && !hidden) g2.setColor(Color.BLUE);
            else g2.setColor(Color.RED);
            Rectangle rect = new Rectangle(40+(i*dimX),25+dimY*y,dimX,dimY);
            g2.draw(rect);
            g2.fill(rect);
        }        
    }
    
}//CarroLineare
