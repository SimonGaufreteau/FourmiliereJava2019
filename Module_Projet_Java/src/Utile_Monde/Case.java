package Utile_Monde;

public class Case {
    private Carte carteCourante;
    private int x;
    private int y;

    public Case(int y, int x, Carte carteCourante){
        this.x=x;
        this.y=y;
        this.carteCourante=carteCourante;
    }

    /*La case a besoin de connaitre la carte sur
    laquelle elle se trouve pour calculer la distance utile à la fourmi*/
    public Carte getCarteCourante() {
        return carteCourante;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return "C";
    }
}
