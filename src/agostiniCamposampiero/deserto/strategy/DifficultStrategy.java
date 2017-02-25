package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;
import java.util.ArrayList;

/**
 *
 * @author giaco
 */
public class DifficultStrategy implements Strategy {
   
    private ArrayList<Posizione> coord;
    private Posizione last;
    private boolean lastHit;
    private boolean repeat;
    private final int height;
    private final int width;

    /**
     * Costruttore parametrico
     * @param height    altezza del campo   
     * @param width     larghezza del campo
     */
    public DifficultStrategy(int height, int width) {
        this.height = height;
        this.width = width;
        repeat = false;
        coord = new ArrayList<>();
        for(int i=1; i<=height; i++){
            if(i%2==0) for(int j=2; j<width; j++) coord.add(new Posizione(i,j));
            else for(int j=1; j<width; j++) coord.add(new Posizione(i,j));
        }
    }//Costruttore parametrico
    
   /**
     * E' stato colpito un carro nella precedente posizione
     * @param result
     */
    public void hit(boolean result){
        if(last!=null && result) repeat = true;
    }

    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    @Override
    public Posizione nextHit() {
        return last;
    }//nextHit
    
}
