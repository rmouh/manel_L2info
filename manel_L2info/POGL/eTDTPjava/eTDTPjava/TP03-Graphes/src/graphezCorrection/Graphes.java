package graphezCorrection;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

// Classe principale
public class Graphes {
    public static void main(String[] args) {
        // Insérer des exemples ici : construit un graphe, lui ajoute
        // des sommets et des arêtes, et contrôler les couleurs, les
        // composantes connexes, les accessibilités...
        Graphe g = new Graphe();
        Sommet a = g.ajouteSommet("a");
        Sommet b = g.ajouteSommet("b");
        Sommet c = g.ajouteSommet("c");
        Sommet d = g.ajouteSommet("d");
        g.ajouteArete(a, b);
        g.ajouteArete(b, c);
        g.ajouteArete(a, d);
        g.ajouteArete(b, d);
        g.affiche();
    }
}

// Un graphe est constitué de sommets et d'arêtes.
// On veut notamment :
// - Créer un graphe vide
// - Ajouter des sommets
// - Ajouter des arêtes
// - Effectuer un coloriage
class Graphe {
    // Ensembles de sommets et d'arêtes, directement représentés comme tels.
    private Set<Sommet> sommets;
    private Set<Arete> aretes;
    // Une composante connexe est représentée par un sommet principal
    // Idée : utiliser l'algorithme Union-Find de Tarjan (dans ce corrigé,
    // il y a au passage les optimisations "union par rangs" et "compression
    // de chemins".
    private Set<Sommet> composantes;
    // Le nombre de composantes pourrait être recalculé à la demande plutôt
    // qu'enregistré, mais ça ne coûte pas cher.
    private int nbComposantes;

    // Graphe vide : pas de sommets, pas d'arêtes, pas de composantes.
    public Graphe() {
        sommets = new HashSet<Sommet>();
        aretes = new HashSet<Arete>();

    }

    public void affiche() {
        for (Sommet s : sommets) s.affiche();
    }

    // Ajout d'un sommet de nom [n]
    public Sommet ajouteSommet(String n) {
        Sommet s = new Sommet(n);
        sommets.add(s);
        return s;
    }

    // Ajout d'une arête entre deux sommets [s1] et [s2]
    public void ajouteArete(Sommet s1, Sommet s2) {
        Arete a = new Arete(s1, s2);
        aretes.add(a);
    }

    // Vérifie que tous deux sommets adjacents ont des couleurs différentes
    public boolean bienColore() {
        for (Sommet s : sommets) {
            if (!s.bienColore()) return false;
        }
        return true;
    }

    // Prend les sommets dans l'ordre, affecte à chacun la plus petite couleur
    // disponible, et renvoie la couleur maximale utilisée.
    public int colorie() {
        int couleurMax = 0;
        for (Sommet s : sommets) {
            couleurMax = Math.max(couleurMax, s.colorie());
        }
        return couleurMax;
    }

}

// Un sommet possède un nom et une couleur.
// On peut :
// - Créer un sommet
// - Lui ajouter des arêtes incidentes
// - Obtenir sa composante connexe
// - Lui donner une couleur
// Ici, les sommets représentent aussi des composantes connexes, on
// peut donc encore :
// - Unir la composante d'un sommet avec celle d'un autre sommet
class Sommet {
    private String nom;
    private int couleur = 0;
    // Ensemble des arêtes incidentes
    private Set<Arete> incidences;
    // Les distances sont représentés par une table associant
    // chaque sommet accessible à sa distance.
    private Map<Sommet, Integer> distances;

    public Sommet(String n) {
        nom = n;
        // Un sommet est créé sans arêtes incidentes.
        // Le seul sommet accessible est lui-même, via le chemin de longueur 0.
        incidences = new HashSet<Arete>();
        distances = new HashMap<Sommet, Integer>();
        distances.put(this, 0);
    }

    // Ajout d'une arête incidente.
    // Pour mettre à jour l'ensemble des sommets accessible dans le graphe,
    // il faut prendre tous les sommets accessibles depuis le sommet actuel,
    // tous les sommets accessibles depuis le sommet cible, et comparer le
    // nouveau chemin créé avec le meilleur chemin qui existait déjà.
    public void ajouteArete(Arete a) {
        incidences.add(a);

    }

    // Un sommet est bien coloré s'il n'a pas la même couleur que ses voisins.
    public boolean bienColore() {
        for (Arete a : incidences) {
            Sommet autre = a.autreExtremite(this);
            if (autre.couleur == this.couleur)
                return false;
        }
        return true;
    }

    // Affecte à un sommet la plus petite couleur pas déjà prise par ses
    // voisins.
    public int colorie() {
        this.couleur = 0;
        while (!this.bienColore())
            this.couleur++;
        return this.couleur;
    }

    public void afficheNom() {
        System.out.print(nom + " ");
    }

    public void affiche() {
        System.out.print(nom + couleur + ": [");
        for (Arete a : incidences) a.autreExtremite(this).afficheNom();
        System.out.print("]");
        for (Map.Entry<Sommet, Integer> accS2 : this.distances.entrySet())
        { accS2.getKey().afficheNom(); System.out.print(accS2.getValue() +" ,");  }
        for (Map.Entry<Sommet, Integer> accS2 : this.distances.entrySet())
        { routage( accS2.getKey()).affiche(); }
        System.out.println("__");
    }
    public Set<Sommet> sommetVoisin() {
        Set<Sommet> result= new HashSet<>();
         for (Arete a : incidences) result.add(a.autreExtremite(this)) ;
       return result;
    }

    Set<Sommet> accessibles() {
        return new HashSet<>(distances.keySet());
    }

    void updateDistances(Sommet s, Arete a) {
        for (Sommet s1 : accessibles())
            for (Sommet s2 : s.accessibles()) {
                int newDist =  distances.get(s1)
                        + a.getPoids()
                        + s.distances.get(s2);
                if (!s1.distances.containsKey(s2))
                    s1.distances.put(s2, newDist);
                else if (s1.distances.get(s2) > newDist)
                    s1.distances.replace(s2, newDist);
                            }

    }
    Chemin routage(Sommet s){
        if(s==this) return new Chemin(this);
        int dcur= -1; Sommet scur=null;Arete  acur=null;
         for(Arete a:incidences){
                Sommet s2= a.autreExtremite(this);
                if(dcur==-1 || s2.distances.get(s)<dcur)
             {
                 scur=s2;acur=a;dcur=s2.distances.get(s);
             }
            }
         return(scur.routage(s).ajouteArete(acur));
    }

}

// Une arête a deux extrémités (ordre arbitraire, on regarde des graphes
// non orientés).
// On peut :
// - Savoir si un sommet est une extrémité
// - Obtenir l'autre extrémité si on en a déjà une
// - Obtenir le poids de l'arête (par défaut, 1)
class Arete {
    // On représente les extrémités par un tableau à deux cases
    // Comme souvent ici : c'est une possibilité parmi d'autres
    private Sommet[] extremites;

    public Arete(Sommet s1, Sommet s2) {
        // On range les extrémité dans l'ordre donné par les paramètres
        // Variante : on les classe en fonction d'un ordre (pas utile ici)
        extremites = new Sommet[2];
        extremites[0] = s1;
        extremites[1] = s2;
        // On inscrit l'arête dans les listes d'incidence de ses extrémités
        s1.ajouteArete(this);
        s2.ajouteArete(this);
        s2.colorie();
        s1.updateDistances(s2, this);
        s2.updateDistances(s1, this);

    }

    // Donne l'autre extrémité
    // Pré-condition : [s] est l'une des extrémités
    public Sommet autreExtremite(Sommet s) {
        if (s == extremites[0]) return extremites[1];
        else return extremites[0];
    }

    // Vérifie qu'un sommet est une extrémité
    public boolean admetExtremite(Sommet s) {
        return (s == extremites[0] || s == extremites[1]);
    }

    // Pour une arête non pondérée, le poids est 1
    public int getPoids() {
        return 1;
    }

}

// Une arête pondérée est une arête avec en plus un poids.
class AretePonderee extends Arete {
    private int poids;

    public AretePonderee(Sommet s1, Sommet s2, int p) {
        super(s1, s2);
        poids = p;
    }

    public int getPoids() {
        return poids;
    }
}


// Un chemin est un ensemble d'arêtes entre une source et une destination
// On peut :
// - Créer un chemin vide
// - Obtenir la longueur du chemin
// - Ajouter une arête compatible à la fin du chemin
// - Concaténer un chemin à la fin du chemin
// - Obtenir le chemin inverse
class Chemin {
    protected ArrayList<Arete> aretes;
    private Sommet source;
    private Sommet destination;

    // Le chemin vide est quand même défini au niveau d'un sommet
    public Chemin(Sommet s) {
        aretes = new ArrayList<Arete>();
        source = s;
        destination = s;
    }

    // La longueur est la somme des poids des chemins
    public int longueur() {
        int l = 0;
        for (Arete a : aretes) {
            l += a.getPoids();
        }
        return l;
    }

    // Ajout d'une arête à la fin du chemin
    // - renvoie vraie si l'arête est compatible
    // - renvoie faux (et ne fait rien) sinon
    public Chemin   ajouteArete(Arete a) {
        if (a.admetExtremite(source)) {
            aretes.add(0,a);
            source = a.autreExtremite(source);
        }
        return this;
    }



    public void affiche() {
        Sommet s = source;
        System.out.print("(" + longueur() + " ");
        for (Arete a : aretes) {
            s = a.autreExtremite(s);
            s.afficheNom();
            System.out.print(":");
        }
        System.out.print(")");
    }
}

