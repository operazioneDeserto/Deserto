package agostiniCamposampiero.deserto.carri.normali;

import agostiniCamposampiero.deserto.carri.CarroCantiere;
import agostiniCamposampiero.deserto.pos.*;
import java.util.ArrayList;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroLineare extends CarroCantiere{
    
    private ArrayList<Pezzo> tank;
       
    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero di pezzi del carro
     */
    public CarroLineare(Posizione pos, int num){
        super(pos,num);
        tank = new ArrayList<>();
        for(int i=0; i<num; i++) tank.add(new Pezzo(i));
    }//Costruttore

    /**
     * Restituisce lo stato del carro
     * @return <0 se il carro è distrutto, 0 se danneggiato e >0 se integro
     */
    @Override
    public int stato() {
        if(distrutto()) return -1;
        for(Pezzo tmp:tank) if(!tmp.stato()) return 0;
        return 1;
    }//stato

    /**
     * Verifica se il carro è stato distrutto
     * @return ture se il carro è distrutto, false altrimenti
     */
    @Override
    public boolean distrutto() {
        return (tank.isEmpty());           
    }//distrutto

    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non + stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        return fuoco(pos, false);
    }//fuoco
        
    /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @param hidden    true se il carro è nascosto, false altrimenti
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non è stato colpito
     */
    public int fuoco(Posizione pos, boolean hidden) {    
        Posizione tmp = getPos();
        int x=tmp.getX(), y=tmp.getY();
        for(int i=0; i<tank.size(); i++){
            if(pos.equals(tmp)){
                if(tank.get(i).hit(hidden)) tank.remove(i);
                if(distrutto()) return -1;
                else return 0;
            }
            tmp = new Posizione(x++,y);
        }
        return 1;
    }//fuoco
    
    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        String ris = "\n";
        for(Pezzo tmp:tank) ris+=tmp.toString();
        return "CarroLineare attualmente composto da "+tank.size()+" pezzi." +ris + super.toString();
    }//toString
    
}//CarroLineare
