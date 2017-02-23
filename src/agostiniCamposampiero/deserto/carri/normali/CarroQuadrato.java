/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agostiniCamposampiero.deserto.carri.normali;

import agostiniCamposampiero.deserto.carri.CarroCantiere;
import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroQuadrato extends CarroCantiere{

    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero di pezzi del carro
     */
    public CarroQuadrato(Posizione pos, int num) {
        super(pos, num);
    }//Costruttore

    @Override
    public int stato() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean distrutto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int fuoco(Posizione pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        return "CarroQuadrato";
    }//toString
    
}
