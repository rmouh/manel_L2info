package formule1zCorrection;
import java.awt.Color;

import IG.ZoneCliquable;

public class Case extends ZoneCliquable {
    public    final boolean traversable;
    public     final Vect coord;
    public     final Circuit circuit;
    
    // Création d'une case de coordonnées données, traversable ou non
    Case(Circuit c, Vect p, boolean b) {
        super("", 12, 12);
        this.traversable = b;
        this.coord = p;
        this.circuit = c;
        if (!this.traversable)
            this.setBackground(Color.BLACK);
    }
    
    // Réaction au clic
    public void clicGauche() {
        this.circuit.gereClic(this.coord);
    }
    
    public void clicDroit() {}
}
