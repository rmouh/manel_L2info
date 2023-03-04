package complexe;

/** immutable class representing a complex number*/
public class Complexe {

    /** definition des nombres complexes et quelques operations*/
         private double re;
         private double im;
        public static void main(String[] arg)
        { Complexe c=new Complexe(2,3);
            System.out.println(c);

        }
        /** Cree un Complexe
         * @param x la partie reelle
         * @param y la partie imaginaire */
        public Complexe(double x, double y) {
            this.re=x;
            this.im=y;
        }

        // methodes d'instance

        /** Renvoie une representation sous forme de chaine
         *  de ce Complexe */
        public String toString() {
            double a = this.re;  double b = this.im;
            if (a==0 && b==0) return("0");
            else if (a!=0 && b==0) return(""+a);
            else if (a==0 && b>0) return(b+" i");
            else return ""+a+"+"+b+" i";
        }


        /** Renvoie la somme de ce Complexe et du Complexe parametre.
         * @param c le Complexe a ajouter */
        public Complexe somme(Complexe c) {
            return (new Complexe(this.re+c.re, this.im+c.im));
        }

        /** Renvoie le produit de ce Complexe et du double specifie.
         * @param d le double par lequel multiplier */
        public Complexe produit(double d ) {
            return (new Complexe(this.re*d, this.im*d));
        }

        /** Renvoie le produit de ce Complexe et du Complexe parametre.
         * @param c le Complexe par lequel multiplier */
        public Complexe produit(Complexe c) {
            return (new Complexe(this.re*c.re - this.im*c.im,
                    this.re*c.im + this.im*c.re));
        }

        /** Renvoie le carre de la norme de ce Complexe */
        public double carreNorme() {
            return this.re*this.re + this.im*this.im;
        }

        /** Renvoie la norme de ce Complexe */
        public double norme() {
            return Math.sqrt(this.carreNorme());
        }

        /** Renvoie le conjugue de ce Complexe */
        public Complexe conjugue() {
            return new Complexe(this.re, -this.im);
        }
}
