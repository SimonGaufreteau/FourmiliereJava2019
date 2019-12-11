package Utile_Monde;

public class CaseNourriture extends Case{
    private String typeNourriture;
    public CaseNourriture(int y, int x) {
        super(y, x);
        this.typeNourriture="Nourriture";
    }

    //Retourne le type de nourriture sur la case. Par défaut on initialise à "Nourriture"
    public String getType(){
        return typeNourriture;
    }
    public String toString(){
        return "N";
    }
}
