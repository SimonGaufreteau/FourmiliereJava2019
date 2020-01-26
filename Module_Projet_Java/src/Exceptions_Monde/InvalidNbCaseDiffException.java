package Exceptions_Monde;

public class InvalidNbCaseDiffException extends Exception {
    public InvalidNbCaseDiffException(){
        super("Exception InvalidNbCaseDiffException raised. (nb case fourmiliere + nb case nourriture > nb cases");
    }
}
