package agostiniCamposampiero.deserto.carri;
import agostiniCamposampiero.deserto.pos.*;

/**
 *
 * @author FSEVERI\camposampiero3429
 */
public abstract class CarroCantiere {
  private Posizione pos;
  private int num;
  
  /**
   * Costruttore parametrico
   * @param pos posizione del primo pezzo del carro
   * @param num numero di pezzi del carro
   */
  public CarroCantiere(Posizione pos, int num) {
    this.pos = pos;
    this.num = num;
  }
  
  
  public abstract int stato ();
  
  public abstract boolean distrutto ();
  
  public abstract void fuoco ();
   
}
