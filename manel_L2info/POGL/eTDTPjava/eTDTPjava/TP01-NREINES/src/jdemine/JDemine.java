package jdemine; /**
 * POGL : programmation objet et génie logiciel
 * L'objectif ici est de réviser les principaux
 * éléments de Java que vous avez  abordés .
 * On développe pour cela une mini-application
 * [JDemine], qui reprend le principe du jeu du démineur.
 * Ce fichier commenté tient lieu de notes de cours.
 * <p>
 * Ce fichier définit trois classes :
 * - [JDemine] est la classe principale de l'application
 * - [Terrain] modélise la grille du jeu
 * - [Case] modélise les cases de cette grille
 * On fournit avec un petit paquet [IG] qui donne des éléments
 * d'interaction avec la bibliothèque graphique de Java (Swing).
 * <p>
 * En règle générale, chaque fichier contient une classe,
 * les classes [Terrain] et [Case] seraient  placées dans deux
 * fichiers [Terrain.java] et [Case.java].
 * Ici on conserve tout dans le même fichier pour une
 * présentation linéaire de ces notes.
 * <p>
 * Avant de commencer : on intègre dans notre développement
 * quelques classes qui seront en charge de l'interface graphique :
 * - la classe [java.awt.Color] de la bibliothèque standard de Java
 * - les classes [Fenetre], [Grille] et [ZoneCliquable] du paquet [IG]
 */



import java.awt.Color;import IG.Fenetre;
import IG.Grille;import IG.ZoneCliquable;

/**
 Un fichier de code Java porte l'extention [.java], et contient
 en général une unique classe publique, de nom correspondant,
 introduite par la mention
 public class C { ... }
 où [C] est le nom donné à la classe.

 Avant exécution, le programme doit être compilé, c'est-à-dire
 traduit en  langage machine. En l'occurrence, la traduction
 ne se fait pas vers le  langage assembleur de votre machine
 physique, mais vers le "bytecode" de  la machine virtuelle
 Java (JVM). Cette machine virtuelle simule un  ordinateur.
 Cette technique facilite la diffusion des applications Java :
 les développeurs n'ont besoin de s'adapter qu'à une
 architecture cible (la  JVM), sans se soucier de
 l'environnement réel des utilisateurs.

 Dans un terminal, on demande la compilation avec la commande
 javac JDemine.java
 Au passage, cette commande vérifie certains éléments  du
 programme, et indique les éventuelles erreurs relevées.
 Le programme compilé prend la forme d'un nouveau fichier
 [JDemine.class]. Dans Eclipse, ceci correspond à  "Build".
 */

public class JDemine {


    /**
     La méthode [main] décrit le programme principal.
     Sa signature [public static void main(String[])] est fixée par
     convention, nous détaillerons sa signification au prochain cours.

     Dans un terminal on exécute le programme, c'est-à-dire le code de
     la méthode [main] par la commande
     java JDemine
     Attention, ceci ne peut se faire qu'en présence du fichier
     [JDemine.class], c'est-à-dire après la compilation.
     Dans Eclipse, ceci correspond à la commande "Run".
     */
    public static void main(String[] args) {
        /**
         On définit une variable locale [x] de type [T] par
         la déclaration
         T x;
         On peut en outre affecter une valeur à cette variable en étendant
         la ligne de la façon suivante
         T x = ...
         En l'occurrence, on crée un nouvel objet (on dit aussi une instance)
         avec le mot clé [new], suivi du nom de la classe concernée appliqué
         à d'éventuels paramètres.

         On obtient une fenêtre graphique [f] intitulée "JDemine" et un
         terrain [t] de hauteur 6 et de largeur 9.
         */
        Fenetre f = new Fenetre("JDemine");
        Terrain t = new Terrain(6, 9);
        /**
         On appelle une méthode [m] d'un objet [obj] avec une expression de
         la forme [obj.m(...)], en fournissant entre parenthèses les
         paramètres donnés à [m].

         Les méthodes [ajouteElement] et [dessineFenetre] sont définies dans
         la classe [Fenetre]. La première permet d'intégrer de nouveaux
         éléments (ici le terrain [t]) à l'affichage, et la deuxième provoque
         l'affichage de la fenêtre et de l'ensemble de ses éléments.
         */
        f.ajouteElement(t);
        f.dessineFenetre();
    }
}

/**
 Lors de la définition d'une nouvelle classe [C], on peut déclarer qu'elle
 étend une classe pré-existante [D], avec la mention
 class C extends D { ... }
 Dans ce cas, les fonctionnalités de la classe [D] sont intégrées à la
 classe [C]. Les implications de cette déclaration sont plus subtiles qu'il
 n'y paraît, on reviendra en détail plus tard dans le semestre sur ce que
 cela signifie exactement (deuxième quinzaine de mars).

 Ici, [Terrain] va hériter des fonctionnalités de la classe [Grille],
 c'est-à-dire d'une capacité à intégrer plusieurs éléments graphique disposés
 selon une grille à deux dimensions.
 */
class Terrain extends Grille {

    /**
     La définition d'une classe contient trois types d'éléments :
     - des attributs, qui sont des données attachées à chaque instance de
     la classe
     - des constructeurs, qui permettent de créer et d'initialiser des
     instances de la classe
     - des méthodes, qui permettent d'agir sur les instances
     */

    /**
     La déclaration d'un attribut [a] de type [T] a la forme
     T a;
     Cette déclaration est généralement précédée d'une indication de
     visibilité [private], indiquant que l'attribut n'est pas accessible
     depuis les autres classes. On peut déclarer plusieurs attributs du
     même type à la fois en les séparant par des virgules.

     Note sur les conventions de nommage : le code n'étant pas uniquement
     écrit pour être exécuté, mais aussi pour être lu, on préférera utiliser
     des noms significatifs. Quand un nom d'attribut est composé de plusieurs
     mots, on met en majuscule la première lettre de chaque mot (sauf pour
     le premier mot, seuls les noms de classe commençant par une majuscule).

     Ici on définit trois attributs entiers indiquant la hauteur et la
     largeur du terrain, ainsi que le nombre total de cases piégées (en l'état
     du programme, cette dernière information n'est pas renseignée).
     */
    private int hauteur, largeur;
    private int nombrePiegees;

    /**
     On note [ T[] ] le type des tableaux d'éléments de type [T].
     Le type d'un tableau à deux dimensions est donc noté [ T[][] ], avec
     un parenthésage implicite [ (T[])[] ] : on parle d'un tableau dont les
     éléments sont eux mêmes des tableaux (d'élément de type [T]).
     */
    private Case[][] lesCases;

    /**
     Un constructeur porte systématiquement le nom de sa classe, a
     généralement une visibilité [public], et peut prendre des paramètres
     dont il précise les types.

     Ici le constructeur de la classe [Terrain] attend deux paramètres
     entiers [h] et [l] définissant la hauteur et la largeur du terrain à
     créer.
     */
    public Terrain(int h, int l) {
        /**
         Lorsqu'un classe [C] étend une classe pré-existante [D], les
         constructeurs de [C] peuvent commencer par un appel aux
         constructeurs de [D], qui s'écrit
         super( ... );
         Ici, on transmet au constructeur de la classe [Grille] les
         dimensions de la grille.
         */
        super(h, l);
        /**
         Le constructeur est ensuite en charge d'initialiser les attributs
         de l'objet en cours de création.

         Un attribut [a] de l'objet courant est désigné par [this.a].
         Le [this] est optionnel mais son usage est recommandé.

         À savoir : un attribut non initialisé aura généralement par défaut
         une valeur équivalente à [0] ou [null]. On recommande de ne pas se
         reposer sur ce fait.
         */
        this.hauteur = h;
        this.largeur = l;
        /**
         On crée un tableau de [n] éléments de type [T] avec l'expression
         new T[n]
         Si le tableau a plusieurs dimensions, on enchaîne les crochets.
         Cette déclaration ne créé que le tableau lui-même, et n'initialise
         pas les éléments qu'il contient.

         Ici, on initialise l'attribut [terrain] avec un tableau de hauteur
         [h] et de largeur [l], destiné à contenir des objets de type [Case]
         qui ne sont pas encore définis mais qu'on va ensuite pouvoir
         ajouter.
         */
        this.lesCases = new Case[h][l];

        /**
         L'initialisation de toutes les cases du terrain peut être considérée
         comme une tâche non élémentaire, on va donc définir pour cela une
         méthode dédiée, qu'on appelle ici.

         On appelle une méthode [m] de l'objet courant avec l'expression
         this.m( ... )

         Ce qui manquait dans le direct : la ligne suivante était commentée
         (j'avais dit en passant "on s'en occupera plus tard"), aucune case
         n'était donc ajoutée au terrain.
         */
        initialiserTerrain();
    }

    /**
     La définition d'une méthode ressemble à celle d'un constructeur, mais
     permet d'utiliser un nom quelconque et précise un éventuel type de
     résultat (ou indique [void] quand aucun résultat n'est produit).

     Ici, la méthode [initialiserTerrain] va créer les cases composant le
     terrain. Elle ne prend aucun paramètre et ne renvoie aucun résultat
     (mais elle agit en modifiant l'attribut [terrain]).
     Comme cette méthode est destinée à un usage interne, on peut restreindre
     sa visibilité avec la déclaration [private].
     */
    private void initialiserTerrain() {
        /**
         La boucle inconditionnelle [for] permet la répétition d'un bloc
         de code. On y déclare une variable de boucle [int x], sa valeur
         initiale [=0], une condition de poursuite de boucle [x<this.hauteur]
         à vérifier avant chaque tour et une instruction de mise à jour de la
         variable [x++] à appliquer à la fin de chaque tour.

         Ici, on emboîte deux boucles, dont les indices [x] et [y]
         correspondent aux ordonnées et aux abscisses de notre grille à deux
         dimensions. On parcourt ainsi chaque ligne du tableau, et chaque
         élément de chaque ligne, pour initialiser l'ensemble du tableau.

         Remarque : indépendamment de nos attributs [hauteur] et [largeur],
         on peut connaître la longueur d'un tableau [t] avec l'expression
         [t.length]. Dans un tableau de tableaux, on obtient donc la longueur
         de la [x]-ème ligne avec [t[x].length].
         */
        for (int x = 0; x < lesCases.length; x++) {
            for (int y = 0; y < lesCases[x].length; y++) {
                /**
                 Définition d'une variable booléenne [piegee] non initialisée,
                 puis affectation d'une valeur à cette variable en fonction
                 d'un tirage aléatoire, et utilisation de la variable [piegee]
                 comme paramètre dans la construction d'une nouvelle case.

                 La méthode [Math.random()] renvoie un nombre flottant de
                 type [double], compris entre 0 et 1.
                 */
                boolean piegee;
                if (Math.random() < 0.25) {
                    piegee = true;
                } else {
                    piegee = false;
                }
                Case c = new Case(x, y, piegee);
                /**
                 On affecte la valeur d'une expression [e] à l'indice [i]
                 d'un tableau [t] avec une instruction
                 t[i] = e;
                 En cas de tableaux à plusieurs dimensions on enchaîne les
                 indices entre crochets.

                 En l'occurrence, la prochaine instruction place la case [c]
                 à l'indice [y] de la [x]-ème ligne de [terrain].
                 L'instruction suivante ajoute la case à la liste des éléments
                 à afficher.
                 */
                lesCases[x][y] = c;
                ajouteElement(c);
                /**
                 Alternativement, on aurait pu directement donner comme
                 paramètre au constructeur [Case] une expression calculant
                 un booléen.
                 Case c = new Case(x, y, Math.random() < 0.25);

                 On a également une forme générale d'expression conditionnelle
                 qui combine un test [c] et deux expressions [e₁] et [e₂] :
                 (c)?e₁:e₂
                 Si le test [c] vaut [true], alors le résultat sera celui de
                 l'expression [e₁], sinon le résultat sera celui de
                 l'expression [e₂]. L'expression booléenne
                 Math.random() < 0.25
                 est donc équivalente à
                 (Math.random()<0.25)?true:false
                 ou encore à
                 (Math.random()>=0.25)?false:true
                 */
            }
        }
        /**
         Les accès à des attributs, indices de tableaux ou méthodes
         peuvent s'enchaîner. Pour chaque case du terrain de coordonnées
         [x, y] les instructions suivantes accèdent à l'attribut
         [nombreVoisinesPiegees] de cette case obtenue dans le tableau
         [terrain] de l'objet courant.

         Remarque : l'attribut [nombreVoisinesPiegees] est déclaré dans la
         classe [Case] avec la visibilité [protected] et est donc accessible.

         La méthode [compteVoisines] est définie en dessous, et indique le
         nombre de voisines piégées. L'invocation de méthode
         compteVoisines(x, y)
         est équivalent à
         this.compteVoisines(x, y)
         */
        for (int x = 0; x < this.hauteur; x++) {
            for (int y = 0; y < this.largeur; y++) {
                this.lesCases[x][y].nombreVoisinesPiegees = compteVoisines(x, y);
            }
        }
    }

    /**
     Méthode renvoyant le nombre de voisines piégées d'une case donnée par
     ses coordonnées.
     À nouveau, son usage exclusivement interne permet de la garder privée.
     */

    private int compteVoisines(int x, int y) {
        // Initialisation d'une variable à 0
        int nb = 0;
        // Pour chaque case dans le carré de 3x3 autour des coordonnées
        // considérées...
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // si ces coordonnées sont valides et correspondent à une
                // case piégée, alors on incrémente le compteur.
                if (coordonneesValides(i, j) &&
                        this.lesCases[i][j].estPiegee()) {
                    nb++;
                }
                /**
                 Remarques : l'opérateur [&&] est paresseux, et n'évaluera
                 pas son deuxième argument si le premier vaut déjà [false].
                 Le code précédent est donc équivalent à :
                 if (coordonneesValides(i, j)) {
                 if (this.terrain[i][j].estPiegee()) { nb++; }
                 }
                 et on s'assure qu'on n'essaiera jamais de lire le tableau
                 à des coordonnées inadaptées.
                 */
            }
        }
        // À la fin, on renvoie le nombre obtenu
        return nb;
    }
    /**
     Remarque : contrairement à ce qui était prétendu au début, l'invocation
     [compteVoisines(x, y)] ne renvoie pas le nombre de voisines piégées de
     la case de coordonnées [x, y], mais compte aussi cette case.

     Question : pourquoi peut-on se permettre cette approximation ?
     On reparlera de ce genre de considérations à partir de mi-février.
     */

    /**
     Des coordonnées sont valides si elles sont convenablement encadrées
     par les dimensions du terrain.
     Rappel : les indices d'un tableau [t] vont de [0] à [t.length - 1].
     */
    private boolean coordonneesValides(int x, int y) {
        return (x >= 0 && y >= 0 && x < this.hauteur && y < this.largeur);
    }


    /**
     La méthode [main] des classes annexes peut être utilisée pour effectuer
     de petits tests unitaires.
     Après compilation, on peut exécuter ce test en exécutant la classe
     actuelle, c'est-à-dire [Terrain]. Dans un terminal, cela se traduit
     par la ligne de commande
     java Terrain

     Consigne générale : incluez ce genre de tests dans tous vos
     développements.
     */
    public static void main(String[] args) {
        Terrain t = new Terrain(3, 4);
        t.lesCases[0][0] = new Case(0, 0, true);
        t.lesCases[0][1] = new Case(0, 1, false);
        t.lesCases[0][2] = new Case(0, 2, false);
        t.lesCases[1][0] = new Case(1, 0, false);
        t.lesCases[1][1] = new Case(1, 1, true);
        t.lesCases[1][2] = new Case(1, 2, false);
        // Affichage d'un compte rendu du test.
        System.out.println("Voisines de (0, 1): "
                + t.compteVoisines(0, 1)
                + " (attendu : 2)");
        /**
         Variante possible : utiliser une assertion pour interrompre le
         programme en cas d'échec.
         assert t.compteVoisines(0, 1) == 2 : "Erreur compte voisines";
         System.out.println("Test classe Terrain OK");

         Dans ce cas, il faut exécuter le
         programme avec l'option -ea ("execute assertions"), c'est-à-dire
         java -ea Terrain
         */
    }
}

/**
 La classe [Case] étend la classe abstraite [ZoneCliquable] fournie dans
 l'interface [IG]. Cela va permettre de rendre les cases réactives aux clics,
 mais demande de définir deux méthodes [clicGauche] et [clicDroit].
 Au passage, l'extension de [ZoneCliquable] donne aussi accès à une méthode
 [changeTexte] permettant de choisir l'éventuel texte affiché dans la case.
 */
class Case extends ZoneCliquable {

    // À chaque case on associe ses coordonnées ainsi qu'un booléen indiquant
    // la présence ou l'absence d'une mine.
    private int x, y;
    private boolean piegee;
    /**
     On peut fournir une valeur d'initialisation par défaut lors de la
     déclaration d'un attribut, de la même façon que pour donner une valeur
     à une variable locale.

     Ici on déclare un attribut indiquant si la case a déjà été cliquée,
     initialisé à [false] (cette information n'est pas utilisée dans la
     version actuelle).
     */
    private boolean dejaCliquee = false;
    /**
     Pour rendre un attribut accessible depuis les autres classes de la même
     application (formellement, du même paquet), on peut utiliser la
     visibilité [protected], qui est aussi la visibilité par défaut de Java.

     Ici, ceci permet à la classe [Terrain] de précalculer les nombres de
     voisines piégées de chaque case du plateau lors de l'initialisation.
     */
    protected int nombreVoisinesPiegees;

    // Le constructeur prend en paramètres les coordonnées de la case et
    // un booléen indiquant la présence ou l'absence d'une mine.
    public Case(int x, int y, boolean piegee) {
        /**
         Première étape : appeler le constructeur de [ZoneCliquable],
         auquel on transmet le texte à afficher initialemenet (la chaîne
         vide) ainsi que les dimensions de la case (40x40 pixels).
         */
        super("", 40, 40);
        /**
         Deuxième étape : initialisation des attributs avec les valeurs
         fournies.
         */
        this.x = x;
        this.y = y;
        this.piegee = piegee;
        /**
         Il n'est pas nécessaire d'initialiser l'attribut [dejaCliquee],
         puisque la valeur [false] lui a été fournie lors de sa déclaration.
         Sinon, on aurait pu l'écrire
         this.dejaCliquee = false;
         */
    }

    /**
     La méthode publique [estPiegee] permet d'accéder à la valeur de
     l'attribut [piegee]. L'attribut [piegee] étant déclaré privé, on ne
     peut y accéder librement que depuis l'intérieur de la classe [Case],
     et les méthodes de la classe [Terrain] par exemple sont forcées
     d'utiliser cette méthode d'accès.

     Ce genre de méthodes, qu'on appelle aussi "getter", est à introduire
     en fonction des besoins.
     */
    public boolean estPiegee() {
        return this.piegee;
    }

    // La méthode [clicGauche] colore la case en rouge si la case était
    // piégée, et affiche sinon le nombre de voisines piégées.
    public void clicGauche() {

        if (this.piegee) {
            /**
             La méthode [setBackground] est récupérée de la bibliothèque
             standard de Java via [ZoneCliquable], et change la couleur de
             fond d'un élément graphique.
             */
            setBackground(Color.RED);
        } else {
            /**
             Remarque : la méthode [changeTexte] attend en paramètre une
             chaîne de caractères. Or, le nombre de voisines piégées est
             un nombre entier. On applique donc une fonction de conversion
             proposée par la bibliothèque [Integer] de Java.
             */
            this.changeTexte(Integer.toString(this.nombreVoisinesPiegees));
        }
    }

    // La méthode [clicDroit] colore la case en bleu.
    public void clicDroit() {
        setBackground(Color.BLUE);
    }
}
