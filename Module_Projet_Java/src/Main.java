import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import util_fourmi.Fourmi;
import util_monde.Carte;
import util_monde.Serialization;
import vue.Jeu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFileFormatException, InvalidDirectionException, InvalidNbCaseDiffException, InvalidMapSizeException, CloneNotSupportedException, ClassNotFoundException {
        /*Jeu jeu = new Jeu();
        jeu.jeu();*/
        Fourmi f = new Fourmi();
        System.out.println("f1 :");
        System.out.println(f.toString());
        Serialization.serialiseProgrammeGenetique(f.getIntelligence(),"fourmi_test.txt");
        Fourmi f2 = new Fourmi(Serialization.deserialiseProgrammeGenetique("fourmi_test.txt"));
        System.out.println("f2 :");
        System.out.println(f2.toString());
        Carte c = new Carte("Sauvegardes\\Save_Carte_Test.carte");
        System.out.println(c.toString());
    }
}
