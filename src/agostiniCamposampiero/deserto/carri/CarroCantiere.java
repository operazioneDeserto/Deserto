package agostiniCamposampiero.deserto.carri;

import agostiniCamposampiero.deserto.pos.*;
import java.awt.Graphics2D;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public abstract class CarroCantiere {

    private final Posizione pos;
    private final int num;

    /**
     * Costruttore parametrico
     * @param pos posizione del primo pezzo del carro
     * @param num numero di pezzi del carro
     */
    public CarroCantiere(Posizione pos, int num) {
        this.pos = pos;
        this.num = num;
    }//Costruttore

    /**
     * Restituisce la posizione
     * @return posizione del carro
     */
    public Posizione getPos() {
        return pos;
    }//getPos

    /**
     * Restituisce il numero di pezzi del carro
     * @return numero di pezzi del carro
     */
    public int getNum() {
        return num;
    }//getNum

    /**
     * Restituisce lo stato del carro
     * @return <0 se il carro è distrutto, 0 se danneggiato e >0 se integro
     */
    public abstract int stato();

    /**
     * Verifica se il carro è stato distrutto
     * @return ture se il carro è distrutto, false altrimenti
     */
    public abstract boolean distrutto();

    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non + stato colpito
     */
    public abstract int fuoco(Posizione pos);
    
    /**
     * Attacco dei guastatori
     * @return percentuale di riuscita dell'assalto di una squadra di guastatori
     */
    public abstract double sapperAttack();
    
    /**
     * Disegna il carro nella griglia
     * @param g2 grafica
     * @param dimX  dimensione quadretti X
     * @param dimY  dimensione quadretti Y
     */
    public abstract void draw(Graphics2D g2, int dimX, int dimY);
    
    /**
     * Verifica se una posizione è presente all'interno del carro
     * @param pos   posizione
     * @return true se è presente, false altrimenti
     */
    public abstract boolean present(Posizione pos);
    
    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        return "Carro originariamente composto da "+num+" pezzi. La posizione del carro è ("+pos.toString()+").";
    }//toString

}
