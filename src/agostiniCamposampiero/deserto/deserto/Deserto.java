package agostiniCamposampiero.deserto.deserto;

import agostiniCamposampiero.deserto.grafica.*;
import agostiniCamposampiero.deserto.carri.normali.*;
import agostiniCamposampiero.deserto.carri.speciali.CarroTalpa;
import agostiniCamposampiero.deserto.pos.Posizione;
import javax.swing.JFrame;

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
        CarroQuadrato z = new CarroQuadrato(new Posizione(1,2), 4);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        z.fuoco(new Posizione(2,2));
        System.out.println(z);
        JFrame my = new Start();
        my.setSize(300,245);
        my.setVisible(true);
    }
    
}
