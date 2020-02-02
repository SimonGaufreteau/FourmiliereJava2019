package util_fourmi;

import exceptions_monde.InvalidDirectionException;
import interfaces_global.Deplacer;
import interfaces_global.Deposer;
import interfaces_global.Detecter;
import interfaces_global.Ramasser;
import util_monde.Carte;
import util_monde.Case;
import util_monde.CaseFourmiliere;
import util_monde.CaseNourriture;

import java.io.IOException;
import java.util.ArrayList;

public class Fourmi implements Ramasser, Deposer, Deplacer, Detecter {
    private Case position;
    private Score score; //variable qui sera incrémentée au cours du déroulement du jeu
    private boolean porteNourriture;
    private boolean rentreFourmiliere;
    private ArrayList<CaseFourmiliere> listeFourmilieres;
    private ProgrammeGenetique intelligence;

    /*Pour rentrer à la fourmilière la plus proche la fourmi a besoin de connaître toutes les fourmilières
    du Monde dans lequel elle évolue
    On lui passe une case position(son X et son Y sont contenus dans la case)
    */
    public Fourmi(Case position, ArrayList<CaseFourmiliere> listeFourmilieres) throws IOException {
        this.position = position;
        this.listeFourmilieres = listeFourmilieres;
        score = new Score(0);
        porteNourriture = false;
        rentreFourmiliere = false;
        intelligence = new ProgrammeGenetique();
    }

    public Fourmi() throws IOException {
        score = new Score(0);
        porteNourriture = false;
        rentreFourmiliere = false;
        intelligence = new ProgrammeGenetique();
    }

    public Fourmi(ProgrammeGenetique intelligence, ArrayList<CaseFourmiliere> listeFourmilieres, Carte cartecourante) throws IOException {
        this.score = new Score(0);
        this.porteNourriture = false;
        this.rentreFourmiliere = false;
        this.intelligence = intelligence;
        this.listeFourmilieres = listeFourmilieres;
        this.position = new Case(cartecourante);
    }

    public Fourmi(ProgrammeGenetique p) throws IOException {
        this();
        this.intelligence=p;
    }

    public Score getScore() {
        //pour afficher le score on a besoin de le récupérer
        return score;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public boolean transporteNourriture() {
        return porteNourriture;
    }

    /*Fonction utilisée pour ramasser la nourriture*/
    public boolean ramasser() {
        if (detecterCaseNourriture() && !porteNourriture) {
            porteNourriture = ((CaseNourriture) position).enleverNourriture();
            return porteNourriture; //peut être true ou false selon la quantité de nourriture qu'il reste dans la case
        }
        return false;
    }

    /*Pour rentrer à la fourmilière la plus proche la fourmi a besoin de savoir où se situe la
    plus proche des fourmilières comme elle connait toutes la liste il lui suffit de la parcourir*/
    public CaseFourmiliere trouverFourmilierePlusProche() {
        int min = Integer.MAX_VALUE;
        int distance;
        CaseFourmiliere caseFourmilierePlusProche = new CaseFourmiliere(0, 0, new Carte());
        for (CaseFourmiliere caseFourmiliere : listeFourmilieres) {
            distance = position.getCarteCourante().distanceEntreDeuxCases(position, caseFourmiliere);
            if (distance < min) {
                min = distance;
                caseFourmilierePlusProche = caseFourmiliere;
            }
        }
        return caseFourmilierePlusProche;
    }

    /*Permet à la fourmi d'effectuer le trajet d'une case de moins vers la fourmilière
     * à effectuer autant de fois jsq à atteindre la fourmilière
     * Chaque fourmi effectue une action à la fois, donc elle ne peut pas se déplacer en une seule fois
     * vers la fourmilière. */
    public void rentrerFourmiliere() {
        CaseFourmiliere fourmiliere = trouverFourmilierePlusProche();
        try {
            Case voisinHaut = position.getCarteCourante().getVoisin(position.getX(), position.getY(), 'H');
            Case voisinBas = position.getCarteCourante().getVoisin(position.getX(), position.getY(), 'B');
            if (position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinHaut) >
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinBas)) {
                deplacer('B');
                return;
            }
            if (position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinHaut) <
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinBas)) {
                deplacer('H');
                return;
            }
            Case voisinGauche = position.getCarteCourante().getVoisin(position.getX(), position.getY(), 'G');
            Case voisinDroite = position.getCarteCourante().getVoisin(position.getX(), position.getY(), 'D');
            if (position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinGauche) >
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinDroite)) {
                deplacer('D');
                return;
            }
            if (position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinGauche) <
                    position.getCarteCourante().distanceEntreDeuxCases(fourmiliere, voisinDroite)) {
                deplacer('G');
            }
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        }
    }

    //Permets à la fourmi de savoir si elle est sur une case nourriture(true:si oui, false:sinon)
    public boolean detecterCaseNourriture() {
        return this.position instanceof CaseNourriture;
    }

    @Override
    //true si sur CaseFourmiliere
    public boolean detecterCaseFourmiliere() {
        return this.position instanceof CaseFourmiliere;
    }

    @Override
    public boolean deposer() {
        if (porteNourriture) {
            porteNourriture = false;
            return detecterCaseFourmiliere();
        }
        return false;
    }

    public boolean deplacerAleatoirement() throws InvalidDirectionException {
        char direction = ' ';
        int aleat = (int) (Math.random() * 4);
        switch (aleat) {
            case 0:
                direction = 'H';
            case 1:
                direction = 'B';
            case 2:
                direction = 'D';
            case 3:
                direction = 'G';
        }
        return deplacer(direction);
    }

    @Override
    public boolean deplacer(char direction) throws InvalidDirectionException {
        //System.out.println("X : " + position.getCarteCourante().getVoisin(0, 4, 'G').getX());
        //System.out.println("Y : " + position.getCarteCourante().getVoisin(0, 4, 'G').getY());
        position = position.getCarteCourante().getVoisin(position.getX(), position.getY(), direction);
        return true;
    }

    // Cette fonction permet d'agir en fonction de son arbre de décision
    // Pour les conditions, si elle est validée, on passe par l'arbre gauche, sinon par l'arbre droit
    public void agir() throws InvalidDirectionException {
        ProgrammeGenetique noeudEnCours = intelligence;
        if (rentreFourmiliere == true) {
            if (detecterCaseFourmiliere()) {
                rentreFourmiliere = false;
            } else {
                rentrerFourmiliere();
            }
        }
        if (rentreFourmiliere == false) { // Si l'on n'est pas en train de rentrer à la fourmilière
            while ((noeudEnCours.getNoeud().getClass().getName()).equals("util_fourmi.Condition")) {
                if ((noeudEnCours.getValeurNoeud()).equals("cond_nourriture")) {
                    //System.out.println("Nourriture ?");
                    if (detecterCaseNourriture()) {
                        //System.out.println("  La fourmi est sur une case nourriture");
                        noeudEnCours = noeudEnCours.getAGauche();
                    } else {
                        //System.out.println("  La fourmi n'est pas sur une case nourriture");
                        noeudEnCours = noeudEnCours.getADroite();
                    }

                } else if ((noeudEnCours.getValeurNoeud()).equals("cond_fourmiliere")) {
                    //System.out.println("Condition fourmiliere");
                    if (detecterCaseFourmiliere()) {
                        rentreFourmiliere = false;
                        //System.out.println("  La fourmi est sur une case fourmilière");
                        noeudEnCours = noeudEnCours.getAGauche();
                    } else {
                        //System.out.println("  La fourmi n'est pas sur une case fourmilière");
                        noeudEnCours = noeudEnCours.getADroite();
                    }

                } else if ((noeudEnCours.getValeurNoeud()).equals("cond_possedeNourriture")) {
                    //System.out.println("Condition possède nourriture");
                    if (transporteNourriture()) {
                        //System.out.println("  La fourmi possède de la nourriture sur elle");
                        noeudEnCours = noeudEnCours.getAGauche();
                    } else {
                        //System.out.println("  La fourmi ne possède pas de la nourriture sur elle");
                        noeudEnCours = noeudEnCours.getADroite();
                    }

                }
            }
            //System.out.println(noeudEnCours.getValeurNoeud());
            if ((noeudEnCours.getValeurNoeud()).equals("act_allerGauche")) {
                deplacer('G');
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_allerDroite")) {
                deplacer('D');
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_allerHaut")) {
                deplacer('H');
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_allerBas")) {
                deplacer('B');
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_allerAleat")) {
                deplacerAleatoirement();
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_ramasser")) {
                if (ramasser()) { // Si la fourmi a pu ramasser de la nourriture
                    score.augmenterScore(2);
                }
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_rentrer")) {
                rentreFourmiliere = true;
                rentrerFourmiliere();
            } else if ((noeudEnCours.getValeurNoeud()).equals("act_deposer")) {
                if (deposer()) {
                    score.augmenterScore(5);
                }
            }
        }
    }

    public ProgrammeGenetique getIntelligence() {
        return intelligence;
    }

    public void afficherIntelligence() {
        intelligence.afficherArbre();
    }

    public Case getPosition() {
        return position;
    }

    public String toString() {
        String S = "";
        S = "Score : " + score.getPoint() + "\n";
        /*S += "Position : " + position.getX() + " / " + position.getY() + "\n";*/
        S += "Intelligence : \n" + intelligence.toString();
        return S;
    }
}
