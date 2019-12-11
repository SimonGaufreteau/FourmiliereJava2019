package Exceptions_Monde;

public class InvalidMapSizeException extends Exception{
    public InvalidMapSizeException(int taille_attendue,int taille_recue){
        super("Exception InvalidMapSizeException raised. Expected = " + taille_attendue + " , Received = " + taille_recue);
    }
}
