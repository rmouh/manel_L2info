package formule1zCorrection;


import java.util.List;
import java.util.LinkedList;

public class Bolide {
    public Vect position;
    public Vect vitesse = Vect.ZERO;
    
    // Donne la position passée en paramètre et la vitesse 0
    public Bolide(Vect p) {
        this.position = p;
    }
    
    // Traduit la direction en une accélération élémentaire, en prenant le
    // vecteur de norme 1 approchant au mieux la direction.
    public Vect calculeAcceleration(Vect cible) {
        return cible.sub(this.position).normalise();
    }
    
    // Ajoute l'acceleration à la vitesse
    public void accelereDe(Vect acceleration) {
        this.vitesse = this.vitesse.add(acceleration);
    }

    // Combine les deux méthodes précédentes
    public void accelereVers(Vect cible) {
        this.accelereDe(this.calculeAcceleration(cible));
    }
    
    // Annule la vitesse
    public  void stop() {
        this.vitesse = Vect.ZERO;
    }
    
    // Renvoie une liste de déplacements de norme 1, dont la somme
    // égale la vitesse.
    // Stratégie : on part d'un vecteur cible égal à la vitesse, qu'on
    // normalise pour obtenir le premier déplacement, puis on retire ce
    // premier déplacement du vecteur cible et on normalise à nouveau
    // pour obtenir le deuxième déplacement, etc.
    public List<Vect> calculeDeplacements() {
        Vect deplacementCible = this.vitesse;
        List<Vect> deplacements = new LinkedList<>();
        while (!deplacementCible.equals(Vect.ZERO)) {
            Vect deplacement = deplacementCible.normalise();
            deplacements.add(deplacement);
            deplacementCible = deplacementCible.sub(deplacement);
        }
        return deplacements;
    }

}
