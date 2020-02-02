package exceptions_monde;

public class OutOfMapException extends Exception {
    public OutOfMapException(int x, int y) {
        super("Exception OutOfMapException raised. Coordinates out of Map. X = " + x + " , Y = " + y);
    }
}
