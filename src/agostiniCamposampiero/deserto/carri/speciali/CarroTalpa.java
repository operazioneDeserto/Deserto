package agostiniCamposampiero.deserto.carri.speciali;

import agostiniCamposampiero.deserto.carri.normali.CarroLineare;
import agostiniCamposampiero.deserto.pos.Posizione;
import java.awt.Graphics2D;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroTalpa extends CarroLineare{
    
    private boolean hidden;

    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero pezzi del carro
     * @param hidden    true se il carro è nascosto, false altrimenti
     */
    public CarroTalpa(Posizione pos, int num, boolean hidden) {
        super(pos, num);
        this.hidden = hidden;
    }//Costruttore
    
    /**
     * Scopre il carro
     */
    public void scoperto(){
        hidden = false;    
    }//scoperto
    
    /**
     * Nasconde il carro
     */
    public void nascosto(){
        hidden = true;    
    }//nascosto
    
    /**
     * Attacco dei guastatori
     * @return percentuale di riuscita dell'assalto di una squadra di guastatori
     */
    @Override
    public int sapperAttack (){
        return super.sapperAttack(hidden);
    }
    
    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non + stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        return super.fuoco(pos, hidden);
    }//fuoco

    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        return "CarroTalpa nascosto: "+hidden+". "+ super.toString();
    }//toString
    
    /**
     * Disegna il carro nella griglia
     * @param g2 grafica
     * @param dimX  dimensione quadretti X
     * @param dimY  dimensione quadretti Y
     */
    @Override
    public void draw(Graphics2D g2, int dimX, int dimY){
        super.draw(g2, dimX, dimY, true, hidden);
    }//draw    
    
}//CarroTalpa
