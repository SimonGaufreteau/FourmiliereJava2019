package util_monde;

import exceptions_monde.InvalidDirectionException;
import util_fourmi.*;

public class SimulationBis {
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
    public SimulationBis(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour) {
        int nbCoups = 50;
        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            System.out.println(monMonde);
            System.out.println("OK");
            for (int i = 0; i < mesFourmis.length; i++) {
                for(int j = 0; j < nbCoups; j++) {
                    /*System.out.println("Coup " + j + " : ");
                    System.out.println("X : " + mesFourmis[i].getPosition().getX());
                    System.out.println("Y : " + mesFourmis[i].getPosition().getY());*/
                    mesFourmis[i].agir();
                }
                scoreFourmi[i] = mesFourmis[i].getScore();
                if(scoreFourmi[i].getPoint() != 0)
                    System.out.println(scoreFourmi[i].getPoint());
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
    public SimulationBis(String nomFichier, int nbFourmis){
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