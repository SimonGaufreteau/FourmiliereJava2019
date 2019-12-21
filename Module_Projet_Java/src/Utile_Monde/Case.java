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
