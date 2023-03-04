package listzCorrection;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Consumer;
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
        assert checkInvariants() : "Mauvaise initialisation";
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
        assert emptyList.checkInvariants();
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
        assert smallList.checkInvariants();
      //  smallList.swap12();         smallList.print();
        //assert smallList.checkInvariants();
        smallList.removeBlock(smallList.anchor.nextBlock); smallList.print();
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

    // Comptage des éléments, méthode auxiliaire pour vérifier l'invariant 1/
    private int countElements() {
        int count = 0;
        for (T elt: this) { count++; }
        return count;
    }

     /**
      Vérification des invariants, à l'aide des méthodes auxiliaires
      précédentes
      */
     private boolean checkInvariants() {
         return
                 // Le champ [size] doit valoir le nombre de blocs utiles
                 this.size == countElements()
                         // Le champ [elt] doit être indéfini pour l'ancre, et défini
                         // pour les blocs utiles
                         && forAllBlocks(b -> (b==anchor)?b.elt==null:b.elt!=null)
                         // Chaque bloc est le prédécesseur de son successeur
                         && forAllBlocks(b -> b.nextBlock.prevBlock == b)        ;
     }
     /**
      A priori ce test est suffisant, dans le sens où tout verdict obtenu est
      correct. Question : existe-t-il une situation dans laquelle cette
      vérification ne termine pas ? Oui si y a pas d'ancres
      */


     /**
      L'interface [java.util.function.Predicate] contient principalement une
      méthode [test] qui renvoie un booléen. C'est à cet méthode que va faire
      appel la méthode [forAllBlocks].

      https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html
      */

     /**
       Version simple de [forAllBlocks], où on mélange dans un même code
       l'itération sur les blocs (boucle et passage d'un bloc à son successeur)
       et la logique (test du prédicat, et résultat).
    */
    private boolean forAllBlocksSimple(Predicate<Block> p) {
        // Une variable [currentBlock] mémorise le bloc qu'on est en train
        // de considérer. On commence par l'ancre, qui doit aussi être analysée
        Block currentBlock = this.anchor;
        do {
            // Si le bloc courant ne valide pas le prédicat, alors on arrête
            // l'itération et on renvoie faux
            if (!p.test(currentBlock)) { return false; }
            // Sinon on passe au bloc suivant
            currentBlock = currentBlock.nextBlock;
        // Et on continue jusqu'à trouver à nouveau l'ancre
        } while (currentBlock != this.anchor);
        // Si on arrive jusque là, renvoyer vrai
        return true;
        /**
           Remarque : on utilise ici une boucle [do/while] plutôt que [while]
           pour assurer que l'ancre est bien examinée quoiqu'il arrive
        */
    }

    /**
       Variante possible pour [forAllBlocks], qui séparer les deux aspects que
       sont l'itération et la logique.

       On définit d'abord une méthode produisant un itérateur pour les blocs.
       Remarquez que la définition de l'itérateur lui-même, avec ses méthodes
       [hasNext] et [next], est faite à l'intérieur de la méthode produisant
       l'itérateur. On utilise ici le mécanisme des classes internes anonymes,
       ce qui est possible car l'itérateur qu'on définit ici n'a pas vocation
       à être utilisé ailleurs.
 */
           private Iterator<Block> blockIterator() {
               return new Iterator<Block>() {
                   private Block currentBlock = anchor.nextBlock;
                   public boolean hasNext() {
                       return this.currentBlock != anchor;
                   }
                   public Block next() {
                       Block b = this.currentBlock;
                       this.currentBlock = this.currentBlock.nextBlock;
                       return b;
                   }        
               };
           }

 /*      On définit enfin une méthode s'occupant de la logique, qui considère un
       à un les blocs fournis par l'itérateur, renvoie faux si l'un des blocs
       ne vérifie pas le prédicat, et vrai si tous les blocs ont été testés
       avec succès.
*/
           private boolean forAllBlocks(Predicate<Block> p) {
               Iterator<Block> it = this.blockIterator();
               while (it.hasNext()) {
                   if (!p.test(it.next())) return false;
               }
               return true;
           }




    // Retirer un bloc
    private void removeBlock(Block b) {
        assert b != this.anchor : "Enlève mauvais bloc";
        b.prevBlock.nextBlock = b.nextBlock;
        b.nextBlock.prevBlock = b.prevBlock;
        this.size--;
        assert checkInvariants() : "Problème après removeBlock";
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

     // Similaire à [forAllBlocks], mais ne renvoie pas de résultat (et
     // n'interrompt jamais l'itération)
    private void forAllBlock2(Consumer<Block> f) {
        Block currentBlock = this.anchor;
        do {
            f.accept(currentBlock);
            currentBlock = currentBlock.nextBlock;
        } while (currentBlock != this.anchor);
    }

     /**
      Une méthode pour supprimer toutes les occurrences d'un élément.
      Elle se décompose en un itérateur [forEachBlock] et une méthode
      [removeBlock] qui fait les manipulations de pointeurs nécessaires
      pour retirer un bloc donné de la liste.
      */
     public void removeAll(T elt) {
         this.forAllBlock2(b -> { if (b.elt == elt) removeBlock(b); });
     }


    /**
  sion
    public void concat(DLL<T> l) {
        this.size += l.size;
        if (!l.isEmpty()) {
            l.firstPayloadBlock().prevBlock = this.lastPayloadBlock();
            l.lastPayloadBlock().nextBlock = this.anchor;
            this.lastPayloadBlock().nextBlock = l.firstPayloadBlock();
            this.anchor.prevBlock = l.lastPayloadBlock();
        }
        assert checkInvariants() : "Problème après extend";
    }
    private boolean isEmpty() {
        return this.anchor.nextBlock == this.anchor;
    }
    private Block firstPayloadBlock() {
        return this.anchor.nextBlock;
    }
    private Block lastPayloadBlock() {
        return this.anchor.prevBlock;
    }

    /**
       Défi : une méthode pour renverser une liste en place.

       Une proposition de solution, en deux passes :
       1/ une itération en suivant les pointeurs [nextBlock]
       2/ une itération en suivant les pointeurs [prevBlock]
    */
    public void rev() {
        // Première passe : on itère en suivant les champs [nextBlock]
        // et on met à jour tous les champs [prevBlock]
        forAllBlock2(b -> { b.prevBlock = b.nextBlock; });
        // Deuxième passe : on suit cette fois les champs [prevBlock]
        // et on met à jour les champs [nextBlock]
        forEachBlockDescending(b -> { b.prevBlock.nextBlock = b; });
        // Remarque : les deux itérations considèrent les différents blocs
        // dans le même ordre
        assert checkInvariants() : "Problème après rev";
    }
    private void forEachBlockDescending(Consumer<Block> f) {
        Block currentBlock = this.anchor;
        do {
            f.accept(currentBlock);
            currentBlock = currentBlock.prevBlock;
        } while (currentBlock != this.anchor);
    }



    
}
