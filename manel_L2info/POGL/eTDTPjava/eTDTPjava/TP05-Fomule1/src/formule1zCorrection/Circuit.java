package formule1zCorrection;
import java.util.List;
import java.util.LinkedList;
import java.awt.Color;

import IG.Grille;

public class Circuit extends Grille {
 public    int nbLignes, nbColonnes;
    public        Case[][] terrain;
    public Bolide bolide;
    public        List<Vect> historique;

    // Construit un circuit à partir de dimensions, d'une carte, et
    // de coordonnées de départ.
    public Circuit(int nbL, int nbC, boolean[][] carte, int lD, int cD) {
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

    public    boolean deplaceBolide(List<Vect> deplacements) {
        Vect positionCourante = bolide.position;
        for (Vect depl: deplacements) {
            Vect positionCible = bolide.position.add(depl);
            if (getCase(positionCible).traversable) {
                historique.add(bolide.position);
                bolide.position = positionCible;
            } else {
                bolide.stop();
                return false;
            }
        }
        return true;
    }
    
    // Provoque le déplacement
    public    void gereClic(Vect cible) {
        for (Vect pos: historique) {
            getCase(pos).setBackground(Color.WHITE);
        }
        historique.clear();
        getCase(bolide.position).setBackground(Color.WHITE);
        bolide.accelereVers(cible);
        List<Vect> deplacements = bolide.calculeDeplacements();
        if (deplaceBolide(deplacements)) {
            for (Vect pos: historique) {
                getCase(pos).setBackground(Color.GREEN);
            }
        }
        getCase(bolide.position).setBackground(Color.BLUE);
    }
    
    // Renvoie la case aux coordonnées fournies
    Case getCase(Vect p) {
        return terrain[p.x][p.y];
    }
}
