package listeSquelette;

import java.util.Iterator;


public class DLL<T> implements Iterable<T> {

    // L'ancre est de type [Block] et est immuable
    private final Block anchor;
    private int size;
    public DLL() {
        // On crée un nouveau bloc [b] qui jouera le rôle de l'ancre.
        // Som attribut [elt] vaut [null] et le bloc est son propre successeur
        // et son propre prédécesseur.
        Block b = new Block(null);
        b.nextBlock = b;
        b.prevBlock = b;
        // On définit l'ancre comme étant ce bloc [b]
        this.anchor = b;
        // Indépendamment, on initialise la taille
        this.size = 0;

    }

    class Block {
        // Un bloc contient un élément fixé à la création, donc immuable
        private final T elt;
        // Les attributs [prevBlock] et [nextBlock] pointent vers les blocs
        // précédent et suivant
        private Block prevBlock, nextBlock;
        public Block(T e) {
            this.elt = e;
            /**
             On a initialisé uniquement [elt] ici
             Cela signifie qu'il faudra ajuster [prevBlock] et [nextBlock] au
             moment d'intégrer le bloc dans sa liste
             */
        }
    }


    // Cadeau : un itérateur sur les éléments de la liste
    public Iterator<T> iterator() { return new DLLIterator(); }
    class DLLIterator implements Iterator<T> {
        private Block currentBlock;
        public DLLIterator() {
            this.currentBlock = anchor.nextBlock;
        }
        public boolean hasNext() {
            return this.currentBlock != anchor;
        }
        public T next() {
            T elt = this.currentBlock.elt;
            this.currentBlock = this.currentBlock.nextBlock;
            return elt;
        }
    }
    /**
     Méthode d'affichage : la liste étant iterable, on peut simplement faire
     appel à une boucle [for] de type "for each".
     Rappel : cela correspond à la création d'un objet itérateur et à
     l'itération sur le prochain élément jusqu'à épuisement de la collection
     */
    public void print() {
        for (T elt: this) { System.out.print(elt + " "); }
        System.out.println();
    }

    public static void main(String[] args) {
        /**
         Premier test interne : la liste vide []
         Le constructeur fabrique exactement ceci : une liste contenant
         uniquement une ancre qui est son propre successeur et son propre
         prédécesseur
         */
        DLL<Integer> emptyList = new DLL<>();
        emptyList.print();
        /**
         Deuxième test interne : la liste [4, 2, 1]
         On crée d'abord une liste vide et trois blocs contenant chacun l'un
         des éléments
         */
        DLL<Integer> smallList = new DLL<>();
        smallList.add(4);
        smallList.add(2);
        smallList.add(1);
        smallList.print();

    }

    /**
     Définition d'une méthode d'ajout d'un élément à la fin de la liste,
     traduite en l'ajout d'un élément avant le bloc ancre
     */
    public void add(T elt) {
        // Création d'un bloc [b] portant un élément [elt], sans branchement
        // des pointeurs prédécesseur et successeur pour l'instant
        Block b = new Block(elt);
        // Méthode auxiliaire qui intercale [b] antre l'ancre et son
        // prédécesseur (c'est-à-dire le dernier élément actuel)
        this.addBefore(b, this.anchor);
        this.size++;
        assert checkInvariants() : "Problème après addBefore";
    }
    /**
     Méthode auxiliaire qui définit ou modifie les pointeurs successeur et
     prédécesseur pour intercaler le bloc [addedBlock] entre le bloc
     [refBlock] et son prédécesseur actuel
     */
    private void addBefore(Block addedBlock, Block refBlock) {
        addedBlock.nextBlock = refBlock;
        addedBlock.prevBlock = refBlock.prevBlock;
        refBlock.prevBlock.nextBlock = addedBlock;
        refBlock.prevBlock = addedBlock;
    }


    /**
     Vérification des invariants, a
     */
    private boolean checkInvariants() {
        //a completer
        return true;
    }


    public void swap12() {
        Block first=this.anchor.nextBlock;
        Block second=first.nextBlock;
        Block third=second.nextBlock;
        anchor.nextBlock = second;
        second.nextBlock = first;
        first .nextBlock=third;
        assert checkInvariants() : "Problème après swap";
    }





}