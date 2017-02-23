/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agostiniCamposampiero.deserto.carri.speciali;

import agostiniCamposampiero.deserto.carri.normali.CarroLineare;
import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class CarroTalpa extends CarroLineare{
    
    private boolean hidden;

    /**
     * Costruttore parametrico
     * @param pos   posizione del carro
     * @param num   numero pezzi del carro
     * @param hidden    true se il carro è nascosto, false altrimenti
     */
    public CarroTalpa(Posizione pos, int num, boolean hidden) {
        super(pos, num);
        this.hidden = hidden;
    }//Costruttore
    
    /**
     * Nasconde il 
     */
    public void scoperto(){
        hidden = false;    
    }//scoperto
    
    public void nascosto(){
        hidden = true;    
    }//nascosto
}
