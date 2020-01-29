package util_monde;

import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import util_fourmi.Fourmi;
import util_fourmi.ProgrammeGenetique;
import util_fourmi.Score;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Simulation {

    private Monde monMonde;
    private Score[] scoreFourmi;
    private Score[] scoreFourmi2;

    public Monde getMonMonde() {
        return monMonde;
    }


    public Simulation(){

    }

    public void lancerSimulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour) throws IOException {
        int nbCoups; // Nombre de coups par évolution
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        int nbEvolutions; // Nombre d'évolutions que vont subir les fourmis
        double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5; // Pourcentage des fourmis qui vont muter la 2eme fois (échange de noeuds)
        Fourmi[] mesFourmis;
        int nbTransform=0;

        try {
            //création du monde et des fourmis
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            //initialisation des positions initiales et du tableaux des scores
            scoreFourmi = new Score[mesFourmis.length];
            Case[] posInit = new Case[mesFourmis.length];
            //demande de saisi à l'utilisateur du nb de coup et d'evolution
            Scanner Sc = new Scanner(System.in);
            System.out.print("Nombre d'évolution : ");
            nbEvolutions = Sc.nextInt();
            System.out.print("Nombre de coups par évolution : ");
            nbCoups = Sc.nextInt();

            for(int nbMut = 0; nbMut < nbEvolutions; nbMut++) {
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.05*(nbMut+1),0.7);
                for (int i = 0; i < mesFourmis.length; i++) {
                    //on met en position iitiale la position de la fourmi ( change a chaque evolution)
                    //mesFourmis[i].setPosition(monMonde.getCarte().getGrille()[0][0]);
                    posInit[i] = mesFourmis[i].getPosition();
                    for (int j = 0; j < nbCoups; j++) {
                        mesFourmis[i].agir();
                    }
                }
                triFourmis(mesFourmis);
                for (int i = 0; i < nbFourmis; i++) {
                    System.out.println(i + " : " + mesFourmis[i].getScore().getPoint());
                }

                // MUTATION
                int nbFourmisMeilleures = (int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length);
                int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
                int nbFourmisMuter2 = (int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter);
                Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
                System.out.println("Meilleures fourmis : " + nbFourmisMeilleures + " (" + POURCENTAGE_FOURMIS_MEILLEURES*100 + "%)");
                System.out.println("Fourmis à muter : " + nbFourmisMuter);
                //on recupere nos meilleures fourmmies
                for (int i = 0; i < nbFourmisMeilleures; i++) {
                    meilleuresFourmis[i] = mesFourmis[i];
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
    //le nombre de case nourriture et fourmiliere est defini aleatoirement  respectivement entre 1 et la largeur et 1 et la  hauteur
    public void lancerSimulation(String nomFichier, int nbFourmis) throws InvalidMapSizeException, InvalidFileFormatException, IOException, CloneNotSupportedException, InvalidDirectionException, InvalidNbCaseDiffException {
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


