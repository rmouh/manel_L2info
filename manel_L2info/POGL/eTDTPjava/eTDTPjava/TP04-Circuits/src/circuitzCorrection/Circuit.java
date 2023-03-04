package circuitzCorrection;

import java.util.ArrayList;

//==========================================================
//   Circuits
//==========================================================


public class Circuit {

    public static void main(String[] args) {
        Circuit c = new Circuit();
        Noeud n0 = c.creeEntree();	c.sortie=n0;
        System.out.println("Ceci doit afficher 33 : " + c.calcule(33));
        Noeud n1=c.creeAddition(n0,n0);c.sortie=n1;
        System.out.println("Ceci doit afficher 66 : " + c.calcule(33));
        Noeud n2=c.creeAddition(n1,c.creeConstante(1));c.sortie=n2;
        System.out.println("Ceci doit afficher 67 : " + c.calcule(33));
        Noeud n3 = c.creeMultiplication(n2, c.creeMultiplication(n0,n0)); c.sortie = n3;
        System.out.println("Ceci doit afficher 20 : " + c.calcule(2));
        c.sortie=n0;System.out.print("Ceci doit afficher x:  ");c.affiche();
        c.sortie = n3; System.out.print("Ceci doit afficher (((x + x) + 1) * (x * x)) : ");   c.affiche();

        Circuit c2 = new Circuit();
        Noeud n4 = c2.creeEntree();
        Noeud n5=c2.creeMultiplication(n4,n4);
        Noeud n6=c2.creeMultiplication(n5,n5); c2.sortie=n6;
        System.out.print("Ceci doit afficher  ((x * x) * (x * x)):   ");  c2.affiche();
        System.out.println("Ceci doit afficher 2 : " + c2.nbNoeudsArith());
        c2.calcule(1); System.out.println("Ceci doit afficher 3: " + c2.nbOpEffectuees());


        Circuit c3 = new Circuit();
        Noeud n7 = c3.creeEntree();
        Noeud n8=c3.creeMultiplicationMemoisee(n7,n7);
        Noeud n9=c3.creeMultiplicationMemoisee(n8,n8); c3.sortie=n9;
        System.out.print("Ceci doit afficher  ((x * x) * (x * x)):   ");  c2.affiche();
        System.out.println("Ceci doit afficher 2 : " + c2.nbNoeudsArith());
        c2.calcule(1); System.out.println("Ceci doit afficher 2: " + c2.nbOpEffectuees());


        Circuit p4 = expRapide(4); p4.affiche();
        System.out.println("Ceci doit afficher 4 : " + p4.nbNoeudsArith());
        System.out.println("Ceci doit afficher  65536 : " + p4.calcule(2));
        System.out.println("Ceci doit afficher  15: " + p4.nbOpEffectuees());

        Circuit p4m = expRapideMemoise(4);
        System.out.println("Ceci doit afficher 4 : " + p4m.nbNoeudsArith());
        System.out.println("Ceci doit afficher 65536 : " + p4m.calcule(2));
        System.out.println("Ceci doit afficher 4 : " + p4m.nbOpEffectuees());

    }

    // Partie I : Construction
    private int entree;    // Valeur de la variable dont dépend le calcul
    private Noeud sortie;  // Le dernier nœud, calculant le résultat
    private ArrayList<Noeud> noeuds;  // L'ensemble des nœuds

    // Le constructeur n'initialise pas l'entrée : la méthode calcule
    // s'en chargera avant chaque calcul. La sortie n'est pas initialisée
    // non plus pour éviter un problème de circularité.
    private Circuit() {
        this.noeuds = new ArrayList<Noeud>();
    }

    // Ajout d'un nœud à la liste
    private void ajouteNoeud(Noeud n) {
        noeuds.add(n);
    }


    // Donne la valeur de l'entrée, dont auront besoin certains nœuds
    public int litEntree() {
        return this.entree;
    }

    // Initialise la variable d'entrée et calcule le résultat.
    public int calcule(int e) {
        this.entree = e;
        return this.sortie.valeur();
    }

    // Création de nœuds avec ajout direct

    public Noeud creeEntree() {
        Noeud e = new Entree(this);
        this.ajouteNoeud(e);
        return e;
    }

    public Noeud creeConstante(int n) {
        Noeud c = new Constante(n);
        this.ajouteNoeud(c);
        return c;
    }
    public Noeud creeAddition(Noeud n1, Noeud n2) {
        Noeud a = new Addition(n1, n2);
        this.ajouteNoeud(a);
        return a;
    }
    public Noeud creeMultiplication(Noeud n1, Noeud n2) {
        Noeud m = new Multiplication(n1, n2);
        this.ajouteNoeud(m);
        return m;
    }
    public Noeud creeSoustraction(Noeud n1, Noeud n2) {
        Noeud m = new Soustraction(n1, n2);
        this.ajouteNoeud(m);
        return m;
    }
    public Noeud creeMultiplicationMemoisee(Noeud n1, Noeud n2) {
        Noeud m = new MultiplicationMemoisee(n1, n2);
        this.ajouteNoeud(m);
        return m;
    }

    // Partie III : Affichage
    public void affiche() {
        // Pour l'affichage on part de la sortie, qui va transmettre à ses
        // sources jusqu'à arriver aux constantes et aux entrées.
        // this.sortie.affiche();
        System.out.println(this.sortie);
    }

    // Partie IV : Des outils de diagnostic
    // Pour l'une et l'autre, on fait une boucle sur l'ensemble des nœuds, et on
    // utilise une méthode définie (ou héritée, ou redéfinie) dans chaque nœud.
    public int nbNoeudsArith() {
        int nb=0;
        for (Noeud n : this.noeuds) {
            if (n.estArithmetique()) { nb++; }
        }
        return nb;
    }
    public int nbOpEffectuees() {
        int nb=0;
        for (Noeud n : this.noeuds) {
            nb += n.nbOpEffectuees();
        }
        return nb;
    }


   //partie V
    public static Circuit expRapide(int p) {
        // On crée d'abord un circuit vide...
        Circuit c = new Circuit();
        Noeud n=c.creeEntree();
        for(int i=0;i<p;i++)
            n=c.creeMultiplication(n,n);
        c.sortie=n;
        return c;
    }

    public static Circuit expRapideMemoise(int p) {
        // On crée d'abord un circuit vide...
        Circuit c = new Circuit();
        Noeud n=c.creeEntree();
        for(int i=0;i<p;i++)
            n=c.creeMultiplicationMemoisee(n,n);
        c.sortie=n;
        return c;
    }




    // Pour réinitialiser, on fait une boucle sur les nœuds et on délègue à
    // chaque nœud la tâche de se réinitialiser si besoin.
    public void reInit() {
        for (Noeud n : noeuds) { n.reInit(); }
    }

}

//==========================================================
//   Nœuds
//==========================================================

abstract class Noeud {
    abstract public int valeur();
    // Partie IV
    // Par défaut, un nœud n'est pas une opation arithmétique. Tous les nœuds
    // vont hériter de cette définition qui renvoie {false}, le nœud NoeudBinaire
    // la redéfinira de manière à ce qu'elle renvoie {true},
    // et la multiplication mémoïsée héritera de cette redéfinition.
    public boolean estArithmetique() { return false; };
    // L'attribut comptant le nombre d'utilisations d'un mot est privé, mais
    // on fournit d'une part la possibilité d'obtenir sa valeur, et d'autre part
    // des moyens *limités* de la mettre à jour.
    // Dire à ceux qui écrivent sans mettre aucune restriction une méthode 
    //   public void setNbOp(int n) { this.nbOp = n; }
    // qu'ils tuent l'encapsulation.
    private int nbOp;
    public int nbOpEffectuees() { return this.nbOp; }
    public void incrNbOp() { this.nbOp++; }
    // Partie V
    // Par défaut, un nœud n'a rien à réinitialiser. Seuls les nœuds avec
    // mémoïsation ont réellement besoin de redéfinir cette méthode.
    public void reInit() {}
}


class Entree extends Noeud {
    private Circuit circuit;
    public Entree(Circuit c) {
        this.circuit = c;
    }
    public int valeur() {
        return this.circuit.litEntree();
    }
    public String toString() {
        return "x";
    }
}

class Constante extends Noeud {
    private int cst;
    public Constante(int c) { this.cst = c; }
    public int valeur() { return this.cst; }
    public String toString() {
        return String.valueOf(this.cst);
    }
}


class AdditionOld extends Noeud  {
    private Noeud source1, source2;
    public AdditionOld(Noeud e1, Noeud e2) { source1=e1 ;source2=e2; }
    public int valeur() {
        return (source1.valeur() + source2.valeur());
    }
}
class MultiplicationOld extends Noeud  {
    private Noeud source1, source2;
    public MultiplicationOld (Noeud e1, Noeud e2) { source1=e1 ;source2=e2; }
    public int valeur() {
        return (source1.valeur() * source2.valeur());
    }
}
// Partie II : Une hiérarchie de nœuds
abstract class NoeudBinaire extends Noeud {
    private Noeud source1, source2;
    public NoeudBinaire(Noeud s1, Noeud s2) {
        this.source1 = s1;
        this.source2 = s2;
    }
    // Les attributs {source*} étant privés, il faut fournir des méthodes pour
    // les récupérer. Cependant ici, personne n'a vraiment besoin d'obtenir la
    // source elle-même. On obtient une meilleure encapsulation en ne
    // fournissant qu'une méthode accédant à la chose utile : la valeur
    // calculée par {source*}.
    public int valeurSource1() {
        return this.source1.valeur();
    }
    public int valeurSource2() {
        return this.source2.valeur();
    }

    // Partie IV
    // Redéfinition de cette méthode dont la définition standard renvoie
    // {false}.
    public boolean estArithmetique() { return true; }
    abstract public String opString();
    public String toString() {
        return "(" + this.source1 + this.opString() + this.source2 + ")";
    }
}

class Addition extends NoeudBinaire {
    public Addition(Noeud e1, Noeud e2) { super(e1, e2); }
    public int valeur() {
        this.incrNbOp(); // Partie IV : on compte une opération
        return (this.valeurSource1() + this.valeurSource2());
    }
    public String opString() {
        return " + ";
    }
}

class Multiplication extends NoeudBinaire {
    public Multiplication(Noeud e1, Noeud e2) { super(e1, e2); }
    public int valeur() {
        this.incrNbOp(); // Partie IV : on compte une opération
        return (this.valeurSource1() * this.valeurSource2());
    }
    public String opString() {
        return " * ";
    }

}

// Rien de spécial ici, c'est juste pour montrer qu'il y a peu de choses à
// écrire une fois qu'on hérite de {NoeudBinaire}.
class Soustraction extends NoeudBinaire {
    public Soustraction(Noeud e1, Noeud e2) { super(e1, e2); }
    public int valeur() {
        this.incrNbOp(); // Partie IV
        return (this.valeurSource1() - this.valeurSource2());
    }
    public String opString() {
        return " - ";
    }
}

// Partie V : Mémoïsation

class MultiplicationMemoisee extends Multiplication {
    // Par défaut, un nœud n'est pas déjà évalué, et {mem} n'est pas défini.
    // Invariant des objets de cette classe : si {dejaEvalue} vaut {true},
    // alors {mem} contient une valeur.
    private boolean dejaEvalue=false;
    private int mem;
    public MultiplicationMemoisee(Noeud e1, Noeud e2) { super(e1, e2); }
    // Redéfinition de {valeur()}
    public int valeur() {
        if (!this.dejaEvalue) {
            // Si pas déjà évalué, on appelle la méthode {valeur()} de la
            // classe mère {Multiplication} pour définir l'attribut {mem}.
            this.mem = super.valeur();
            this.dejaEvalue = true;
        }
        // Dans tous les cas, à ce stade {mem} est défini, on renvoie sa
        // valeur.
        return this.mem;
    }

    // Pour la réinitialisation, pas besoin d'effacer explicitement {mem}, il
    // suffit de placer le booléen à {false} pour s'assurer que la méthode
    // {valeur()} ne peut plus aller voir l'ancienne valeur de {mem} (ni aucune
    // autre méthode, l'attribut {mem} étant évidemment privé).
    public void reInit() {
        this.dejaEvalue=false;
    }
}
