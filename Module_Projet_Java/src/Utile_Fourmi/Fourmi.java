package Utile_Fourmi;
import Exceptions_Monde.InvalidDirectionException;
import Utile_Monde.*;
import Interfaces_Global.*;

import java.util.ArrayList;

public class Fourmi implements Ramasser, Deposer, Deplacer, Detecter{
    private Case position;
    private int score; //variable qui sera incrémentée au cours du déroulement du jeu
    private boolean porteNourriture;
    private ArrayList<CaseFourmiliere> listeFourmilieres;

    /*on passe la liste des fourmilieres en paramètres car la fourmi doit
    connaitre toutes ses fourmilieres de son monde
    */
    public Fourmi (Case position, ArrayList<CaseFourmiliere> listeFourmilieres) {
        this.position = position;
        this.listeFourmilieres = listeFourmilieres;
        this.score=0; //initialisation à zéro
    }

    public Fourmi() {
        //pour initiliser monde
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

    public void setPorteNourriture(boolean porteNourriture) {
        this.porteNourriture = porteNourriture;
    }

    /*fonction qui aide à ramasser la nourriture*/
    public boolean ramasser() {
        if(detecterCaseNourriture() && !porteNourriture){
            porteNourriture = ((CaseNourriture)position).enleverNourriture();
            return porteNourriture; //peut être true ou false selon la quantité de nourriture qu'il reste dans la case
        }
        return false;
    }

    public ArrayList<CaseFourmiliere> getListeFourmilieres() {
        return listeFourmilieres;
    }

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

    public boolean detecterCaseNourriture(){
        if(this.position instanceof CaseNourriture){
            return true;
        }
        return false;
    }

    @Override
    public boolean detecterCaseFourmiliere() {
        return this.position instanceof CaseFourmiliere;
    }

    @Override
    public boolean deplacer(int direction) {
        return false;
    }

    @Override
    public boolean deposer() {
        return false;
    }
}
