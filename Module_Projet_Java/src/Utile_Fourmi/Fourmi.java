package Utile_Fourmi;
import Interfaces_Global.*;

public class Fourmi implements Ramasser, Deposer, Deplacer, Detecter{
    //Classe définie dans "master". Utilisée ici pour éviter les erreurs.
    private static int nbFourmi=0;
    private int nb;
    private int x; //x, y définissent la position de la fourmi sur la carte
    private int y;
    private int score; //variable qui sera incrémentée au cours du déroulement du jeu

    public Fourmi(){
        this.nb=nbFourmi;
        nbFourmi++;
    }

    public Fourmi (int x, int y) {
        this.x=x;
        this.y=y;
        this.score=0; //initialisation à zéro
    }

    public int getScore() {
        //pour afficher le score on a besoin de le récupérer
        return score;
    }

    public int getX() {
        //on a besoin de connaitre la position de la fourmi
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        //lors de son déplacement on aura besoin de modifier les valeurs de position
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScore(int score) {
        //pour pouvoir incrémenter le score au cours du jeu on aura besoin de son accesseur
        this.score = score;
    }

    public boolean deposer(){
        return true;
    }

    public boolean ramasser(){
        return true;
    }

    public boolean deplacer(){
        return true;
    }

    public Case detecter(){
        return Case;
    }

    public String toString(){
        return ""+nb;
    }
}
