import Exceptions_Monde.InvalidDirectionException;
import Utile_Fourmi.Fourmi;
import Utile_Fourmi.Score;
import Utile_Monde.Monde;

import java.io.IOException;
import java.util.Scanner;

public class Simulation {
    private Monde monMonde;
    private Fourmi[] mesFourmis;
    private Score[] scoreFourmi;


    public Simulation() {
    }

    //lancement d'une simulation avec la creation d'un monde avec choix des parametres
    /* deroulement :
    creation et initialisation d'un monde
    recuperation des fourmis
    action des fourmis
    recuêration du score
     */
    public Simulation(int hauteur, int largeur, int nbFourmis, int nbCaseFourm, int nbCaseNour) {
        try {
            monMonde = new Monde(hauteur, largeur, nbFourmis, nbCaseFourm, nbCaseNour);
            scoreFourmi = new Score[nbFourmis];
            mesFourmis = monMonde.getFourmis();
            System.out.println(monMonde);
            for (int i = 0; i < nbFourmis; i++) {
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


    //lancement d'une simulation avec chargement d'une carte en parametre
    public Simulation(String nomFichier, int nbFourmis) {
        try {
            monMonde = new Monde(nomFichier, nbFourmis);
            mesFourmis = monMonde.getFourmis();
            scoreFourmi = new Score[nbFourmis];
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


    //methode qui demande à l'utilisateur de son mode de jeu et un nom de fichier s'il veut rentrer une carte
    public void jeu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue, veuillez choisir votre mode de jeu : \n 1) tout aléatoire\n 2)charger une carte ");
        int nb = sc.nextInt();
        if (nb == 1) {
            Simulation S = new Simulation(10, 10, 3, 10, 15);
            System.out.println("Voulez-vous sauvegarder la carte du monde ? Si oui taper le nom du fichier dans lequel vous voulez la sauvegarder\n si non n");
            sc.nextLine(); // on  vide la ligne
            String nomfichier = sc.nextLine();
            if (nomfichier.length() != 1)
                S.monMonde.sauvegarder(nomfichier);
            else {
                if (nomfichier.charAt(0) != 'n') {
                    S.monMonde.sauvegarder(nomfichier);
                }
            }

        } else if (nb == 2) {
            System.out.println("Veuillez saisir un nom de fichier: \n");
            sc.nextLine(); // on  vide la ligne
            String nomfichier = sc.nextLine();
            Simulation S = new Simulation("Cartes/" + nomfichier, 2);
        }
        System.out.println("Voulez-vous sauvegarder le comportement d'une fourmi ? Si oui,veuillez indiquer le numero de la fourmi");
        sc.nextLine(); // on  vide la ligne
        String nomfichier = sc.nextLine();
        if (nomfichier.length() != 1)
            System.out.println("Erreur veuillez rentrer n pour non ou le numero d'une fourmi");
        else {
            /*if( nomfichier.charAt(0)<mesFourmis.length ){
                System.out.println("Veuillez saisir un nom de fichier pour l'enregistrement: \n");
                sc.nextLine(); // on  vide la ligne
                String nomfichier=sc.nextLine();
                S.mesFourmis.sauvegarder(nomfichier);
            }*/

        }

    }
}