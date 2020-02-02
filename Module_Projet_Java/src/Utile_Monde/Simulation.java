package Utile_Monde;

import Exceptions_Monde.InvalidDirectionException;
import Utile_Fourmi.*;
import Utile_Monde.Monde;
import Interfaces_Global.*;

import java.io.IOException;
import java.util.Scanner;


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
        int nbCoups; // Nombre de coups par évolution
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.1; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        int nbEvolutions; // Nombre d'évolutions que vont subir les fourmis
        double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5; // Pourcentage des fourmis qui vont muter la 2eme fois (échange de noeuds)
        Fourmi[] mesFourmis;
        int nbTransform=0;

        /*Noeud C1 = new Condition("cond_nourriture");
        Noeud C2 = new Condition("cond_fourmiliere");
        Noeud C3 = new Condition("cond_possedeNourriture");
        Noeud A1 = new Action("act_rentrer");
        Noeud A2 = new Action("act_deposer");
        Noeud A3 = new Action("act_allerAleat");
        Noeud A4 = new Action("act_ramasser");
        ProgrammeGenetique P1 = new ProgrammeGenetique(A1);
        ProgrammeGenetique P2 = new ProgrammeGenetique(A2);
        ProgrammeGenetique P3 = new ProgrammeGenetique(A3);
        ProgrammeGenetique P4 = new ProgrammeGenetique(A4);
        ProgrammeGenetique P5 = new ProgrammeGenetique(C2,P2,P1);
        ProgrammeGenetique P6 = new ProgrammeGenetique(C1,P4,P3);
        ProgrammeGenetique P7 = new ProgrammeGenetique(C3,P5,P6);
        P7.numerotationFeuille();
        P7.numerotationNoeud();*/

        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            Case[] posInit = new Case[mesFourmis.length];
            Scanner Sc = new Scanner(System.in);
            System.out.print("Nombre d'évolution : ");
            nbEvolutions = Sc.nextInt();
            System.out.print("Nombre de coups par évolution : ");
            nbCoups = Sc.nextInt();
            for(int nbMut = 0; nbMut < nbEvolutions; nbMut++) {
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.02*(nbMut+1),0.1);
                for (int i = 0; i < mesFourmis.length; i++) {
                    mesFourmis[i].setPosition(monMonde.getCarte().getGrille()[0][0]);
                    posInit[i] = mesFourmis[i].getPosition();
                    for (int j = 0; j < nbCoups; j++) {
                        mesFourmis[i].agir();
                    }
                }
                triFourmis(mesFourmis);
                for (int i = 0; i < nbFourmis; i++) {
                    System.out.println(i + " : " + mesFourmis[i].getScore().getPoint());
                }
                if(nbMut+1 == nbEvolutions){
                    System.out.println("Meilleur score : " + mesFourmis[0].getScore().getPoint());
                    mesFourmis[0].afficherIntelligence();
                }
                int nbFourmisMeilleures = (int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length);
                int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
                int nbFourmisMuter2 = (int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter);
                Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
                for (int i = 0; i < nbFourmisMeilleures; i++) {
                    meilleuresFourmis[i] = mesFourmis[i];
                    mesFourmis[i] = new Fourmi(mesFourmis[i].getIntelligence().clone(), monMonde.lesFourmilieres(), monMonde.getCarte());
                }
                //Fourmi[] fourmisApresMut = new Fourmi[nbFourmisMuter];
                Fourmi[] fourmisApresMut = muterFourmisCroisement(meilleuresFourmis, nbFourmisMuter, nbFourmisMuter2);

                for (int i = nbFourmisMeilleures; i < mesFourmis.length; i++) {
                    mesFourmis[i] = fourmisApresMut[i - nbFourmisMeilleures];
                }

                monMonde.getCarte().razCaseNourriture(); // On remet les cases nourritures à leur quantité initiale pour la simulation suivante
            }
        }
        catch (InvalidDirectionException e) {
            System.out.println(e);
        }
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