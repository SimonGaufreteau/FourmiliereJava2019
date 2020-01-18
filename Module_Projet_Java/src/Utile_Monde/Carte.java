package Utile_Monde;

import Exceptions_Monde.InvalidDirectionException;
import Exceptions_Monde.OutOfMapException;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    // création d'une carte "vide" (sans nourriture ni fourmiliere)
    public Carte(int hauteur, int largeur) {
        this.hauteur=hauteur;
        this.largeur=largeur;
        this.grille=new Case[hauteur][largeur];
        for (int ligne=0;ligne<hauteur;ligne++){
            for (int colonne=0;colonne<largeur;colonne++){
                this.grille[ligne][colonne]=new Case(colonne,ligne,this);
            }
        }
    }
    // initialisation d'une carte avec des fourmiliere et de la nourriture pour cela on tire au sort un position x et une position y pour les placer
    public Carte(int hauteur, int largeur,int nbFourmiliere,int nbNourriture){
        super();
        int aleatX,aleatY;
        for(int i=0;i<nbFourmiliere;i++){
            aleatX= (int)(Math.random()*largeur);
            aleatY= (int)(Math.random()*hauteur);
            while(this.grille[aleatY][aleatX] instanceof CaseNourriture || this.grille[aleatY][aleatX] instanceof CaseNourriture){
                aleatX= (int)(Math.random()*largeur);
                aleatY= (int)(Math.random()*hauteur);
            }
            this.grille[aleatY][aleatX]=new CaseFourmiliere(aleatX,aleatY,this);
        }
        for(int i=0;i<nbNourriture;i++){
            aleatX= (int)(Math.random()*largeur);
            aleatY= (int)(Math.random()*hauteur);
            while(this.grille[aleatY][aleatX] instanceof CaseNourriture || this.grille[aleatY][aleatX] instanceof CaseNourriture){
                aleatX= (int)(Math.random()*largeur);
                aleatY= (int)(Math.random()*hauteur);
            }
            this.grille[aleatY][aleatX]=new CaseNourriture(aleatX,aleatY,100, this);
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

        //On compte la taille de la grille manuellement avec "hauteur" et "largeur_reelle", pour vérifier que le format est respecté
        int hauteur=0;
        int largeur_reelle;
        while(iterator.hasNext()){

            //On récupère la ligne dans l, si la taille n'est pas la bonne (en enlevant les espaces entre les caractères), on throw une exception
            l=iterator.next();
            if (l.split(" ").length!=this.largeur){
                throw new InvalidMapSizeException(this.largeur,l.split(" ").length);
            }

            largeur_reelle=0;
            for (int largeur_actuelle = 0 ; largeur_actuelle < l.length() ;largeur_actuelle++) {
                //on réalise un switch sur chaque caractère de la ligne

                car = l.charAt(largeur_actuelle);
                switch (car) {
                    case 'C':
                        //A chaque fois qu'on a un caractere reconnu, on incremente la largeur "reelle" de la ligne
                        this.grille[hauteur][largeur_reelle] = new Case(hauteur, largeur_reelle,this);
                        largeur_reelle++;
                        break;
                    case 'F':
                        this.grille[hauteur][largeur_reelle] = new CaseFourmiliere(hauteur, largeur_reelle,this);
                        largeur_reelle++;
                        break;
                    case 'N':
                        this.grille[hauteur][largeur_reelle] = new CaseNourriture(hauteur, largeur_reelle,7,this);
                        largeur_reelle++;
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

    public Carte(){}

    //Méthode permettant de renvoyer la liste des lignes d'un fichier
    private  List<String> getLignes(String nomCarte) throws IOException {
        List<String> lignes  = new ArrayList<>();
        nomCarte = System.getProperty("user.dir")+ "\\Module_Projet_Java\\"+nomCarte;
        BufferedReader reader = new BufferedReader(new FileReader(nomCarte));
        String line;
        while ((line = reader.readLine()) != null) {
            lignes.add(line);
        }
        reader.close();
        return lignes;
    }

    /*Calcul de la distance entre deux cases, nécessaires à la fourmi pour rentrer au plus proche*/
    public  int distanceEntreDeuxCases(Case case1, Case case2){
        int xMin = case1.getX(); //récupèration des coordonnées des cases
        int xMax= case2.getX();
        int yMin = case1.getY();
        int yMax = case2.getY();
        if(case1.getX() > case2.getX()){
            xMin=case2.getX();
            xMax=case1.getX();
        }
        if(case1.getY() > case2.getY()){
            yMin=case2.getY();
            yMax=case1.getY();
        }
        //calcul de la distance dans un monde torique
        int deltaX = Math.min(xMax-xMin,(largeur-xMax)+xMin);
        int deltaY = Math.min(yMax-yMin,(largeur-yMax)+yMin);
        return deltaX + deltaY;
    }
    /*Pour rentrer à la fourmilière la plus proche la fourmi doit savoir dans quelle direction partir, il faut donc examiner
     * les cases voisines pour calculer leur distance à la fourmilière. */
    public Case getVoisin(int x, int y, char direction) throws InvalidDirectionException {
        switch (direction){
            //en fonction de la direction, donne la case voisine
            case 'H':
                if(y==0) {
                    return grille[hauteur-1][x];
                }
                return grille[y-1][x];
            case 'D':
                if(x==largeur-1){
                    return grille[y][0];
                }
                return grille[y][x+1];
            case 'B':
                if(y==hauteur-1){
                    return grille[0][x];
                }
                return grille[y+1][x];
            case 'G':
                if(x==0){
                    return grille[y][largeur-1]; }
                return grille[y][x-1];
            default:
                throw new InvalidDirectionException(direction);
        }
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

    //Méthode de sauveagarde de la carte. On prend en paramètre un nom de carte à sauvegarder dans le dossier "Sauvegardes"
    //Throws une IOException si on a un problème d'ouverture du fichier en écriture
    public void sauvegarder(String nomFichier) throws IOException {
        nomFichier = System.getProperty("user.dir")+ "\\Module_Projet_Java\\Sauvegardes\\"+nomFichier;

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), StandardCharsets.UTF_8));
        writer.write(hauteur + " "+largeur + "\n"+this.toString());
        writer.close();
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
    C N C C C
    C C C C N
    C C F C C
    C C C C C
    (C = Case normale, N = Nourriture, F = Fourmiliere)
    (Voir toString() de chaque case pour detail)
    * */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int h=0;h<hauteur;h++){            //Affichage une par une des lignes
            for (int l=0;l<largeur;l++){        //Affichage d'une ligne
                s.append(grille[h][l].toString()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}