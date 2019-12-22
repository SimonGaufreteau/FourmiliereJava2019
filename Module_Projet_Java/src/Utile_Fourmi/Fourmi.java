package Utile_Fourmi;

public class Fourmi {
    //Classe définie dans "master". Utilisée ici pour avoid les erreurs.
    private static int nbFourmi=0;
    private int nb;

    public Fourmi(){
        this.nb=nbFourmi;
        nbFourmi++;
    }
    //tests

    public String toString(){
        return ""+nb;
    }
}
