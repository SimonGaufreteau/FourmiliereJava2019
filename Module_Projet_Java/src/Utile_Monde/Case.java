package Utile_Monde;

public class Case {
    private int x;
    private int y;

    public Case(int y, int x){
        this.x=x;
        this.y=y;
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
