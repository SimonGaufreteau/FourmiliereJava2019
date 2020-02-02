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

    public Monde getMonMonde() {
        return monMonde;
    }


    public Simulation() {

    }

    public void lancerSimulation(int largeur, int hauteur, int nbFourmis, int nbCaseFourm, int nbCaseNour, int nbEvolutions, int nbCoups) throws IOException {
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5; // Pourcentage des fourmis qui vont muter la 2eme fois (échange de noeuds)
        Fourmi[] mesFourmis;

        try {
            monMonde = new Monde(largeur, hauteur, nbFourmis, nbCaseFourm, nbCaseNour);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[mesFourmis.length];
            int nbFourmisMeilleures = 2;

            //Case[] posInit = new Case[mesFourmis.length];
            for (int nbMut = 0; nbMut < nbEvolutions; nbMut++) {
                nbFourmisMeilleures = Math.max((int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length), 2);
                int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
                int nbFourmisMuter2 = Math.max((int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter), 2);
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                actionEtMutation(mesFourmis, nbCoups, nbMut, nbFourmisMeilleures, nbFourmisMuter, nbFourmisMuter2);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.05 * (nbMut + 1), 0.7);
            }
            affichagefindepartie(mesFourmis, nbFourmisMeilleures);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Lancement d'une simulation avec le chargement d'une carte et d'un programme génétique
     *
     * @param nomFichier
     * @param nbFourmis
     * @param nbEvolutions
     * @param nbCoups
     * @param programmeGenetique
     * @throws InvalidMapSizeException
     * @throws InvalidFileFormatException
     * @throws IOException
     * @throws CloneNotSupportedException
     * @throws InvalidDirectionException
     * @throws InvalidNbCaseDiffException
     */
    public void lancerSimulation(String nomFichier, int nbFourmis, int nbEvolutions, int nbCoups, ProgrammeGenetique programmeGenetique) throws InvalidMapSizeException, InvalidFileFormatException, IOException {
        monMonde = new Monde(nomFichier, nbFourmis,programmeGenetique);
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5; // Pourcentage des fourmis qui vont muter la 2eme fois (échange de noeuds)
        Fourmi[] mesFourmis;

        try {
            mesFourmis = monMonde.getFourmis();
            //initialisation variables pour mutations
            int nbFourmisMeilleures = Math.max((int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length), 2);
            int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
            int nbFourmisMuter2 = Math.max((int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter), 2);


            for (int nbMut = 0; nbMut < nbEvolutions; nbMut++) {
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                actionEtMutation(mesFourmis, nbCoups, nbMut, nbFourmisMeilleures, nbFourmisMuter, nbFourmisMuter2);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.05 * (nbMut + 1), 0.7);
            }
            affichagefindepartie(mesFourmis, nbFourmisMeilleures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lancement d'une simulation avec chargement d'une carte en parametre
    //le nombre de case nourriture et fourmiliere est defini aleatoirement  respectivement entre 1 et la largeur et 1 et la  hauteur
    public void lancerSimulation(String nomFichier, int nbFourmis, int nbEvolutions, int nbCoups) throws InvalidMapSizeException, InvalidFileFormatException, IOException, CloneNotSupportedException, InvalidDirectionException, InvalidNbCaseDiffException {
        monMonde = new Monde(nomFichier, nbFourmis);
        double POURCENTAGE_FOURMIS_MEILLEURES = 0.05; // On pourra fait augmenter ce pourcentage au fur et à mesure des évolutions
        double POURCENTAGE_FOURMIS_MUTATION_2 = 0.5; // Pourcentage des fourmis qui vont muter la 2eme fois (échange de noeuds)
        Fourmi[] mesFourmis;

        try {
            mesFourmis = monMonde.getFourmis();
            //initialisation varibales pour mutations
            int nbFourmisMeilleures = Math.max((int) (POURCENTAGE_FOURMIS_MEILLEURES * mesFourmis.length), 2);
            int nbFourmisMuter = nbFourmis - nbFourmisMeilleures;
            int nbFourmisMuter2 = Math.max((int) (POURCENTAGE_FOURMIS_MUTATION_2 * nbFourmisMuter), 2);


            for (int nbMut = 0; nbMut < nbEvolutions; nbMut++) {
                System.out.println("---------- Evolution " + nbMut + " ----------");
                System.out.println(monMonde);
                actionEtMutation(mesFourmis, nbCoups, nbMut, nbFourmisMeilleures, nbFourmisMuter, nbFourmisMuter2);
                POURCENTAGE_FOURMIS_MEILLEURES = Math.min(0.05 * (nbMut + 1), 0.7);
            }
            affichagefindepartie(mesFourmis, nbFourmisMeilleures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actionEtMutation(Fourmi[] mesFourmis, int nbMut, int nbCoups, int nbFourmisMeilleures, int nbFourmisMuter, int nbFourmisMuter2) throws InvalidDirectionException, IOException, CloneNotSupportedException {
        for (int i = 0; i < mesFourmis.length; i++) {
            for (int j = 0; j < nbCoups; j++) {
                mesFourmis[i].agir();
            }
        }
        triFourmis(mesFourmis);
        scoreFourmi = new Score[mesFourmis.length];
        for (int i = 0; i < mesFourmis.length; i++) {
            scoreFourmi[i] = mesFourmis[i].getScore();
            System.out.println(i + " : " + scoreFourmi[i].getPoint());
        }
        // MUTATION
        if(mesFourmis.length<nbFourmisMeilleures) nbFourmisMeilleures=mesFourmis.length;
        Fourmi[] meilleuresFourmis = new Fourmi[nbFourmisMeilleures];
        //System.out.println("Meilleures fourmis : " + nbFourmisMeilleures + " (" + POURCENTAGE_FOURMIS_MEILLEURES*100 + "%)");
        //System.out.println("Fourmis à muter : " + nbFourmisMuter);
        //on recupere nos meilleures fourmmies
        for (int i = 0; i < nbFourmisMeilleures; i++) {
            meilleuresFourmis[i] = mesFourmis[i];
            mesFourmis[i] = new Fourmi(mesFourmis[i].getIntelligence().clone(), monMonde.lesFourmilieres(), monMonde.getCarte());
        }
        if(nbFourmisMuter>0) {
            Fourmi[] fourmisApresMut = new Fourmi[nbFourmisMuter];
            fourmisApresMut = muterFourmisCroisement(meilleuresFourmis, nbFourmisMuter, nbFourmisMuter2);

            for (int i = nbFourmisMeilleures; i < mesFourmis.length; i++) {
                mesFourmis[i] = fourmisApresMut[i - nbFourmisMeilleures];
            }
        }
        monMonde.getCarte().razCaseNourriture(); // On remet les cases nourritures à leur quantité initiale pour la simulation suivante
    }

    private Fourmi[] muterFourmisCroisement(Fourmi[] meilleuresFourmis, int nbFourmisMuterCroisement, int nbFourmisMuterEchange) throws IOException, CloneNotSupportedException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuterCroisement];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuterCroisement];
        for (int i = 0; i < nbFourmisMuterCroisement; i++) {
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
        Fourmi[] fourmisMuterEchange = new Fourmi[nbFourmisMuterEchange];
        for (int i = 0; i < nbFourmisMuterEchange; i++) {
            fourmisMuterEchange[i] = fourmisMuter[i];
        }
        fourmisMuterEchange = muterFourmisEchange(fourmisMuterEchange, nbFourmisMuterEchange);
        Fourmi[] fourmisFinales = new Fourmi[nbFourmisMuterCroisement];
        for (int i = 0; i < nbFourmisMuterEchange; i++) {
            fourmisFinales[i] = fourmisMuterEchange[i];
        }
        for (int i = nbFourmisMuterEchange; i < nbFourmisMuterCroisement; i++) {
            fourmisFinales[i] = fourmisMuter[i];
        }
        return fourmisFinales;
    }

    private Fourmi[] muterFourmisEchange(Fourmi[] meilleuresFourmis, int nbFourmisMuterEchange) throws CloneNotSupportedException, IOException {
        ProgrammeGenetique[] progMuter = new ProgrammeGenetique[nbFourmisMuterEchange];
        Fourmi[] fourmisMuter = new Fourmi[nbFourmisMuterEchange];
        for (int i = 0; i < nbFourmisMuterEchange; i++) {
            int choixEchange = (int) (Math.random() * 3);
            progMuter[i] = meilleuresFourmis[i].getIntelligence().clone();
            switch (choixEchange) {
                case 0:
                    progMuter[i].remplacerAction();
                case 1:
                    progMuter[i].remplacerCondition();
                case 2:
                    progMuter[i].echangerSousArbres();
            }
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

    private void affichagefindepartie(Fourmi[] mesFourmis, int nbFourmisMeilleures) {
        Scanner Sc = new Scanner(System.in);
        System.out.println("Le meilleur score est : " + mesFourmis[0].getScore().meilleurScore(scoreFourmi));
        System.out.println("Le pire score est : " + mesFourmis[0].getScore().pireScore(scoreFourmi));
        System.out.println("Le score moyen est : " + mesFourmis[0].getScore().scoreMoyen(scoreFourmi) + "\n");
        System.out.println("Voulez-vous afficher les meilleures fourmis (arbres génétiques) ? \n o : oui \n n : non ");
        String choix = Sc.nextLine();
        if (nbFourmisMeilleures>mesFourmis.length) nbFourmisMeilleures=mesFourmis.length;
        if (choix.length() == 1 && choix.equals("o")) {
            triFourmis(mesFourmis);
            for (int i = 0; i < nbFourmisMeilleures; i++) {
                System.out.println("Fourmi " + i + ":\n" + mesFourmis[i].getIntelligence());
            }
        }
    }


    public void sauvegarderParametre(String nomFichier, int nbevol, int nbcoups) throws IOException {
        nomFichier = System.getProperty("user.dir") + "\\Module_Projet_Java\\Sauvegardes\\" + nomFichier;

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), StandardCharsets.UTF_8));
        writer.write("Hauteur de la carte : " + monMonde.getCarte().getHauteur() + " " + "Largeur de la carte :" + monMonde.getCarte().getLargeur() + "\n" + monMonde.getCarte().toString() + "\n");
        writer.write("Nombre total de fourmis :" + monMonde.getFourmis().length + " \n" + "Nombre de fourmilières :" + monMonde.lesFourmilieres().size() + "\n");
        writer.write("Nombre de nourriture:" + monMonde.laNourriture().size() + "\n" + "Nombre d'évolution :"+ nbevol + "\n Nombre de coups par évolution :" + nbcoups);
        writer.close();
    }
}


