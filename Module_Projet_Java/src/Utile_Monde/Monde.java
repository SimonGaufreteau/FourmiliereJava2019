package Utile_Monde;/*
Classe de génération et de manipulation du Utile_Monde.Monde.
Attributs : Une Utile_Monde.Carte, un tableau de fourmis
*/

import Exceptions_Monde.InvalidFileFormatException;
import Exceptions_Monde.InvalidMapSizeException;
import Exceptions_Monde.OutOfMapException;
import Utile_Fourmi.Fourmi;

import java.io.IOException;
import java.util.ArrayList;

public class Monde {
    private Carte carte;
    private Fourmi[] fourmis;
    private static int nbFourmisDefaut=20;

    /*
    Constructeur avec Carte à charger. On prend en argument le nom du fichier contenant la carte
     */
    public Monde(String nomCarte,int nbFourmi) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        int x,y;
        this.carte= new Carte(nomCarte);            //throws les exception ci-dessus en cas de mauvais fichier, mauvais format, etc...
        this.fourmis=new Fourmi[nbFourmi];
        for (int i=0;i<nbFourmi;i++){
            y= (int) (Math.random() * (getCarte().getHauteur()-1)); // on prend un nombre au hasard en tre0 et la hauteur -1)
            x=(int) (Math.random() * (getCarte().getLargeur()-1)); // meme chose avec la largeur
            Case posF= new Case(y,x,this.carte);
            this.fourmis[i] = new Fourmi(posF,lesFourmilieres());
        }
    }

    /*Constructeur par défaut avec un nombre de fourmis à créer :
    prend la hauteur + la largeur pour créer la grille, ainsi qu'un nombre de fourmis à créer.
    * */
    public Monde(int hauteur,int largeur,int nbFourmi){
        int y, x; // variables pour la position des fourmis
        this.carte= new Carte(hauteur,largeur);
        this.fourmis=new Fourmi[nbFourmi];
        for (int i=0;i<nbFourmi;i++){
            try {
                y= (int) (Math.random() * (getCarte().getHauteur())); // on prend un nombre au hasard en tre0 et la hauteur -1)
                x=(int) (Math.random() * (getCarte().getLargeur())); // meme chose avec la largeur
                Case posF= new Case(y,x,this.carte);
                this.fourmis[i] = new Fourmi(posF,lesFourmilieres());
            }catch (Exception e){

            }
        }
    }
    // constructeur du monde qui prend en parametre la hauteur et la largeur de la carte ainsi que le nombre de fourmi de fourmiliere et de nourriture

    public Monde(int hauteur,int largeur, int nbFourmi, int nbCaseFourm, int nbCaseNour){
        int y, x; // variables pour la position des fourmis
        this.carte= new Carte(hauteur,largeur,nbCaseFourm,nbCaseNour);
        this.fourmis=new Fourmi[nbFourmi];
        for (int i=0;i<nbFourmi;i++){
            try {
                y= (int) (Math.random() * (getCarte().getHauteur())); // on prend un nombre au hasard en tre0 et la hauteur -1)
                x=(int) (Math.random() * (getCarte().getLargeur())); // meme chose avec la largeur
                Case posF= new Case(y,x,this.carte);
                this.fourmis[i] = new Fourmi(posF,lesFourmilieres());
            }catch (Exception e){

            }
        }
    }




    /*Constructeur par défaut :
        On utilise deux entiers pour établir la taille de la grille du monde (i.e.la carte)
    */
    public Monde(int hauteur, int largeur){
       this(hauteur,largeur,nbFourmisDefaut);
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

    //fonction qui renvoi une liste de caseForurmilieres
    public ArrayList<CaseFourmiliere> lesFourmilieres() {
        ArrayList fourmilieres = new ArrayList();
        Case[][] grille = carte.getGrille();
        for (int ligne = 0; ligne < carte.getHauteur(); ligne++) {
            for (int colonne = 0; colonne < carte.getLargeur(); colonne++) {
                if (grille[ligne][colonne] instanceof CaseFourmiliere) {
                    fourmilieres.add(grille[ligne][colonne]);
                }
            }
        }
        return fourmilieres;
    }
    //Affichage du Monde sous forme de String.
    //Affichage de la Carte + des fourmis.
    public String toString(){
        StringBuilder s= new StringBuilder("Affichage de la Carte :\n" + carte.toString() + "\nAffichage des Fourmis :\n");
        for (Fourmi fourmi : fourmis) {
            s.append(fourmi.toString()).append("\n");
        }
        return s.toString();
    }
}
