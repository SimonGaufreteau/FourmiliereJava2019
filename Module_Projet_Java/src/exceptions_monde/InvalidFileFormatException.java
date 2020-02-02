package exceptions_monde;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String nomFichier) {
        super("Exception InvalidFileFormatException raised. Check the file +\"" + nomFichier + "\" format");
    }
}
