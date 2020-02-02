import exceptions_monde.InvalidDirectionException;
import exceptions_monde.InvalidFileFormatException;
import exceptions_monde.InvalidMapSizeException;
import exceptions_monde.InvalidNbCaseDiffException;
import vue.Jeu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFileFormatException, InvalidDirectionException, InvalidNbCaseDiffException, InvalidMapSizeException, CloneNotSupportedException {
        Jeu jeu = new Jeu();
        jeu.jeu();

    }
}
