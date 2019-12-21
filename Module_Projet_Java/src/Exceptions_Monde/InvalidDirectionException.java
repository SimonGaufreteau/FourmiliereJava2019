package Exceptions_Monde;

public class InvalidDirectionException extends Exception{
    public InvalidDirectionException(int direction){
        super("Exception InvalidDirectionException raised. Change the direction : "+direction);
    }
}
