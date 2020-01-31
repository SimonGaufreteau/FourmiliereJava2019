package util_monde;

public class Case implements Cloneable{
    private Carte carteCourante;
    private int x;
    private int y;

    // y : position
    public Case(int x, int y, Carte carteCourante){
        this.x=x;
        this.y=y;
        this.carteCourante=carteCourante;
    }

    public Case(Carte carteCourante){
        this.carteCourante = carteCourante;
        y = (int) (Math.random() * (carteCourante.getHauteur())); // on prend un nombre au hasard en tre0 et la hauteur -1)
        x =(int) (Math.random() * (carteCourante.getLargeur())); // meme chose avec la largeur
    }

    public Case(){
        this.x = 1;
        this.y = 1;
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