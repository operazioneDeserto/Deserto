package agostiniCamposampiero.deserto.strategy;

import agostiniCamposampiero.deserto.pos.Posizione;
import java.util.ArrayList;

/**
 *
 * @author giaco
 */
public class DifficultStrategy implements Strategy {
   
    private final ArrayList<Posizione> coord;
    private Posizione pos;
    private int norden;
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
        norden = 0;
        coord = new ArrayList<>();
        for(int i=1; i<=height; i++){
            if(i%2==0) for(int j=2; j<width; j++) coord.add(new Posizione(i,j));
            else for(int j=1; j<width; j++) coord.add(new Posizione(i,j));
        }
        pos = coord.get((int) (Math.random()*coord.size()));
    }//Costruttore parametrico

    /**
     * Raccoglie il feedback del fuoco sull'ultima posizione restituita
     * Metodo implementato con il sistema "norden", che permette al PC di trovare attraverso un
     * algoritmo l'angolo in alto a destra del carro, per poi distruggerlo del tutto
     * @param result    risultato ultimo sparo
     */
    @Override
    public void hitFeedback(int result) {
        int x=pos.getX(), y=pos.getY();
        if(result>0 && norden==0) pos = coord.get((int) (Math.random()*coord.size()));
        else if(result<0){
            norden = 0;
            pos = coord.get((int) (Math.random()*coord.size()));
        } else if (result==0){
            switch(norden){
                case 0:
                    norden = 1;
                    if(x>1) pos = new Posizione(x-=1,y);
                    else norden++;
                    break;                    
                case 1:
                    if(x>1) pos = new Posizione(x-=1,y);
                    else norden++;
                    break;
                case 2:
                    if(y>1) pos = new Posizione(x,y-=1);
                    else norden++;                    
                    break;
                default: break;
            }
        } else {
            switch(norden){
                case 1:
                    norden++;
                    if(y>1) pos = new Posizione(x+=1,y);
                    break;
                case 2:
                    norden++;
                    pos = new Posizione(x,y+=1);
                    break;
                default: break;
            }
        }    
        coord.remove(pos);
        System.out.println(pos);
        System.out.println(norden);
    }//hitFeedback
      

    /**
     * Restiuisce la posizione del prossimo colpo da sparare
     * @return posizione in cui sparare il colpo
     */
    @Override
    public Posizione nextHit() {
        return pos;
    }//nextHit

}//DifficultStrategy
