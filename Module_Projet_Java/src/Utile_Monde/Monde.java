package Utile_Monde;/*
Classe de génération et de manipulation du Utile_Monde.Monde.
Attributs : Une Utile_Monde.Carte, un tableau de fourmis
*/

import Exceptions_Monde.InvalidFileFormatException;
import Exceptions_Monde.InvalidMapSizeException;
import Exceptions_Monde.OutOfMapException;
import Utile_Fourmi.Fourmi;

import java.io.IOException;

public class Monde {
    private Carte carte;
    private Fourmi[] fourmis;
    private static int nbFourmisDefaut=20;

    /*
    Constructeur avec Carte à charger. On prend en argument le nom du fichier contenant la carte
     */
    public Monde(String nomCarte,int nbFourmi) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        this.carte= new Carte(nomCarte);            //throws les exception ci-dessus en cas de mauvais fichier, mauvais format, etc...
        this.fourmis=new Fourmi[nbFourmi];
        for (int i=0;i<nbFourmi;i++){
            this.fourmis[i]=new Fourmi();
            //Pour l'instant aucun constructeur de définit. A voir dans la classe "Utile_Fourmi.Fourmi".
        }
    }

    /*Constructeur par défaut avec un nombre de fourmis à créer :
    prend la hauteur + la largeur pour créer la grille, ainsi qu'un nombre de fourmis à créer.
    * */
    public Monde(int hauteur,int largeur,int nbFourmi){
        this.carte= new Carte(hauteur,largeur);
        this.fourmis=new Fourmi[nbFourmi];
        for (int i=0;i<nbFourmi;i++){
            this.fourmis[i]=new Fourmi();
            //Pour l'instant aucun constructeur de définit. A voir dans la classe "Utile_Fourmi.Fourmi".
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
