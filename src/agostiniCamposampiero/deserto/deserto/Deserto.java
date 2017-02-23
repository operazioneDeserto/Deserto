/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.carri.normali.CarroLineare;
import agostiniCamposampiero.deserto.carri.speciali.CarroTalpa;
import agostiniCamposampiero.deserto.pos.Posizione;

/**
 *
 * @author giaco
 */
public class Deserto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CarroTalpa x = new CarroTalpa(new Posizione(1,2), 4, true);
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        x.scoperto();
        x.fuoco(new Posizione(2,2));
        System.out.println(x);
        CarroLineare y = new CarroLineare(new Posizione(1,2), 4);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
        y.fuoco(new Posizione(2,2));
        System.out.println(y);
    }
    
}
