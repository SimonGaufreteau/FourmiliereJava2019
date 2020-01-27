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

    public Simulation(){

    }

    public void lancerSimulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour) throws IOException {
        final int NB_COUPS = 10;
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        final int NB_EVOLUTIONS = 100;
        final double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5;
        Fourmi[] mesFourmis;
        int nbTransform=0;

        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            Case[] posInit = new Case[mesFourmis.length];
            for(int nbMut = 0; nbMut < NB_EVOLUTIONS; nbMut++) {
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.05*(nbMut+1),0.7);
                for (int i = 0; i < mesFourmis.length; i++) {
                    mesFourmis[i].setPosition(monMonde.getCarte().getGrille()[0][0]);
                    posInit[i] = mesFourmis[i].getPosition();
                    //System.out.println("Fourmi " + i + " :");
                    for (int j = 0; j < NB_COUPS; j++) {
                        //System.out.println("Coup " + j + " : " + mesFourmis[i].getPosition().getX() + " " + mesFourmis[i].getPosition().getY() + " " + mesFourmis[i].getPosition());
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
                for (int i = 0; i < nbFourmis; i++) {
                    //System.out.println(i + " : " + mesFourmis[i].getIntelligence());
                    System.out.println(i + " : " + mesFourmis[i].getScore().getPoint());
                }
                int nbFourmisMeilleures = (int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length);
                int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
                int nbFourmisMuter2 = (int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter);
                Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
                System.out.println("Meilleures fourmis : " + nbFourmisMeilleures + " (" + POURCENTAGE_FOURMIS_MEILLEURES*100 + "%)");
                System.out.println("Fourmis à muter : " + nbFourmisMuter);
                //System.out.println("Meilleures fourmis de la simulation : ");
                for (int i = 0; i < nbFourmisMeilleures; i++) {
                    meilleuresFourmis[i] = mesFourmis[i];
                    //System.out.println();
                    //meilleuresFourmis[i].afficherIntelligence();
                    mesFourmis[i] = new Fourmi(mesFourmis[i].getIntelligence().clone(), monMonde.lesFourmilieres(), monMonde.getCarte());
                }
                Fourmi[] fourmisApresMut = new Fourmi[nbFourmisMuter];
                fourmisApresMut = muterFourmisCroisement(meilleuresFourmis, nbFourmisMuter, nbFourmisMuter2);

                for (int i = nbFourmisMeilleures; i < mesFourmis.length; i++) {
                    mesFourmis[i] = fourmisApresMut[i - nbFourmisMeilleures];
                }
                monMonde.getCarte().razCaseNourriture(); // On remet les cases nourritures à leur quantité initiale pour la simulation suivante
            }
        }
        /*catch (InvalidDirectionException e) {
            System.out.println(e);
        }*/
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private Fourmi[] muterFourmisCroisement(Fourmi[] meilleuresFourmis, int nbFourmisMuterCroisement, int nbFourmisMuterEchange) throws IOException, CloneNotSupportedException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuterCroisement];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuterCroisement];
        for(int i=0;i<nbFourmisMuterCroisement;i++){
            int fourmi1 = (int) (Math.random() * meilleuresFourmis.length);
            int fourmi2;
            do {
                fourmi2 = (int) (Math.random() * meilleuresFourmis.length);
            }
            while(fourmi1 == fourmi2);
            //System.out.println(i + " : Fourmi 1 : " + fourmi1 + " et fourmi 2 : " + fourmi2);
            progMuter[i] = meilleuresFourmis[fourmi1].getIntelligence().clone();
            progMuter[i].croiserProgrammes(meilleuresFourmis[fourmi2].getIntelligence().clone());
            fourmisMuter[i] = new Fourmi(progMuter[i],monMonde.lesFourmilieres(),monMonde.getCarte());
        }
        Fourmi[] fourmisMuterEchange = new Fourmi[nbFourmisMuterEchange];
        for(int i=0;i<nbFourmisMuterEchange;i++){
            fourmisMuterEchange[i] = fourmisMuter[i];
        }
        fourmisMuterEchange = muterFourmisEchange(fourmisMuterEchange,nbFourmisMuterEchange);
        Fourmi[] fourmisFinales = new Fourmi[nbFourmisMuterCroisement];
        for(int i=0;i<nbFourmisMuterEchange;i++){
            fourmisFinales[i] = fourmisMuterEchange[i];
        }
        for(int i=nbFourmisMuterEchange;i<nbFourmisMuterCroisement;i++){
            fourmisFinales[i] = fourmisMuter[i];
        }
        return fourmisFinales;
    }

    private Fourmi[] muterFourmisEchange(Fourmi[] meilleuresFourmis, int nbFourmisMuterEchange) throws CloneNotSupportedException, IOException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuterEchange];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuterEchange];
        for(int i=0;i<nbFourmisMuterEchange;i++){
            int choixEchange = (int) (Math.random() * 3);
            progMuter[i] = meilleuresFourmis[i].getIntelligence().clone();
            switch(choixEchange){
                case 0: progMuter[i].remplacerAction();
                case 1: progMuter[i].remplacerCondition();
                case 2: progMuter[i].echangerSousArbres();
            }
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