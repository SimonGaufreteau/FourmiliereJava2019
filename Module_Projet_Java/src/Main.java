public class Main {

    public static void main (String[] args){
        try {
            Simulation S = new Simulation();
            S.jeu();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
