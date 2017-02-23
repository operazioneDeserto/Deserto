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
     * Scopre il carro
     */
    public void scoperto(){
        hidden = false;    
    }//scoperto
    
    /**
     * Nasconde il carro
     */
    public void nascosto(){
        hidden = true;    
    }//nascosto
    
        /**
     * Colpisce il carro
     * @param pos   posizione del colpo sparato
     * @return <0 se il carro è stato distrutto, 0 se il carro è stato colpito, >0 se il carro non + stato colpito
     */
    @Override
    public int fuoco(Posizione pos) {
        return super.fuoco(pos, hidden);
    }//fuoco

    /**
     * Restituisce una stringa significativa sull'oggetto
     * @return stringa significativa
     */
    @Override
    public String toString() {
        return "CarroTalpa nascosto: "+hidden+". "+ super.toString();
    }//toString
    
}//CarroTalpa
