package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author giaco
 */
public class EasyStrategy implements Strategy {
    
    private final int height;
    private final int width;
    
    /**
     * Costruttore parametrico
     * @param height    altezza del campo   
     * @param width     larghezza del campo
     */
    public EasyStrategy(int height, int width){
        this.height = height;
        this.width = width;
    }//Costruttore parametrico
    
    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    @Override
    public Posizione nextHit() {
        return new Posizione((int) (Math.random()*height),(int) (Math.random()*width));
    }//nextHit
    
}//EasyStrategy
