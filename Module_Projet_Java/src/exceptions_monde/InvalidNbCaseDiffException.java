package exceptions_monde;

public class InvalidNbCaseDiffException extends Exception {
    public InvalidNbCaseDiffException(){
        super("Exception InvalidNbCaseDiffException raised. (nb case fourmiliere + nb case nourriture > nb cases");
    }
}
