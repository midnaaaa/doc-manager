package domini.exceptions;

public class AutorIncorrecteException extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public AutorIncorrecteException(String message) {
        super(message);
    }
}
