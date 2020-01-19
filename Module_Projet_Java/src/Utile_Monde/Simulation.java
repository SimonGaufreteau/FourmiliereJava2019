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
        int nbCoups = 50;
        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            System.out.println(monMonde);
            System.out.println("OK");
            for (int i = 0; i < mesFourmis.length; i++) {
                for(int j = 0; j < nbCoups; j++) {
                    /*System.out.println("Coup " + j + " : ");
                    System.out.println("X : " + mesFourmis[i].getPosition().getX());
                    System.out.println("Y : " + mesFourmis[i].getPosition().getY());*/
                    mesFourmis[i].agir();
                }

                //scoreFourmi[i] = mesFourmis[i].getScore();
                //System.out.println(scoreFourmi[i]);
            }
            for (int i=0;i<mesFourmis.length;i++){
                if(mesFourmis[i].getScore().getPoint() != 0)
                    System.out.println(mesFourmis[i].getScore().getPoint());
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