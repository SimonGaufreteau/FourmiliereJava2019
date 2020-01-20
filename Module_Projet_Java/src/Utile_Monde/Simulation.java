package Utile_Monde;

import Exceptions_Monde.InvalidDirectionException;
import Utile_Fourmi.*;
import Utile_Monde.Monde;
import Interfaces_Global.*;

public class Simulation {
    private Monde monMonde;
    private Fourmi[] mesFourmis;
    private Score[] scoreFourmi;

    //lancement d'une simulation avec la creation d'un monde avec choix des parametres
    /* deroulement :
    creation et initialisation d'un monde
    recuperation des fourmis
    action des fourmis
    recuêration du score
     */
    public Simulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour) {
        int nbCoups = 20;
        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            System.out.println(monMonde);
            for (int i = 0; i < mesFourmis.length; i++) {
                for(int j = 0; j < nbCoups; j++) {
                    System.out.println("\nCoup " + (j+1) + " : ");
                    System.out.println("X : " + mesFourmis[i].getPosition().getX());
                    System.out.println("Y : " + mesFourmis[i].getPosition().getY());
                    System.out.println("Type : " + mesFourmis[i].getPosition().toString());
                    mesFourmis[i].agir();
                }
                scoreFourmi[i] = mesFourmis[i].getScore();
                if(scoreFourmi[i].getPoint() != 0) {
                    //System.out.println("Fourmi " + i + " : ");
                    //System.out.println(mesFourmis[i].getIntelligence());
                    System.out.println("Score : " + scoreFourmi[i].getPoint());
                }
            }
        }
        /*catch (InvalidDirectionException e) {
            System.out.println(e);
        }*/
        catch (Exception e) {
            System.out.println(e);
        }
    }


    //lancement d'une simulation avec chargement d'une carte en parametre
    public Simulation(String nomFichier,int nbFourmis){
        try {
            monMonde = new Monde(nomFichier, nbFourmis);
            mesFourmis = monMonde.getFourmis();
            System.out.println(monMonde);
            for (int i = 0; i < mesFourmis.length; i++) {
                mesFourmis[i].agir();
                scoreFourmi[i] = mesFourmis[i].getScore();
                System.out.println(scoreFourmi[i]);
            }

        } catch (InvalidDirectionException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}