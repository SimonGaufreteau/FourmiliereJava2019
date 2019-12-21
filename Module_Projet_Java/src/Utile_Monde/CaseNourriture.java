package Utile_Monde;

public class CaseNourriture extends Case{
    private String typeNourriture;
    private int quantiteNourriture; //qté de nourriture restante sur la case

    public CaseNourriture(int y, int x,int quantiteNourriture,Carte carteCourante) {
        super(y, x,carteCourante);
        this.quantiteNourriture= quantiteNourriture;
        this.typeNourriture="Nourriture";
    }

    public boolean enleverNourriture() {
        if(quantiteNourriture > 0) {
            quantiteNourriture = quantiteNourriture - 1;
            return true;
        }
        return false;
    }

    public void setQuantiteNourriture(int quantiteNourriture) {
        this.quantiteNourriture = quantiteNourriture;
    }

    //Retourne le type de nourriture sur la case. Par défaut on initialise à "Nourriture"
    public String getType(){
        return typeNourriture;
    }
    public String toString(){
        return "N";
    }
}
