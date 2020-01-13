package Utile_Fourmi;
import Exceptions_Monde.InvalidDirectionException;
import Utile_Monde.*;
import Interfaces_Global.*;

import java.io.IOException;
import java.util.ArrayList;

public class Fourmi implements Ramasser, Deposer, Deplacer, Detecter{
    private Case position;
    private int score; //variable qui sera incrémentée au cours du déroulement du jeu
    private boolean porteNourriture;
    private ArrayList<CaseFourmiliere> listeFourmilieres;
    private ProgrammeGenetique intelligence;

    /*Pour rentrer à la fourmilière la plus proche la fourmi a besoin de connaître toutes les fourmilières
    du Monde dans lequel elle évolue
    On lui passe une case position(son X et son Y sont contenus dans la case)
    */
    public Fourmi (Case position, ArrayList<CaseFourmiliere> listeFourmilieres) throws IOException {
        this.position = position;
        this.listeFourmilieres = listeFourmilieres;
        this.score=0; //initialisation à zéro
        intelligence = new ProgrammeGenetique();
    }

    public Fourmi() throws IOException {
        intelligence = new ProgrammeGenetique();
        //pour initialiser monde
    }

    public int getScore() {
        //pour afficher le score on a besoin de le récupérer
        return score;
    }

    public void setScore(int score) {
        //pour pouvoir incrémenter le score au cours du jeu on aura besoin de son accesseur
        this.score = score;
    }


    public boolean transporteNourriture() {
        return porteNourriture;
    }

    /*Fonction utilisée pour ramasser la nourriture*/
    public boolean ramasser() {
        if(detecterCaseNourriture() && !porteNourriture){
            porteNourriture = ((CaseNourriture)position).enleverNourriture();
            return porteNourriture; //peut être true ou false selon la quantité de nourriture qu'il reste dans la case
        }
        return false;
    }

    /*Pour rentrer à la fourmilière la plus proche la fourmi a besoin de savoir où se situe la
    plus proche des fourmilières comme elle connait toutes la liste il lui suffit de la parcourir*/
    public CaseFourmiliere trouverFourmilierePlusProche(){
        int min = Integer.MAX_VALUE;
        int distance;
        CaseFourmiliere caseFourmilierePlusProche = new CaseFourmiliere(0,0,new Carte());
        for (CaseFourmiliere caseFourmiliere:listeFourmilieres) {
            distance = position.getCarteCourante().distanceEntreDeuxCases(position,caseFourmiliere);
            if(distance<min){
                min=distance;
                caseFourmilierePlusProche = caseFourmiliere;
            }
        }
        return caseFourmilierePlusProche;
    }

    /*Permet à la fourmi d'effectuer le trajet d'une case de moins vers la fourmilière
     * à effectuer autant de fois jsq à atteindre la fourmilière
     * Chaque fourmi effectue une action à la fois, donc elle ne peut pas se déplacer en une seule fois
     * vers la fourmilière. */
    public void rentrerFourmiliere(){
        CaseFourmiliere fourmiliere = trouverFourmilierePlusProche();
        try {
            Case voisinHaut = position.getCarteCourante().getVoisin(position.getX(),position.getY(),0);
            Case voisinBas = position.getCarteCourante().getVoisin(position.getX(),position.getY(),2);
            if(position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinHaut) >
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinBas)){
                deplacer(2);
                return;
            }
            if(position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinHaut)<
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinBas)){
                deplacer(0);
                return;
            }
            Case voisinGauche = position.getCarteCourante().getVoisin(position.getX(),position.getY(),3);
            Case voisinDroite = position.getCarteCourante().getVoisin(position.getX(),position.getY(),1);
            if(position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinGauche) >
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinDroite)){
                deplacer(1);
                return;
            }
            if(position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinGauche) <
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere,voisinDroite)){
                deplacer(3);
            }
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        }
    }

    //Permets à la fourmi de savoir si elle est sur une case nourriture(true:si oui, false:sinon)
    public boolean detecterCaseNourriture(){
        return this.position instanceof CaseNourriture;
    }

    @Override
    //true si sur CaseFourmiliere
    public boolean detecterCaseFourmiliere() {
        return this.position instanceof CaseFourmiliere;
    }

    @Override
    public boolean deplacer(int direction) {
        return false;
    }

    @Override
    public boolean deposer() {
        if (porteNourriture){
            porteNourriture=false;
            if(detecterCaseFourmiliere())
                return true;
        }
        return false;
    }

    // Cette fonction permet d'agir en fonction de son arbre de décision
    public void agir(){
        ProgrammeGenetique noeudEnCours = intelligence;
        while((noeudEnCours.getNoeud().getClass().getName()).equals("Utile_Fourmi.Condition")){
            System.out.println(noeudEnCours.getValeurNoeud());
            if((noeudEnCours.getValeurNoeud()).equals("cond_nourriture")){
                if(detecterCaseNourriture())
                    noeudEnCours = noeudEnCours.getAGauche();
                else
                    noeudEnCours = noeudEnCours.getADroite();
            }
            else if ((noeudEnCours.getValeurNoeud()).equals("cond_fourmiliere")){
                if(detecterCaseNourriture())
                    noeudEnCours = noeudEnCours.getAGauche();
                else
                    noeudEnCours = noeudEnCours.getADroite();
            }
            else if ((noeudEnCours.getValeurNoeud()).equals("cond_possedeNourriture")){
                if(transporteNourriture())
                    noeudEnCours = noeudEnCours.getAGauche();
                else
                    noeudEnCours = noeudEnCours.getADroite();
            }
        }

    }

    public ProgrammeGenetique getIntelligence() {
        return intelligence;
    }
}