package nReinesSquelette;// Un module pour les couleurs
// Trois modules pour l'interface graphique

import IG.Fenetre;
import IG.Grille;
import IG.ZoneCliquable;

import java.util.ArrayList;


/*  Classe principale NReines.
   Ici on initialise le jeu et l'affichage.

   Pour l'affichage, on fait appel à la classe [IG.Fenetre],
   et on utilise deux méthodes de cette classe dont les
   signatures sont :

   public void ajouteElement([composant graphique]);
   public void dessineFenetre();

*/

public class NReines {

    public static void main(String[] args) {
        int nb = 8;//Taille de l'échiquier qu'on peut modifier
        // Création d'une fenêtre graphique, d'un échiquiers
        // et de deux boutons.
        Fenetre fenetre = new Fenetre(nb + " reines");
        Plateau plateau = new Plateau(nb);
        Validation validation = new Validation(plateau);
        Indice indice = new Indice(plateau);
        // On précise que l'échiquier et les boutons doivent
        // être affichés dans la fenêtre graphique.
        fenetre.ajouteElement(plateau);
        fenetre.ajouteElement(validation);
        fenetre.ajouteElement(indice);
        fenetre.dessineFenetre();
    }
}

/*

   Bouton de validation. On ne demande à ce bouton de ne réagir
   qu'aux clics gauches. Le bouton doit se colorer en vert si
   la configuration actuelle du plateau est licite, et en rouge
   sinon.

   Les deux appels de méthode suivants permettent respectivement
   de colorer le bouton en vert ou rouge :

   setBackground(Color.GREEN);
   setBackground(Color.RED);

   On fait appel à la classe abstraite [IG.ZoneCliquable].
   Pour cela, il faut définir deux méthodes correspondant aux
   actions à effectuer lors d'un clic avec le bouton gauche ou
   avec le bouton droit. Leurs signatures sont :

   public void clicGauche();
   public void clicDroit();

*/

class Validation extends ZoneCliquable {

    private Plateau plateau;

    public Validation(Plateau plateau) {
        // Création d'une zone cliquable de dimensions 80*25 pixels,
        // et contenant le texte "Valider".
        super("Valider", 80, 25);
        this.plateau = plateau;
    }

    public void clicGauche() { /* À compléter */ }
    public void clicDroit() {}
}

/*

  Bouton de demande d'indice. On ne demande à ce bouton de ne
  réagir qu'aux clics gauches. Si la configuration actuelle du
  plateau peut être complétée en une configuration complète,
  alors le bouton doit se colorer en vert, et doit indiquer un
  prochain coup possible en colorant la case correspondante en
  bleu.

*/

class Indice extends ZoneCliquable {

    private Plateau plateau;

    public Indice(Plateau plateau) {
        // Création d'une zone cliquable de dimensions 80*25 pixels,
        // et contenant le texte "Indice".
        super("Indice", 80, 25);
        this.plateau = plateau;
    }

    public void clicGauche() { /* À compléter */ }
    public void clicDroit() {}
}



/*

   Une classe pour l'échiquier.

   La mention [extends Grille] permet d'afficher les cases
   sous la forme d'une grille, en utilisant la classe [IG.Grille].

   Lorsqu'une case [c] est créée, pour l'intégrer à l'affichage
   graphique il faut réaliser l'appel de méthode suivante :

   this.ajouteElement(c);

*/

class Plateau extends Grille {

    // Attributs : taille et tableau de cases
    private final int taille;
    private Case[][] plateau;

    // Constructeur
    public Plateau(int taille) {
        // Initialisation de la grille graphique de dimensions taille*taille
        super(taille, taille);
        this.taille = taille;

        plateau = new Case[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++){
                plateau[i][j] = new Case(this);
                this.ajouteElement(plateau[i][j]);
            }
        }

    }

    // Méthode de vérification générale.
    public boolean verifieConfiguration() { return false; /* À remplacer ! */}


    // Cadeau : vérification des diagonales et des antidiagonales.

    // Les méthodes de vérification [verifieDiagonales] et
    // [verifieAntidiagonales] font appel à des méthodes auxiliaires
    // [compteDiagonale] et [compteAntidiagonale] dénombrant les reines
    // présentes sur une diagonale ou antidiagonale donnée par son indice.

    // Pour un plateau de côté [N], et [k] un entier tel que [-N < k < N], la
    // diagonale d'indice [k] est formée par les cases de coordonnées [i, i+k]
    // (pour les [i] tels que cette paire de coordonnées est valide).
    private int compteDiagonale(int k) {
        int nb = 0;
        // Les variables [min, max] sont définies avec les valeurs minimales
        // et maximales de [i] telles que [i, i+k] soit une paire de
        // coordonnées valides.
        int min, max;
        if (k<0) {
            min = -k; max = this.taille;
        } else {
            min = 0; max = this.taille-k;
        }
        for (int i=min; i<max; i++) {
            if (plateau[i][i+k].estOccupee()) { nb++; }
        }
        return nb;
    }
    private boolean verifieDiagonales() {
        // Les diagonales d'indices [-N+1] et [N-1] ne sont pas analysées, car
        // elles ne sont constituées que d'une case et ne peuvent donc pas
        // contenir plus d'une reine.
        for (int k=2-taille; k<taille-1; k++) {
            if (compteDiagonale(k) > 1) { return false; }
        }
        return true;
    }
    // L'antidiagonale d'indice [k] est définie similairement à la diagonale
    // d'indice [k], avec les cases de coordonnées [N-1-i, i+k].
    private int compteAntidiagonale(int k) {
        int nb = 0;
        int min, max;
        if (k<0) {
            min = -k; max = this.taille;
        } else {
            min = 0; max = this.taille-k;
        }
        for (int i=min; i<max; i++) {
            if (plateau[taille-1-i][i+k].estOccupee()) { nb++; }
        }
        return nb;
    }
    private boolean verifieAntidiagonales() {
        for (int k=2-taille; k<taille-1; k++) {
            if (compteAntidiagonale(k) > 1) { return false; }
        }
        return true;
    }

    // Méthode vérifiant que la configuration actuelle est
    // résoluble et plaçant le cas échéant dans [indiceL] et
    // [indiceR] les coordonnées d'un coup possible vers une
    // solution.
    // La méthode est récursive, et explore tous les coups valides.
    // Lors de l'exploration d'un coup, la méthode modifie l'échiquier,
    // puis annule ses modifications lors du "backtrack".
    public boolean verifieResolubilite() { return false; /* À remplacer ! */}

}

/*

   Une classe pour les cases du terrain de jeu.

   On demande à ces cases de réagir aux clics gauches.
   Lorsque l'on clique sur une case libre (blanche), celle-ci doit
   être colorée en noir et indiquée comme occupée.
   Lorsque l'on clique sur une case occupée (noire), celle-ci doit
   être colorée en blanc et indiquée comme libre.

   La mention [extends ZoneCliquable] permet de faire réagir les
   cases aux clics de souris, en utilisant [IG.ZoneCliquable] et
   les méthodes

   public void clicGauche();
   public void clicDroit();

*/

class Case extends ZoneCliquable {

    protected boolean empty;
    public Case(Plateau plateau) {
        // Initialisation d'une case cliquable, de dimensions 40*40 pixels.
        super(40, 40);
        empty = true;
    }

    // Permet de tester si une case est occupée.
    public boolean estOccupee() { return(!empty); }

    // Action à effectuer lors d'un clic gauche.
    public void clicGauche() {
        this.empty = !this.empty;

    }

    // Action à effectuer lors d'un clic droit.
    public void clicDroit() { }

}