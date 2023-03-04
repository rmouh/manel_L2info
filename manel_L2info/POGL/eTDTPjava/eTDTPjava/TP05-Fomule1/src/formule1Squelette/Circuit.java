package formule1Squelette;

import IG.Grille;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Circuit extends Grille {
    int nbLignes, nbColonnes;
    Case[][] terrain;
    Bolide bolide;
    List<Vect> historique;

    // Construit un circuit a partir de dimensions, d'une carte, et
    // de coordonnees de départ.
    Circuit(int nbL, int nbC, boolean[][] carte, int lD, int cD) {
    	// Initialisation de la partie ig
        super(nbL, nbC);
        this.nbLignes = nbL;
        this.nbColonnes = nbC;
        this.terrain = new Case[nbL][nbC];
        for (int l=0; l<this.nbLignes; l++) {
            for (int c=0; c<this.nbColonnes; c++) {
                boolean b = l<carte.length && c<carte[l].length && carte[l][c];
                Case ca = new Case(this, new Vect(l, c), b);
                this.terrain[l][c] = ca;
                this.ajouteElement(ca);
            }
        }
        if (lD<0 || lD>=nbL || cD<0 || cD >=nbC)
            throw new IllegalArgumentException("Wrong start position");
        Vect positionDepart = new Vect(lD, cD);
        this.bolide = new Bolide(positionDepart);
        this.getCase(positionDepart).setBackground(Color.BLUE);
        this.historique = new LinkedList<>();
    }

    // Applique les deplacements indiques, dans la mesure du possible
    boolean deplaceBolide(List<Vect> deplacements) {
    	bolide.calculeDeplacements();
        return false;
    }
    
    // Calcule et effectue le deplacement
    void gereClic(Vect cible) {
        // A completer
    }
    
    // Renvoie la case aux coordonnées fournies (complet)
    Case getCase(Vect p) {
        return terrain[p.x][p.y];
    }
}
