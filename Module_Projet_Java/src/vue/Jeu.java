package vue;

import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import util_monde.Simulation;

import java.io.IOException;
import java.util.Scanner;


public class Jeu {
    private Simulation simulation = new Simulation();

    //methode qui demande à l'utilisateur de son mode de jeu et un nom de fichier s'il veut rentrer une carte
    //propose aussi la sauvegarde de la carte
    public void jeu() throws IOException, CloneNotSupportedException, InvalidDirectionException, InvalidNbCaseDiffException, InvalidMapSizeException, InvalidFileFormatException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue, veuillez choisir votre mode de jeu : \n 1)Aléatoire\n 2)Charger une carte ");
        int nb = sc.nextInt();

        switch (nb) {
            case 1:
                int largeur = (int) (Math.random() * 14) + 1;
                int hauteur = (int) (Math.random() * 14) + 1;
                int nbfourmis = (int) (Math.random() * 100);
                int nbCaseFourmiliere = largeur - (int) (Math.random() * largeur / 2);
                int nbCaseNourriture = hauteur - (int) (Math.random() * hauteur / 2);
                //simulation.lancerSimulation(4,4,100,4,4);
                simulation.lancerSimulation(largeur, hauteur, nbfourmis, nbCaseFourmiliere, nbCaseNourriture);
                System.out.println("Voulez-vous sauvegarder la carte du monde ? Si oui taper le nom du fichier dans lequel vous voulez la sauvegarder\n si non n");
                sc.nextLine(); // on  vide la ligne
                String nomfichier = sc.nextLine();
                if (nomfichier.length() != 1)
                    simulation.getMonMonde().sauvegarder(nomfichier);
                else {
                    if (nomfichier.charAt(0) != 'n') {
                        simulation.getMonMonde().sauvegarder(nomfichier);
                    }
                }
                break;
            case 2:
                System.out.println("Veuillez saisir un nom de fichier: \n");
                sc.nextLine(); // on  vide la ligne
                String nomfichiercarte = sc.nextLine();
                simulation.lancerSimulation("Cartes/" + nomfichiercarte, 2);
                break;
        }

        System.out.println("Voulez vous sauvegarder les paramètres de la partie ? Si oui rentrer un nom de fichier");
        sc.nextLine(); // on  vide la ligne
        String nomfichierpar = sc.nextLine();
        if (nomfichierpar.length() != 1) {
            simulation.sauvegarderParametre(nomfichierpar);
        } else {
            if (nomfichierpar.charAt(0) != 'n') {
                simulation.sauvegarderParametre(nomfichierpar);
            }
        }
        /*System.out.println("Voulez-vous sauvegarder le comportement d'une fourmi ? Si oui,veuillez indiquer le numero de la fourmi");
        int n = sc.nextInt();
        System.out.println("Rentrer un nom de fichier");
        sc.nextLine();
        String nomfichier = sc.nextLine();
        if (nomfichier.length() == 1) {
            System.out.println("erreur non valide");
        } else {
            if (nomfichier.charAt(0) != 'n') {
                simulation.getMonMonde().getFourmis()[n].getIntelligence().serialiser(nomfichier);
            }

        }*/
    }
}
