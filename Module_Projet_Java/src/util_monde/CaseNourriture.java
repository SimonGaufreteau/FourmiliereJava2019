package util_monde;

public class CaseNourriture extends Case{
    private int quantiteNourriture; //qté de nourriture restante sur la case

    public CaseNourriture(int x, int y, int quantiteNourriture, Carte carteCourante) {
        super(x, y,carteCourante);
        this.quantiteNourriture= quantiteNourriture;
    }

    /*True,lorsque de la nourriture est ramassée sur une case
     * alors on décrémente la quantité de nourriture disponible sur cette case.*/
    public boolean enleverNourriture() {
        if(quantiteNourriture > 0) {
            quantiteNourriture = quantiteNourriture - 1;
            return true;
        }
        return false;
    }

    public int getQuantiteNourriture(){
        return quantiteNourriture;
    }

    //Pourra être utile si on fait un remplissage des case nourriture tous les x intervalles de temps au cours de la partie
    public void setQuantiteNourriture(int quantiteNourriture) {
        this.quantiteNourriture = quantiteNourriture;
    }

    public String toString(){
        return "N";
    }
}