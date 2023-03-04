package formule1zCorrection;
import IG.Fenetre;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FormuLInf {
    public static void main(String[] args) throws IOException {
        Fenetre f = new Fenetre("FormuL∞");
        Circuit t = decodeCircuit(args[0]);
        f.ajouteElement(t);
        f.dessineFenetre();
    }
    
    private static Circuit decodeCircuit(String fichier) throws IOException {
        BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
        int nbLignes = Integer.parseInt(lecteur.readLine());
        int nbColonnes = Integer.parseInt(lecteur.readLine());
        boolean[][] carte = new boolean[nbLignes][nbColonnes];
        int ligneDepart=0, colonneDepart=0;
        for (int l=0; l<nbLignes; l++) {
            String ligne = lecteur.readLine();
            for (int c=0; c<nbColonnes; c++) {
                if (ligne.charAt(c) == '@') {
                    ligneDepart = l;
                    colonneDepart = c;
                }
                carte[l][c] = ligne.charAt(c) != '#';
            }
        }
        lecteur.close();
        return new Circuit(nbLignes, nbColonnes, carte, ligneDepart, colonneDepart);
    }
}
