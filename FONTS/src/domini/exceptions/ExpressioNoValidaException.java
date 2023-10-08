package domini.exceptions;

public class ExpressioNoValidaException extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ExpressioNoValidaException(String message) {
        super(message);
    }
}
