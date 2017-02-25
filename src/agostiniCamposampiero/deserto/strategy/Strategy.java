package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author giaco
 */
public interface Strategy {
    
    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    public Posizione nextHit();    
    
}
