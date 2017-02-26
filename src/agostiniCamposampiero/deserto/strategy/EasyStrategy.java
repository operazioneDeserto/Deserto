package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author giaco
 */
public class EasyStrategy implements Strategy {
    
    private final int height;
    private final int width;
    private Posizione pos;
    
    /**
     * Costruttore parametrico
     * @param height    altezza del campo   
     * @param width     larghezza del campo
     */
    public EasyStrategy(int height, int width){
        this.height = height;
        this.width = width;
        pos = new Posizione((int) (Math.random()*height),(int) (Math.random()*width));
    }//Costruttore parametrico
    
    /**
     * Raccoglie il feedback del fuoco sull'ultima posizione restituita
     * @param result    risultato ultimo sparo
     */    
    @Override
    public void hitFeedback(int result) {
        if(result<0 || result>0) pos = new Posizione((int) (Math.random()*height),(int) (Math.random()*width));
    }//hitFeedback
    
    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    @Override
    public Posizione nextHit() {
        return pos;
    }//nextHit
    
}//EasyStrategy
