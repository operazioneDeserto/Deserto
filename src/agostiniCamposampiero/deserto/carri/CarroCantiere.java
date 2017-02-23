package agostiniCamposampiero.deserto.carri;

import agostiniCamposampiero.deserto.pos.*;
import java.util.ArrayList;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public abstract class CarroCantiere {

    private ArrayList<Pezzo> tank;
    private Posizione pos;
    private int num;

    /**
     * Costruttore parametrico
     * @param pos posizione del primo pezzo del carro
     * @param num numero di pezzi del carro
     */
    public CarroCantiere(Posizione pos, int num) {
        this.pos = pos;
        this.num = num;
        for(int i=0; i<tank.size(); i++) tank.add(new Pezzo());
    }

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

    public abstract boolean distrutto();

    public abstract void fuoco();

}
