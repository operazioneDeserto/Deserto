package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;
import java.util.ArrayList;

/**
 *
 * @author giaco
 */
public class MediumStrategy implements Strategy{
    
    private final ArrayList<Posizione> coord;
    private Posizione pos;
    private final int height;
    private final int width;

    /**
     * Costruttore parametrico
     * @param height    altezza del campo   
     * @param width     larghezza del campo
     */
    public MediumStrategy(int height, int width) {
        this.height = height;
        this.width = width;
        coord = new ArrayList<>();
        for(int i=1; i<=height; i+=2) for(int j=2; j<width; j+=2) coord.add(new Posizione(i,j));
        for(int i=2; i<=height; i+=2) for(int j=1; j<width; j+=2) coord.add(new Posizione(i,j));
        pos = coord.get((int) (Math.random()*coord.size()));
    }//Costruttore parametrico

    /**
     * Raccoglie il feedback del fuoco sull'ultima posizione restituita
     * @param result    risultato ultimo sparo
     */
    @Override
    public void hitFeedback(int result) {
        if(result != 0){
            pos = coord.get((int) (Math.random()*coord.size()));
            coord.remove(pos);
        } 
    }//hitFeedback
      

    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    @Override
    public Posizione nextHit() {
        return pos;
    }//nextHit

}//MediumStrategy
