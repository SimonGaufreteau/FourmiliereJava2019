package vue;

import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import util_fourmi.ProgrammeGenetique;
import util_monde.Serialization;
import util_monde.Simulation;

import java.io.IOException;
import java.util.Scanner;


public class Jeu {
    private Simulation simulation = new Simulation();

    //methode qui demande à l'utilisateur de son mode de jeu et un nom de fichier s'il veut rentrer une carte
    //propose aussi la sauvegarde de la carte
    public void jeu() throws IOException, CloneNotSupportedException, InvalidDirectionException, InvalidNbCaseDiffException, InvalidMapSizeException, InvalidFileFormatException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue, veuillez choisir votre mode de jeu : \n 1)Création d'un monde et de fourmis aléatoire \n 2)Charger une carte \n 3)Tester isolement le comportement d'une fourmi ");
        int nb = sc.nextInt();
        int nbCoups; // Nombre de coups par évolution
        int nbEvolutions; // Nombre d'évolutions que vont subir les fourmis
        //demande de saisi à l'utilisateur du nb de coup et d'evolution
        Scanner Sc = new Scanner(System.in);
        System.out.print("Nombre d'évolution : ");
        nbEvolutions = Sc.nextInt();
        System.out.print("Nombre de coups par évolution : ");
        nbCoups = Sc.nextInt();

        switch (nb) {
            case 1:
                int largeur = Math.max((int) (Math.random() * 14), 2);
                int hauteur = Math.max((int) (Math.random() * 14), 2);
                int nbfourmis = (int) (Math.random() * 1000) + 6;
                int nbCaseFourmiliere = Math.max((int) (Math.random() * largeur / 2), largeur);
                int nbCaseNourriture = Math.max((int) (Math.random() * hauteur / 2), hauteur);
                simulation.lancerSimulation(largeur, hauteur, nbfourmis, nbCaseFourmiliere, nbCaseNourriture, nbEvolutions, nbCoups);

                System.out.println("Voulez-vous sauvegarder la carte du monde ? Si oui taper le nom du fichier dans lequel vous voulez la sauvegarder\n si non n");
                sc.nextLine(); // on  vide la ligne
                String nomFichier = sc.nextLine();
                if (nomFichier.length() != 1)
                    simulation.getMonMonde().sauvegarder(nomFichier);
                else {
                    if (nomFichier.charAt(0) != 'n') {
                        simulation.getMonMonde().sauvegarder(nomFichier);
                    }
                }
                break;
            case 2:
                System.out.println("Veuillez saisir un nom de fichier : ");
                sc.nextLine();
                String nomFichierCarte = sc.nextLine();
                System.out.println("Combien de fourmis voulez vous ? (0 pour un nombre aléatoire) ");
                int nbfourm = sc.nextInt();
                if (nbfourm == 0)
                    nbfourm = (int) (Math.random() * 1000) + 6;
                simulation.lancerSimulation("Module_Projet_Java\\Cartes\\" + nomFichierCarte, nbfourm, nbEvolutions, nbCoups);
                break;
            case 3:
                //tester le comportement d'une fourmi isolement
                System.out.println("Veuillez saisir un nom de fichier pour la carte: ");
                nomFichierCarte = sc.nextLine();
                sc.nextLine();
                System.out.println("Veuillez saisir un nom de fichier pour la fourmi: ");
                String nomFichierFourmi = sc.nextLine();
                ProgrammeGenetique p = Serialization.deserialiseProgrammeGenetique(nomFichierFourmi);
                //Lancer la simulation
                simulation.lancerSimulation("Cartes\\" + nomFichierCarte, 1, nbEvolutions, nbCoups, p);
                break;
        }
        System.out.println("Voulez vous sauvegarder les paramètres de la partie ? Si oui rentrer un nom de fichier");
        String nomFichierParametre = sc.nextLine();
        if (nomFichierParametre.length() != 1) {
            simulation.sauvegarderParametre(nomFichierParametre);
        } else {
            if (nomFichierParametre.charAt(0) != 'n') {
                simulation.sauvegarderParametre(nomFichierParametre);
            }
        }

        System.out.println("Voulez-vous sauvegarder le comportement d'une fourmi ? Si oui,veuillez indiquer le numéro de la fourmi");
        int n = sc.nextInt();
        System.out.println("Rentrer un nom de fichier");
        sc.nextLine();
        String nomFichierFourmi = sc.nextLine();
        if (nomFichierFourmi.length() == 1) {
            System.out.println("Erreur non valide");
        } else {
            if (nomFichierFourmi.charAt(0) != 'n') {
                ProgrammeGenetique programmeGenetique = simulation.getMonMonde().getFourmis()[n].getIntelligence();
                Serialization.serialiseProgrammeGenetique(programmeGenetique, nomFichierFourmi);
            }
        }
    }
}
