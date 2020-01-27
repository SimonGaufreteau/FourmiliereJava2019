package util_monde;

import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import util_monde.Case;
import util_monde.Monde;

import util_fourmi.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Simulation {

    private Monde monMonde;
    private Score[] scoreFourmi;
    private Score[] scoreFourmi2;

    public Monde getMonMonde() {
        return monMonde;
    }

    //lancement d'une simulation avec la creation d'un monde avec choix des parametres
    /* déroulement :
    création et initialisation d'un monde
    récupération des fourmis
    action des fourmis
    évolution
    recupération du score
     */
    public Simulation() {
    }

    public void lancerSimulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourmiliere, int nbCaseNourriture) throws IOException, CloneNotSupportedException, InvalidNbCaseDiffException, InvalidDirectionException {
        final int NB_COUPS = 10;
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        final int NB_EVOLUTIONS = 5;
        final double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5;
        Fourmi[] mesFourmis;
        int nbTransform = 0;

        //copie de l'arbre genetique
        Noeud C1 = new Condition("cond_nourriture");
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
        ProgrammeGenetique P5 = new ProgrammeGenetique(C2, P2, P1);
        ProgrammeGenetique P6 = new ProgrammeGenetique(C1, P4, P3);
        ProgrammeGenetique P7 = new ProgrammeGenetique(C3, P5, P6);
        P7.numerotationFeuille();
        P7.numerotationNoeud();

        monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourmiliere, nbCaseNourriture);
        Monde temp = monMonde.clone();
        mesFourmis = monMonde.getFourmis();
        scoreFourmi = new Score[mesFourmis.length];
        Case[] posInit = new Case[mesFourmis.length];
        for (int nbMut = 0; nbMut < NB_EVOLUTIONS; nbMut++) {

            monMonde = temp.clone();
            System.out.println("---------- Evolution " + nbMut + " ----------");
            System.out.println(monMonde);
            POURCENTAGE_FOURMIS_MEILLEURES = 0.05 * (nbMut + 1);
            for (int i = 0; i < mesFourmis.length; i++) {
                mesFourmis[i] = new Fourmi(P7.clone(), monMonde.lesFourmilieres(), monMonde.getCarte());
                mesFourmis[i].setPosition(monMonde.getCarte().getGrille()[0][0]);
                posInit[i] = mesFourmis[i].getPosition();
                System.out.println("Fourmi " + i + " :");
                for (int j = 0; j < NB_COUPS; j++) {
                    System.out.println("Coup " + j + " : " + mesFourmis[i].getPosition().getX() + " " + mesFourmis[i].getPosition().getY() + " " + mesFourmis[i].getPosition());
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
            //System.out.println(scoreFourmi[0].meilleurScore(scoreFourmi ));
            int nbFourmisMeilleures = (int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length);
            int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
            int nbFourmisMuter2 = (int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter);
            Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
            System.out.println("Meilleures fourmis : " + nbFourmisMeilleures + " (" + POURCENTAGE_FOURMIS_MEILLEURES * 100 + "%)");
            System.out.println("Fourmis à muter : " + nbFourmisMuter);
            //System.out.println("Meilleures fourmis de la simulation : ");
            for (int i = 0; i < nbFourmisMeilleures; i++) {
                meilleuresFourmis[i] = mesFourmis[i];
                //System.out.println();
                //meilleuresFourmis[i].afficherIntelligence();
                mesFourmis[i] = new Fourmi(mesFourmis[i].getIntelligence().clone(), monMonde.lesFourmilieres(), monMonde.getCarte());
            }
            Fourmi[] fourmisApresMut = new Fourmi[nbFourmisMuter];
            fourmisApresMut = muterFourmis(meilleuresFourmis, nbFourmis, nbFourmisMuter, nbFourmisMuter2);

            for (int i = nbFourmisMeilleures; i < mesFourmis.length; i++) {
                mesFourmis[i] = fourmisApresMut[i - nbFourmisMeilleures];
            }
            monMonde = new Monde(temp.getCarte(), mesFourmis);
        }
    }

    private Fourmi[] muterFourmis(Fourmi[] meilleuresFourmis, int nbFourmis, int nbFourmisMuter, int nbFourmisMuter2) throws IOException, CloneNotSupportedException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuter];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuter];
        for (int i = 0; i < nbFourmisMuter; i++) {
            int fourmi1 = (int) (Math.random() * meilleuresFourmis.length);
            int fourmi2;
            do {
                fourmi2 = (int) (Math.random() * meilleuresFourmis.length);
            }
            while (fourmi1 == fourmi2);
            //System.out.println(i + " : Fourmi 1 : " + fourmi1 + " et fourmi 2 : " + fourmi2);
            progMuter[i] = meilleuresFourmis[fourmi1].getIntelligence().clone();
            progMuter[i].croiserProgrammes(meilleuresFourmis[fourmi2].getIntelligence().clone());
            fourmisMuter[i] = new Fourmi(progMuter[i], monMonde.lesFourmilieres(), monMonde.getCarte());
        }
        return fourmisMuter;
    }

    private void triFourmis(Fourmi[] tabFourmis) {
        Fourmi temp;
        int j;
        for (int i = 0; i < tabFourmis.length; i++) {
            temp = tabFourmis[i];
            j = i;
            while ((j >= 1) && tabFourmis[j - 1].getScore().getPoint() < temp.getScore().getPoint()) {
                tabFourmis[j] = tabFourmis[j - 1];
                j--;
            }
            tabFourmis[j] = temp;
        }
    }


    //lancement d'une simulation avec chargement d'une carte en parametre
    //le nombre de case nourriture et fourmiliere est defini aleatoirement  respectivement entre 1 et la largeur et 1 et la  hauteur
    public void lancerSimulation(String nomFichier, int nbFourmis) throws InvalidMapSizeException, InvalidFileFormatException, IOException, CloneNotSupportedException, InvalidDirectionException, InvalidNbCaseDiffException {
        Fourmi[] mesFourmis;
        monMonde = new Monde(nomFichier, nbFourmis);
        int largeur = monMonde.getCarte().getLargeur();
        int hauteur = monMonde.getCarte().getHauteur();
        lancerSimulation(largeur, hauteur, nbFourmis, largeur - (int) (Math.random() * largeur), hauteur - (int) (Math.random() * hauteur));
    }


    //probleme manque le nb de case nourriture
    public void sauvegarderParametre(String nomFichier) throws IOException {
        nomFichier = System.getProperty("user.dir") + "\\Module_Projet_Java\\Sauvegardes\\" + nomFichier;

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), StandardCharsets.UTF_8));
        //on ecrit la carte du monde
        writer.write(monMonde.getCarte().getHauteur() + " " + monMonde.getCarte().getLargeur() + "\n" + monMonde.getCarte().getGrille().toString() + "\n");
        //Puis le nombre de fourmis et de fourmiliere
        //manque le nombre de case nourriture.
        writer.write(monMonde.getFourmis().length + " \n" + monMonde.lesFourmilieres().size() + "\n");
        writer.close();
    }
}

