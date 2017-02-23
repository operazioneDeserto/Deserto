package agostiniCamposampiero.deserto.pos;

/**
 *
 * @author User
 */
public class Posizione {
    private int x;
    private int y;

    /*
    *Costruttore parametrico
    *@param x: coordinata x del pezzo
    *@param y: coordinata y del pezzo
    */    
    public Posizione(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
    *Restituisce la coordinata x del pezzo
    *@return la coordinata x del pezzo
    */
    public int getX() {
        return x;
    }
    
    /*
    *Restituisce la coordinata y del pezzo
    *@return la coordinata y del pezzo
    */
    public int getY() {
        return y;
    }
 
    /*
    *Confronta due posizioni e restituisce true se le coordinate sono uguali, false altrimenti
    *@return true se le coordinate sono identiche, false altrimenti
    */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Posizione other = (Posizione) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    /*
    *Restituisce una stringa di stato della posizione
    *@return una stringa di stato
    */
    @Override
    public String toString() {
        return "Posizione{" + "x=" + x + ", y=" + y + '}';
    }  
}
