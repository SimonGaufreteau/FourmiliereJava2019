package Utile_Monde;

import Exceptions_Monde.InvalidDirectionException;
import Utile_Fourmi.*;
import Utile_Monde.Monde;
import Interfaces_Global.*;

import java.io.IOException;


public class Simulation {
    private Monde monMonde;
    private Score[] scoreFourmi;
    private Score[] scoreFourmi2;

    //lancement d'une simulation avec la creation d'un monde avec choix des parametres
    /* deroulement :
    creation et initialisation d'un monde
    recuperation des fourmis
    action des fourmis
    recuêration du score
     */
    public Simulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour) {
        final int NB_COUPS = 20;
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        final int NB_EVOLUTIONS = 1;
        final double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5;
        Fourmi[] mesFourmis;
        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            Case[] posInit = new Case[mesFourmis.length];
            System.out.println(monMonde);
            for (int i = 0; i < mesFourmis.length; i++) {
                posInit[i] = mesFourmis[i].getPosition();
                for(int j = 0; j < NB_COUPS; j++) {
                    /*System.out.println("\nCoup " + (j+1) + " : ");
                    System.out.println("X : " + mesFourmis[i].getPosition().getX());
                    System.out.println("Y : " + mesFourmis[i].getPosition().getY());
                    System.out.println("Type : " + mesFourmis[i].getPosition().toString());*/
                    mesFourmis[i].agir();
                }
                //scoreFourmi[i] = mesFourmis[i].getScore();
                /*if(scoreFourmi[i].getPoint() != 0) {
                    //System.out.println("Fourmi " + i + " : ");
                    //System.out.println(mesFourmis[i].getIntelligence());
                    System.out.println("Score : " + scoreFourmi[i].getPoint());
                }*/
            }
            triFourmis(mesFourmis);
            int nbFourmisMeilleures = (int) (POURCENTAGE_FOURMIS_MEILLEURES*mesFourmis.length);
            int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
            int nbFourmisMuter2 = (int) (POURCENTAGE_FOURMIS_MUTATION_2*nbFourmisMuter);
            Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
            for(int i=0;i<nbFourmisMeilleures;i++){
                meilleuresFourmis[i] = mesFourmis[i];
            }
            for(int i=nbFourmisMeilleures;i<mesFourmis.length;i++){
                mesFourmis[i] = null;
            }
            Fourmi[] fourmisApresMut = new Fourmi[nbFourmisMuter];
            fourmisApresMut = muterFourmis(meilleuresFourmis,nbFourmis,nbFourmisMuter,nbFourmisMuter2);

            for(int i=0;i<nbFourmisMuter;i++){
                System.out.println("Fourmi 1 : ");
                System.out.println(fourmisApresMut[i].getIntelligence());
            }











            for (int i=0;i<mesFourmis.length;i++){
                if(mesFourmis[i].getScore().getPoint() != 0){
                    /*System.out.println("Arbre : ");
                    System.out.println(mesFourmis[i].getIntelligence());
                    System.out.println("Position init : ");
                    System.out.println("X : " + posInit[i].getX());
                    System.out.println("Y : " + posInit[i].getY());
                    System.out.println("Type : " + posInit[i]);*/
                    System.out.println("Score fourmi : " + mesFourmis[i].getScore().getPoint());
                }
            }
            for (int i=0;i<mesFourmis.length;i++){
                /*if(scoreFourmi[i].getPoint() != 0) {
                    System.out.println("Score : " + scoreFourmi[i].getPoint());
                    //System.out.println("Fourmi : " + mesFourmis[i]);
                }*/
            }
        }
        /*catch (InvalidDirectionException e) {
            System.out.println(e);
        }*/
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private Fourmi[] muterFourmis(Fourmi[] meilleuresFourmis, int nbFourmis, int nbFourmisMuter, int nbFourmisMuter2) throws IOException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuter];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuter];
        for(int i=0;i<nbFourmisMuter;i++){
            int fourmi1 = (int) (Math.random() * meilleuresFourmis.length);
            int fourmi2=0;
            do {
                fourmi2 = (int) (Math.random() * meilleuresFourmis.length);
            }
            while(fourmi1 == fourmi2);
            System.out.println("Fourmi 1 : " + fourmi1 + " et fourmi 2 : " + fourmi2);
            progMuter[i] = meilleuresFourmis[fourmi1].getIntelligence();
            progMuter[i].croiserProgrammes(meilleuresFourmis[fourmi2].getIntelligence());
            fourmisMuter[i] = new Fourmi(progMuter[i],monMonde.lesFourmilieres(),monMonde.getCarte());
        }
        return fourmisMuter;
    }

    private void triFourmis(Fourmi[] tabFourmis){
        Fourmi temp;
        int j;
        for(int i=0;i<tabFourmis.length;i++){
            temp = tabFourmis[i];
            j=i;
            while((j>=1) && tabFourmis[j-1].getScore().getPoint() < temp.getScore().getPoint()){
                tabFourmis[j] = tabFourmis[j-1];
                j--;
            }
            tabFourmis[j] = temp;
        }
    }


    //lancement d'une simulation avec chargement d'une carte en parametre
    public Simulation(String nomFichier,int nbFourmis){
        Fourmi[] mesFourmis;
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