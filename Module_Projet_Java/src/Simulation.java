import Exceptions_Monde.InvalidDirectionException;
import Utile_Fourmi.Fourmi;
import Utile_Fourmi.Score;
import Utile_Monde.Monde;
import Interfaces_Global.*;

public class Simulation {
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
    public Simulation(int hauteur, int largeur, int nbFourmis,int nbCaseFourm,int nbCaseNour) {
        try {
            monMonde = new Monde(hauteur, largeur, nbFourmis,nbCaseFourm,nbCaseNour);
            mesFourmis= monMonde.getFourmis();
            System.out.println(monMonde);
            for (int i = 0; i < mesFourmis.length; i++) {
                mesFourmis[i].agir();
                scoreFourmi[i] = mesFourmis[i].getPoint();
                System.out.println(scoreFourmi[i]);
            }
        } catch (InvalidDirectionException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //lancement d'une simulation avec chargement d'une carte en parametre
    public Simulation(String nomFichier,int nbFourmis){
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