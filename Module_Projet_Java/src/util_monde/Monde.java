
package util_monde;


import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import exceptions_monde.OutOfMapException;
import util_fourmi.Fourmi;
import util_fourmi.ProgrammeGenetique;

import java.io.IOException;
import java.util.ArrayList;

public class Monde  {
    private Carte carte;
    private Fourmi[] fourmis;
    private static int nbFourmisDefaut = 20;

    /*
    Constructeur avec Carte à charger. On prend en argument le nom du fichier contenant la carte
     */
    public Monde(String nomCarte, int nbFourmi) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        this.carte = new Carte(nomCarte);            //throws les exception ci-dessus en cas de mauvais fichier, mauvais format, etc...
        this.fourmis = new Fourmi[nbFourmi];
        for (int i = 0; i < nbFourmi; i++) {
            Case posF = new Case(0, 0, this.carte);
            this.fourmis[i] = new Fourmi(posF, lesFourmilieres());

        }
    }
    public Monde(String nomCarte, ProgrammeGenetique prgG) throws IOException, InvalidMapSizeException, InvalidFileFormatException {
        this.carte = new Carte(nomCarte);            //throws les exception ci-dessus en cas de mauvais fichier, mauvais format, etc...
        this.fourmis = new Fourmi[1];
        Case posF = new Case(0, 0, this.carte);
        this.fourmis[0] = new Fourmi(posF,lesFourmilieres(),prgG);
    }

    public Monde(Carte carte, Fourmi[] fourmis) {
        this.carte = carte;
        this.fourmis = fourmis;
    }

    /*Constructeur par défaut avec un nombre de fourmis à créer :
    prend la hauteur + la largeur pour créer la grille, ainsi qu'un nombre de fourmis à créer.
    * */
    public Monde(int largeur, int hauteur, int nbFourmi) throws IOException {
        this.carte = new Carte(largeur, hauteur);
        this.fourmis = new Fourmi[nbFourmi];
        for (int i = 0; i < nbFourmi; i++) {
            this.fourmis[i] = new Fourmi();
        }
    }

    // constructeur du monde qui prend en parametre la hauteur et la largeur de la carte ainsi que le nombre de fourmi de fourmiliere et de nourriture

    public Monde(int largeur, int hauteur, int nbFourmi, int nbCaseFourm, int nbCaseNour) throws InvalidNbCaseDiffException {
        int x, y; // variables pour la position des fourmis
        this.carte = new Carte(largeur, hauteur, nbCaseFourm, nbCaseNour);
        this.fourmis = new Fourmi[nbFourmi];
        for (int i = 0; i < nbFourmi; i++) {
            try {
                y = (int) (Math.random() * (getCarte().getHauteur())); // on prend un nombre au hasard en tre0 et la hauteur -1)
                x = (int) (Math.random() * (getCarte().getLargeur())); // meme chose avec la largeur
                Case posF = new Case(x, y, this.carte);
                this.fourmis[i] = new Fourmi(posF, lesFourmilieres());
            } catch (Exception e) {

            }
        }
    }

    /*Constructeur par défaut :
        On utilise deux entiers pour établir la taille de la grille du monde (i.e.la carte)
    */
    public Monde(int hauteur, int largeur) throws IOException {
        this(largeur, hauteur, nbFourmisDefaut);
    }

    //Sauvegarder l'état de la carte du monde
    public void sauvegarder(String nomFichier) throws IOException {
        this.carte.sauvegarder(nomFichier);
    }

    public ArrayList<CaseNourriture> laNourriture() {
        ArrayList nourriture = new ArrayList();
        Case[][] grille = carte.getGrille();
        for (int x = 0; x < carte.getLargeur(); x++) {
            for (int y = 0; y < carte.getHauteur(); y++) {
                if (grille[x][y] instanceof CaseNourriture) {
                    nourriture.add(grille[x][y]);
                }
            }
        }
        return nourriture;
    }


    public Carte getCarte() {
        return carte;
    }

    public Fourmi[] getFourmis() {
        return fourmis;
    }

    public void setCases(Case[] cases) throws OutOfMapException {
        this.carte.setCases(cases);
    }

    //fonction qui renvoi une liste de caseForurmilieres
    public ArrayList<CaseFourmiliere> lesFourmilieres() {
        ArrayList fourmilieres = new ArrayList();
        Case[][] grille = carte.getGrille();
        for (int x = 0; x < carte.getLargeur(); x++) {
            for (int y = 0; y < carte.getHauteur(); y++) {
                if (grille[x][y] instanceof CaseFourmiliere) {
                    fourmilieres.add(grille[x][y]);
                }
            }
        }
        return fourmilieres;
    }



    //Affichage du Monde sous forme de String.
    //Affichage de la Carte + des fourmis.
    public String toString() {
        StringBuilder s = new StringBuilder("Affichage de la Carte :\n" + carte.toString()); /* + "\nAffichage des Fourmis :\n");
        int i=1;
        for (Fourmi fourmi : fourmis) {
            s.append("Fourmi " + i + " : " + fourmi.toString()).append("\n\n");
            i++;
        }*/
        return s.toString();
    }
}