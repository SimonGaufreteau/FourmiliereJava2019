package Utile_Monde;

import Exceptions_Monde.InvalidFileFormatException;
import Exceptions_Monde.InvalidMapSizeException;
import Exceptions_Monde.OutOfMapException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
/*
Classe de génération et de manipulation de la Utile_Monde.Carte (du Utile_Monde.Monde).
Attributs : Une hauteur , une largeur et un tableau de tableaux de Cases représentant la grille.

Note : On accède aux éléments de la grille de la facon suivante :
grille[l][h] où "l" est l'index de ligne et "h" l'index de colonne
*/

public class Carte {
    private int hauteur;
    private int largeur;
    private Case[][] grille;

    /*Constructeur par défaut de Utile_Monde.Carte.
    On initialise toutes les Cases avec le constructeur de base (voir classe "Utile_Monde.Case") pour n'avoir que des cases "simples".
    */
    public Carte(int hauteur, int largeur){
        this.hauteur=hauteur;
        this.largeur=largeur;
        this.grille=new Case[hauteur][largeur];
        for (int ligne=0;ligne<hauteur;ligne++){
            for (int colonne=0;colonne<largeur;colonne++){
                this.grille[ligne][colonne]=new Case(colonne,ligne);
            }
        }
    }

    public Carte(String nomCarte) throws InvalidFileFormatException, IOException, InvalidMapSizeException {
        //Constructeur de chargement d'une carte
        //on récupère la liste des lignes via la méthode getLignes

        List<String> lignes = this.getLignes(nomCarte);

        //On parcourt les lignes et on crée une case à chaque fois qu'on rencontre un symbole
        //On utilise l'object ListIterator pour parcourir la liste de lignes.
        ListIterator<String> iterator = lignes.listIterator();
        String l;
        char car;

        //On recupère la taille de la grille se trouvant à la première ligne sous le format suivant : "10 8" (ici 10 pour hauteur et 8 pour largeur)
        //(On vérifiera par la suite la bonne correspondance de ces valeurs)
        try{
            String premiereLigne =iterator.next();
            String[] premierSplit = premiereLigne.split(" ");
            this.hauteur = Integer.parseInt(premierSplit[0]);
            this.largeur = Integer.parseInt(premierSplit[1]);
            this.grille =new Case[this.hauteur][this.largeur];
        } catch (Exception e) {
            throw new InvalidFileFormatException(nomCarte);
        }

        //On compte la taille de la grille manuellement avec "hauteur" et "largeur_reele", pour vérifier que le format est respecté
        int hauteur=0;
        int largeur_reele;
        while(iterator.hasNext()){

            //On récupère la ligne dans l, si la taille n'est pas la bonne (en enlevant les espaces entre les caractères), on throw une exception
            l=iterator.next();
            if (l.split(" ").length!=this.largeur){
                throw new InvalidMapSizeException(this.largeur,l.split(" ").length);
            }

            largeur_reele=0;
            for (int largeur_actuelle = 0 ; largeur_actuelle < l.length() ;largeur_actuelle++) {
                //on réalise un switch sur chaque caractère de la ligne

                car = l.charAt(largeur_actuelle);
                switch (car) {
                    case 'C':
                        //A chaque fois qu'on a un caractere reconnu, on incremente la largeur "reele" de la ligne
                        this.grille[hauteur][largeur_reele] = new Case(hauteur, largeur_reele);
                        largeur_reele++;
                        break;
                    case 'F':
                        this.grille[hauteur][largeur_reele] = new CaseFourmiliere(hauteur, largeur_reele);
                        largeur_reele++;
                        break;
                    case 'N':
                        this.grille[hauteur][largeur_reele] = new CaseNourriture(hauteur, largeur_reele);
                        largeur_reele++;
                        break;
                }
            }
            hauteur++;
        }
        //Fin de la boucle while du parcours des lignes. On vérifie si la taille recue est bien celle attendue.
        if (hauteur!=this.hauteur){
            throw new InvalidMapSizeException(this.hauteur,hauteur);
        }
    }

    //Méthode permettant de renvoyer la liste des lignes d'un fichier
    private  List<String> getLignes(String nomCarte) throws IOException {
        List<String> lignes  = new ArrayList<String>();
        nomCarte = System.getProperty("user.dir")+ "\\Module_Projet_Java\\"+nomCarte;
        BufferedReader reader = new BufferedReader(new FileReader(nomCarte));
        String line;
        while ((line = reader.readLine()) != null) {
            lignes.add(line);
        }
        reader.close();
        return lignes;
    }



    /*Méthode permettant de modifier certaines Cases de la Utile_Monde.Carte.
    Entrée : un tableau de Cases
    Fonctionnement : Pour chaque Utile_Monde.Case donnée en paramètre, on remplace la Utile_Monde.Case de la Utile_Monde.Carte par celle-ci
    */
    public void setCases(Case[] cases) throws OutOfMapException {
        Case c;
        for (Case aCase : cases) {
            c = aCase;
            if (c.getX() >= largeur || c.getY() >= hauteur || c.getX() < 0 || c.getY() < 0) {
                throw new OutOfMapException(c.getX(), c.getY());
            }
            this.grille[c.getY()][c.getX()] = c;
        }
    }
    //Getters
    public Case[][] getGrille() {
        return grille;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    /*Affichage d'une Carte sous le format suivant :
    [ C N C C C ]
    [ C C C C N ]
    [ C C F C C ]
    [ C C C C C ]
    (C = Case normale, N = Nourriture, F = Fourmiliere)
    (Voir toString() de chaque case pour detail)
    * */
    public String toString(){
        String s ="";
        for (int h=0;h<hauteur;h++){            //Affichage une par une des lignes
            s+="[ ";
            for (int l=0;l<largeur;l++){        //Affichage d'une ligne
                s+=grille[h][l].toString()+" ";
            }
            s+="]\n";
        }
        return s ;
    }
}