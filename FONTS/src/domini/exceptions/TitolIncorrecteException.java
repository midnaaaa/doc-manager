package domini.exceptions;

public class TitolIncorrecteException extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public TitolIncorrecteException(String message) {
        super(message);
    }
}
