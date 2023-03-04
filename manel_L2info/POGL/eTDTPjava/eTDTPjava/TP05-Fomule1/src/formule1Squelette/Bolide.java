package formule1Squelette;
import java.util.LinkedList;
import java.util.List;

public class Bolide {
    Vect position;
    Vect vitesse = Vect.ZERO;
    
    // Constructeur
    Bolide(Vect p) {
    	position = p;
    }
    
    // Traduit la direction en une acceleration elementaire, en prenant le
    // vecteur de norme 1 approchant au mieux la direction.
    Vect calculeAcceleration(Vect cible) {
    	 Vect temp = cible.sub(this.position);
        return (temp.normalise());
    }
    
    // Applique une acceleration
    void accelereDe(Vect acceleration) {
        this.vitesse = this.vitesse.add(acceleration);
        //this.position.add(acceleration);
    }

    // Combine les deux methodes precedentes (complet)
    void accelereVers(Vect cible) {
        this.accelereDe(this.calculeAcceleration(cible));
    }
    
    // Arrete le bolide
    void stop() { this.vitesse = Vect.ZERO;    }
    
    // Renvoie une liste de deplacements de norme 1, dont la somme
    // egale la vitesse.
    // Strategie : on part d'un vecteur cible egal a la vitesse, qu'on
    // normalise pour obtenir le premier deplacement, puis on retire ce
    // premier deplacement du vecteur cible et on normalise a nouveau
    // pour obtenir le deuxieme deplacement, etc.
    List<Vect> calculeDeplacements() {
        int i = 0;
        List<Vect> deplacement = new LinkedList<>();
        if (this.vitesse.equals(Vect.ZERO))
            return (deplacement);
        deplacement.add(i, this.vitesse.normalise());
        this.vitesse = this.vitesse.sub(deplacement.get(i));
        while (!this.vitesse.equals(Vect.ZERO)) {
            //System.out.println(i + "  " + this.vitesse.normalise().x + "  " + this.vitesse.normalise().y);
            deplacement.add(++i, this.vitesse.normalise());
            this.vitesse = this.vitesse.sub(deplacement.get(i));
        }
        return deplacement;
    }

}
