/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agostiniCamposampiero.deserto.pos;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public class Pezzo {
    
    private int energy;
    private final int num;
    
    /**
     * Costruttore non parametrico
     * @param num   numero all'interno del carro
     */
    public Pezzo(int num){
        this.num = num;
        energy = 100;
    }//Costruttore
    
    /**
     * Metodo per colpire il pezzo, da utilizzare con carri non occultabili
     * @return true se il pezzo è stato distrutto, false altrimenti
     */
    public boolean hit(){
        energy-=50;
        return (energy==0);
    }//hit  
    
    /**
     * Metodo per colpire il pezzo, da utilizzare con carri occultabili
     * @param hidden    true se il carro è nascosto, false altrimenti
     * @return true se il pezzo è stato distrutto, false altrimenti
     */
    public boolean hit(boolean hidden){
        if(hidden) energy-=25;
        else energy-=50;
        return (energy==0);
    }//hit
    
    /**
     * Controlla se il pezzo è intatto
     * @return integrità dell'oggetto
     */
    public boolean stato(){
        return (energy==100);
    }//stato
    
}//Pezzo
