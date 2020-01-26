public class Main {

    public static void main (String[] args){
        try {
            //Simulation S = new Simulation();
            //S.jeu();
            Simulation sim = new Simulation();
            sim.lancerSimulation(14,2,50,3,8);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
