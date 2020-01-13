import Utile_Fourmi.*;
import Exceptions_Monde.InvalidDirectionException;
import Utile_Monde.*;
import Interfaces_Global.*;


public class Simulation {
    private Monde mon_monde;

    public Simulation(int hauteur, int largeur,int nbFourmis){
        mon_monde=new Monde(hauteur,largeur,nbFourmis);

    }
}
