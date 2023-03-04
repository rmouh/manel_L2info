package nReineszCorrection;// Un module pour les couleurs
import java.awt.Color;
// Trois modules pour l'interface graphique
import IG.ZoneCliquable;
import IG.Grille;
import IG.Fenetre;


/* 

   Classe principale NReines.
   Ici on initialise le jeu et l'affichage.

   Pour l'affichage, on fait appel à la classe [IG.Fenetre],
   et on utilise deux méthodes de cette classe dont les
   signatures sont :

   public void ajouteElement([composant graphique]);
   public void dessineFenetre();

*/

public class NReinesCorrige {

    public static void main(String[] args) {
	int nb = 8;//Integer.parseInt(args[0]);
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
    
    public Validation(Plateau p) {
	// Création d'une zone cliquable de dimensions 80*25 pixels,
	// et contenant le texte "Valider".
	super("Valider", 80, 25);
	this.plateau = p;
    }

    public void clicGauche() {
	if (plateau.verifieConfiguration()) {
	    setBackground(Color.GREEN);
	} else {
	    setBackground(Color.RED);
	}
    }

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
    
    public Indice(Plateau p) {
	// Création d'une zone cliquable de dimensions 80*25 pixels,
	// et contenant le texte "Indice".
	super("Indice", 80, 25);
	this.plateau = p;
    }

    public void clicGauche() {
        // Test de résolubilité de la configuration actuelle.
        // Note : lorsque la méthode [verifieResolubilite] trouve une
        // configuration gagnante, en plus de renvoyer [true] elle met à jour
        // les attributs [indiceL] et [indiceC] du plateau avec les coordonnées
        // (ligne et colonne) de l'une des cases pouvant être jouées.
	if (plateau.verifieResolubilite()) {
	    setBackground(Color.GREEN);
	    int indiceL = plateau.getIndiceL();
	    int indiceC = plateau.getIndiceC();
	    Case cas = plateau.getCase(indiceL, indiceC);
	    cas.setBackground(Color.BLUE);
	} else {
	    setBackground(Color.RED);
	}
    }

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

    // Attributs statiques
    private int taille;

    // Tableau contenant les cases
    private Case[][] plateau;
    public Case getCase(int i, int j) {
    	return plateau[i][j];
    }

    // Constructeur
    public Plateau(int taille) {
	// Initialisation de la grille graphique
	super(taille, taille);
	// Initialisation des attributs statiques
	this.taille = taille;
	// Initialisation du tableau de cases
	this.plateau = new Case[taille][taille];
	for(int i=0; i<taille; i++) {
	    for(int j=0; j<taille; j++) {
		// Création d'une case du plateau
		Case cas = new Case(this);
		plateau[i][j] = cas;
		// On précise que chaque case doit être ajoutée
		// à l'affichage graphique.
		// La méthode vient de IG.Grille.
		this.ajouteElement(cas);
	    }
	}	
    }

    /*

      Partie "vérification de la légalité d'une configuration".

    */

    // Vérification des lignes (avec une construction [for each]).
    // La méthode [compteLigne] prend en paramètre un tableau de case
    // représentant une ligne de l'échiquier et renvoie le nombre de reines
    // présentes sur ces cases.
    private int compteLigne(Case[] l) {
        // La variable [nb] est un accumulateur, qui est incrémenté à chaque
        // nouvelle reine trouvée.
	int nb = 0;
	for (Case cas : l) {
	    if (cas.estOccupee()) { nb++; }
	}
	return nb;
    }
    // La méthode [verifieLignes] vérifie qu'aucune ligne ne contient deux
    // reines ou plus.
    private boolean verifieLignes() {
        // Stratégie avec accumulateur : une variable booléenne [ok] qui est
        // mise à jour après analyse de chaque ligne, et est renvoyée à la fin.
	boolean ok = true;
	for (Case[] l : this.plateau) {
	    ok = ok && (compteLigne(l) < 2);
	}
	return ok;
    }
    
    // Vérification des colonnes (avec un [for] classique).
    // La méthode [compteColonne] prend en paramètre l'indice d'une colonne de
    // l'échiquier et renvoie le nombre de reines présentes sur cette colonne.
    private int compteColonne(int j) {
        // La variable [nb] est toujours un accumulateur.
	int nb = 0;
	for (int i=0; i<this.taille; i++) {
	    if (plateau[i][j].estOccupee()) { nb++; }
	}
	return nb;
    }
    // La méthode [verifieColonnes] vérifie qu'aucune colonne ne contient
    // deux reines ou plus.
    private boolean verifieColonnes() {
        // Stratégie avec court-circuit : on renvoie le résultat définitif
        // [false] dès qu'une colonne fautive est trouvée. Sinon, si toutes
        // les colonnes ont été vérifiées sans souci, on renvoie [true].
	for (int j=0; j<this.taille; j++) {
	    if (compteColonne(j) > 1) { return false; }
	}
	return true;
    }
    
    // Vérification des diagonales et des antidiagonales.
    // Comme pour la vérification des colonnes, les méthodes de vérification
    // [verifieDiagonales] et [verifieAntidiagonales] font appel à des méthodes
    // auxiliaires [compteDiagonale] et [compteAntidiagonale] dénombrant les
    // reines présentes sur une diagonale ou antidiagonale donnée par son
    // indice.
    
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

    // Méthode de vérification générale.
    public boolean verifieConfiguration() {
	return (verifieLignes() && verifieColonnes() &&
		verifieDiagonales() && verifieAntidiagonales());
    }


    /*

      Partie "vérification de la résolubilité d'une configuration et
              proposition d'un coup à jouer".

    */

    // Méthode fournissant la première ligne non occupée
    private int prochaineLigne() {
	int k=0;
	while (k<this.taille && compteLigne(this.plateau[k]) != 0) { k++; }
	return k;
    }

    // Variables locales destinées à recevoir les coordonnées de
    // l'éventuelle proposition de coup.
    private int indiceL, indiceC;
    public int getIndiceL() { return indiceL; }
    public int getIndiceC() { return indiceC; }
    
    // Méthode vérifiant que la configuration actuelle est
    // résoluble et plaçant le cas échéant dans [indiceL] et
    // [indiceR] les coordonnées d'un coup possible vers une
    // solution.
    // La méthode est récursive, et explore tous les coups valides.
    // Lors de l'exploration d'un coup, la méthode modifie l'échiquier,
    // puis annule ses modifications lors du "backtrack".
    public boolean verifieResolubilite() {
	// Si la configuration n'est pas valide, on retournera [false].
	if (verifieConfiguration()) {
	    // Si la configuration est valide, on regarde la prochaine
	    // ligne non occupée. Si toutes les lignes sont occupées
	    // alors une solution a été trouvée et on retourne [true].
	    int l = prochaineLigne();
	    if (l==taille) {
		return true;
	    } else {
		// On explore toutes les cases de la ligne [l]
		// considérée, jusqu'à atteindre le bout de la ligne
		// jusqu'à ce que le booléen [solutionTrouvee] passe
		// à [true].
		// Au passage, on indique les coordonnées courantes
		// dans [indiceL] et [indiceC]. Ainsi, si la fonction
		// retourne [true], alors ces deux attributs
		// correspondront à un coup qui permet bien d'atteindre
		// une solution.
		boolean solutionTrouvee = false;
		for (int c=0; c<this.taille && !solutionTrouvee; c++) {
		    plateau[l][c].occupe();
		    solutionTrouvee = verifieResolubilite();
		    plateau[l][c].libere();
		    indiceC = c;
		}
		indiceL = l;
		return solutionTrouvee;
	    }
	} else {
	    return false;
	}
    }
	
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

    // Attributs
    private boolean occupee;
    private Plateau plateau;

    // Constructeur
    public Case(Plateau p) {
	// Initialisation d'une case cliquable, de dimensions 40*40 pixels.
	super(40, 40);
	// Initialisation des attributs
	this.occupee = false;
	this.plateau = p;
    }

    // Pour permettre à un objet [Plateau] de consulter l'état d'une case.
    public boolean estOccupee() {return this.occupee;}

    // Méthodes pour occuper ou libérer une case.
    public void occupe() { this.occupee = true; }
    public void libere() { this.occupee = false; }

    // Action à effectuer lors d'un clic gauche.
    // Ceci utilise [IG.ZoneCliquable].
    public void clicGauche() {
	this.occupee = !this.occupee;
	if (this.occupee) {
	    setBackground(Color.BLACK);
	} else {
	    setBackground(Color.WHITE);
	}
    }

    // Action à effectuer lors d'un clic droit.
    // Ceci utilise [IG.ZoneCliquable].
    public void clicDroit() { }

}