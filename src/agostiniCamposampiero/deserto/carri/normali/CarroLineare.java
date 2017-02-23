/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agostiniCamposampiero.deserto.carri.normali;

import agostiniCamposampiero.deserto.carri.CarroCantiere;
import agostiniCamposampiero.deserto.pos.*;
import java.util.ArrayList;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroLineare extends CarroCantiere{
        
    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero di pezzi del carro
     */
    public CarroLineare(Posizione pos, int num){
        super(pos,num);
    }//Costruttore

    @Override
    public int stato() {

    }

    @Override
    public boolean distrutto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fuoco() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
