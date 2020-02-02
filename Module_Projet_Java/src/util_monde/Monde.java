package util_monde;


import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import exceptions_monde.OutOfMapException;
import util_fourmi.Fourmi;
import util_fourmi.ProgrammeGenetique;

import java.io.IOException;
import java.util.ArrayList;

public class Monde implements Cloneable {
    private Carte carte;
    private Fourmi[] fourmis;
    private ProgrammeGenetique programmeGenetique;
    private static int nbFourmisDefaut = 20;

    /*
    Constructeur avec Carte à charger. On prend en argument le nom du fichier contenant la carte
     */
    public Monde(String nomCarte, int nbFourmi) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        this.carte = new Carte(nomCarte);            //throws les exception ci-dessus en cas de mauvais fichier, mauvais format, etc...
        this.fourmis = new Fourmi[nbFourmi];
        for (int i = 0; i < nbFourmi; i++) {
            this.fourmis[i] = new Fourmi();
        }
    }

    public Monde(String nomCarte, int nbFourmi, ProgrammeGenetique programmeGenetique) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        this.carte = new Carte(nomCarte);
        this.fourmis = new Fourmi[nbFourmi];
        this.programmeGenetique = programmeGenetique ;
        for (int i = 0; i<nbFourmi; i++){
            this.fourmis[i] = new Fourmi();
        }
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
            //Pour l'instant aucun constructeur de définit. A voir dans la classe "Utile_Fourmi.Fourmi".
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

    public Carte getCarte() {
        return carte;
    }

    public Fourmi[] getFourmis() {
        return fourmis;
    }


    public void setCases(Case[] cases) throws OutOfMapException {
        this.carte.setCases(cases);
    }

    //fonction qui renvoie une liste de caseFourmilieres
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


    public Monde clone() throws CloneNotSupportedException {
        Monde m = (Monde) super.clone();
        m.carte = (Carte) this.carte.clone();
        m.fourmis = (Fourmi[]) this.fourmis.clone();
        return m;
    }

    //Affichage du Monde sous forme de String.
    //Affichage de la Carte + des fourmis.
    public String toString() {
        StringBuilder s = new StringBuilder("Affichage de la Carte :\n" + carte.toString());
        return s.toString();
    }
}