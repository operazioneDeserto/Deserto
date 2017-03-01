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
public class CarroQuadrato extends CarroCantiere {

    private final ArrayList<Pezzo> tank;

    /**
     * Costruttore parametrico
     *
     * @param pos posizione del carro
     * @param num numero di pezzi del carro
     */
    public CarroQuadrato(Posizione pos, int num) {
        super(pos, num);
        tank = new ArrayList<>();
        for (int i = 0; i < num; i++) tank.add(new Pezzo(i));
    }//Costruttore

    /**
     * Restituisce lo stato del carro
     *
     * @return <0 se il carro è distrutto, 0 se danneggiato e >0 se integro
     */
    @Override
    public int stato() {
        if (distrutto()) return -1;
        for (Pezzo tmp : tank) if (!tmp.stato()) return 0;
        return 1;
    }//stato

    /**
     * Verifica se il carro è stato distrutto
     *
     * @return ture se il carro è distrutto, false altrimenti
     */
    @Override
    public boolean distrutto() {
        return (tank.isEmpty());
    }//distrutto

    /**
     * Colpisce il carro
     *
     * @param pos posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito,
     * >0 se il carro non e' stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        Posizione tmp = getPos();
        int x = tmp.getX(), y = tmp.getY();
        int lato =(int) Math.sqrt(getNum());
        int righe = lato -(getNum()-tank.size())/lato;
        for (int i = 0; i < righe; i++) {
            int a = tank.size()-i*lato>lato?lato:tank.size()-i*lato;
            for (int j = 0; j < a; j++) {
                if (pos.equals(tmp)) {
                    if (tank.get(i*3+j).hit()) tank.remove(i*3+j);
                    if (distrutto()) return -1;
                    else return 0;
                }
                if(j==lato-1){
                    x-=lato-1;
                    tmp = new Posizione(x, ++y);
                } else tmp = new Posizione(++x, y);
            }
        }
        return 1;
    }//fuoco
    
    /**
     * Attacco dei guastatori
     * @return percentuale di riuscita dell'assalto di una squadra di guastatori
     */
    @Override
    public double sapperAttack (){
        return 0;
    }
    
    /**
     * Restituisce una stringa significativa sull'oggetto
     *
     * @return stringa significativa
     */
    @Override
    public String toString() {
        String ris = "\n";
        for (Pezzo tmp : tank) ris += tmp.toString();
        return "CarroQuadrato attualmente composto da " + tank.size() + " pezzi." + ris + super.toString();
    }//toString

    /**
     * Disegna il carro nella griglia
     * @param g2 grafica
     * @param dimX  dimensione quadretti X
     * @param dimY  dimensione quadretti Y
     */
    @Override
    public void draw(Graphics2D g2, int dimX, int dimY) {
        int x=getPos().getX(), y=getPos().getY(), lato=(int)Math.sqrt(getNum());
        int righe = lato -(getNum()-tank.size())/lato;
        for (int i = 0; i <righe; i++) {
            int a = tank.size()-i*lato>lato?lato:tank.size()-i*lato;
            for (int j = 0; j <a; j++) {
                g2.setColor(Color.GREEN);
                Rectangle rect = new Rectangle(40+(x+j-1)*dimX,25+dimY*(y+i-1),dimX,dimY);
                g2.draw(rect);
                g2.fill(rect);
                g2.setColor(Color.BLACK);
                g2.drawString(""+tank.get((i*lato+j)).getNum(),44+(x+j-1)*dimX,22+dimY*(y+i));
            }
        }
    }//draw
    
   /**
     * Verifica se una posizione è presente all'interno del carro
     * @param pos   posizione
     * @return true se è presente, false altrimenti
     */
    @Override
    public boolean present(Posizione pos) {
        Posizione tmp = getPos();
        int x=tmp.getX(), y=tmp.getY(), lato=(int)Math.sqrt(getNum());
        int righe = lato -(getNum()-tank.size())/lato;
        for (int i = 0; i <righe; i++) {
            int a = tank.size()-i*lato>lato?lato:tank.size()-i*lato;
            for (int j = 0; j <a; j++) {
                if(tmp.equals(pos)) return true;
                tmp = new Posizione(++x,y);
            }
        }
        return false;
    }//present

}//CarroQuadrato
