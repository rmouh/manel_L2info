package formule1zCorrection;

public class Vect {
    public final int x, y;
    public static final Vect ZERO = new Vect(0, 0);
    // Tan(pi/8)
    private static final double tanpi8 = Math.sqrt(2.)-1.;

    
    // Construit un vecteur avec les coordonnées fournies
    public Vect(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Addition
    public Vect add(Vect v) {
        return new Vect(this.x + v.x, this.y + v.y);
    }
    
    // Soustraction
    public Vect sub(Vect v) {
        return new Vect(this.x - v.x, this.y - v.y);
    }
    
    // Test d'égalité coordonnée par coordonnée
    public boolean egale(Vect other) {
        return this.x == other.x && this.y == other.y;
    }

    // Renvoie le vecteur à coordonnées entières de norme 1 dont la direction
    // est la plus proche de celle du vecteur, ou ZERO pour le vecteur nul.
    // (on utilise la norme L∞, ce qui correspond aux coordonnées adjacentes et
    // diagonales).
    public Vect normalise() {
        int ax = (abs(this.x)>tanpi8*abs(this.y))?((this.x>0)?1:-1):0;
        int ay = (abs(this.y)>tanpi8*abs(this.x))?((this.y>0)?1:-1):0;
        return new Vect(ax, ay);
    }
    // Auxiliaire : valeur absolue d'un nombre
    private static double abs(int z) { return (z<0)?-z:z; }


    // Méthode de comparaison, qui est utilisée par [assertEquals]
    // C'est la seule méthode de cette classe dont le code sera fourni
    // aux étudiants.
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return this.egale((Vect) other);
    }
    
}
