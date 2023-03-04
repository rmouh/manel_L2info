package grapheSquelette;

import java.util.*;

// Classe principale
public class Graphes {
	public static void main(String[] args) {
		// Insérer des exemples ici : construit un graphe, lui ajoute
		// des sommets et des arêtes, et contrôler les couleurs, les
		// composantes connexes, les accessibilités...
		Graphe g=new Graphe();
		Sommet a= g.ajouteSommet("a");
		Sommet b=g.ajouteSommet("b");
		Sommet c=g.ajouteSommet("c");
		Sommet d=g.ajouteSommet("d");
		g.ajouteArete(a,b, 1);
		g.ajouteArete(b,c, 2);
		g.ajouteArete(a,d, 1);
		g.ajouteArete(b,d, 3);
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
	private Set<Integer> allColors;
	private int colCount = 0;



	// Graphe vide : pas de sommets, pas d'arêtes, pas de composantes.
	public Graphe() {
		sommets = new HashSet<Sommet>();
		aretes = new HashSet<Arete>();
		allColors = new HashSet<>();

	}

	public void affiche(){
		Set<Sommet> voisin = new HashSet<>();
		for(Sommet s:sommets){
			voisin = s.Voisins();
			s.affiche();
			System.out.print(" [ ");
			for (Sommet ss : voisin)
				ss.afficheNom();
			System.out.println(" ] " );
		}


		}

	// Ajout d'un sommet de nom [n]
	public Sommet ajouteSommet(String n) {
		Sommet s = new Sommet(  n);
		sommets.add(s);
		allColors.add(s.getCouleur());
		return s;
	}

	// Ajout d'une arête entre deux sommets [s1] et [s2]
	public void ajouteArete(Sommet s1, Sommet s2, int d) {
		int couleur;
		Set<Integer> voisinsCouleurs = new HashSet<>();
		Arete a = new Arete(s1, s2, d);
		if (s1.getCouleur() == s2.getCouleur()) {
			couleur = s1.getCouleur();
			for (Integer col : allColors) {
				if (!col.equals(couleur)) {
					s2.setCouleur(col);
				}
			}if (s1.getCouleur() == s2.getCouleur()) {
				s2.setCouleur(++colCount);
				//System.out.println(s1.getCouleur() + "    "+ s2.getCouleur() +" "+ colCount );
			}
		}aretes.add(a);
	}





}

// Un sommet possède un nom et une couleur.
// On peut :
// - Créer un sommet
// - Lui ajouter des arêtes incidentes
// - recalculer sa couleur
class Sommet {
	private String nom;
	private int couleur=0;
	// Ensemble des arêtes incidentes
	private Set<Arete> incidences;
	//private Set<Integer> voisinsCouleurs;
	private Map<Sommet, Integer> distances;


	public Sommet( String n) {
		nom = n;
		// Un sommet est créé sans arêtes incidentes.
		// Le seul sommet accessible est lui-même, via le chemin vide.
		incidences = new HashSet<Arete>();
		//voisinsCouleurs = new HashSet<>();
		distances = new HashMap<>();
	}
	public void addDistance(Sommet s, int d)
	{
		distances.put(s, d);
	}
	public int getCouleur()
	{
		return (this.couleur);
	}
	public void setCouleur(int cl)
	{
		this.couleur = cl;
	}

	public Set<Sommet> Voisins()
	{
		Set<Sommet> voisin = new HashSet<>();
		for (Arete a : incidences)
		{
			if (a.admetExtremite(this))
				voisin.add(a.autreExtremite(this));
		}
		return(voisin);
	}
	public Set<Integer> voisinCouleurs()
	{
		Set<Integer> couleurs = new HashSet<>();
		for(Arete a : incidences)
		{
			if (a.admetExtremite(this))
				couleurs.add(a.autreExtremite(this).getCouleur());
		}
		return(couleurs);
	}



	public void ajouteArete(Arete a) {
		incidences.add(a);
//		if ()

	}


	public void afficheNom() {System.out.print(nom+"  { col:  " + this.getCouleur() +"  }");}

	public void affiche() {
		System.out.print(nom);
		System.out.print("__");
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
	private int Distance;

	public Arete(Sommet s1, Sommet s2, int d) {
		// On range les extrémité dans l'ordre donné par les paramètres
		// Variante : on les classe en fonction d'un ordre (pas utile ici)
		extremites = new Sommet[2];
		extremites[0] = s1;
		extremites[1] = s2;
		// On inscrit l'arête dans les listes d'incidence de ses extrémités
		s1.ajouteArete(this);
		s2.ajouteArete(this);
		Distance = d;
		s1.addDistance(s2, d);
		s2.addDistance(s1, d);


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
	public int getPoids() { return 1; }

}
