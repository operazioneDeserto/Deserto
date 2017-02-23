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
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Posizione other = (Posizione) obj;
        return (this.x == other.x && this.y == other.y);
    }

    /*
    *Restituisce una stringa di stato della posizione
    *@return una stringa di stato
    */
    @Override
    public String toString() {
        return "x:" + x + ", y:" + y;
    }  
}
