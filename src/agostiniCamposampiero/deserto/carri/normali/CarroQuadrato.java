package agostiniCamposampiero.deserto.carri.normali;

import agostiniCamposampiero.deserto.carri.CarroCantiere;
import agostiniCamposampiero.deserto.pos.*;
import java.util.ArrayList;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroQuadrato extends CarroCantiere {

    private ArrayList<Pezzo> tank;

    /**
     * Costruttore parametrico
     *
     * @param pos posizione del carro
     * @param num numero di pezzi del carro
     */
    public CarroQuadrato(Posizione pos, int num) {
        super(pos, num);
        tank = new ArrayList<>();
        for (int i = 0; i < num; i++) tank.add(new Pezzo(i));
    }//Costruttore

    /**
     * Restituisce lo stato del carro
     *
     * @return <0 se il carro è distrutto, 0 se danneggiato e >0 se integro
     */
    @Override
    public int stato() {
        if (distrutto()) return -1;
        for (Pezzo tmp : tank) if (!tmp.stato()) return 0;
        return 1;
    }//stato

    /**
     * Verifica se il carro è stato distrutto
     *
     * @return ture se il carro è distrutto, false altrimenti
     */
    @Override
    public boolean distrutto() {
        return (tank.isEmpty());
    }//distrutto

    /**
     * Colpisce il carro
     *
     * @param pos posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito,
     * >0 se il carro non + stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        Posizione tmp = getPos();
        int x = tmp.getX(), y = tmp.getY();
        int lato = (int) Math.sqrt(getNum());
        int a=tank.size()%lato, b=tank.size()/lato;
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < (lato-a); j++) {
                if (pos.equals(tmp)) {
                    if (tank.get(i).hit()) tank.remove(i);
                    if (distrutto()) return -1;
                    else return 0;
                }
                if(j==(lato-a)){
                    x-=lato;
                    tmp = new Posizione(x, y++);
                }
                else tmp = new Posizione(x++, y);
            }
        }
        return 1;
    }//fuoco

    /**
     * Restituisce una stringa significativa sull'oggetto
     *
     * @return stringa significativa
     */
    @Override
    public String toString() {
        String ris = "\n";
        for (Pezzo tmp : tank) ris += tmp.toString();
        return "CarroQuadrato attualmente composto da " + tank.size() + " pezzi." + ris + super.toString();
    }//toString

}//CarroQuadrato
