package domini.exceptions;

public class NoExisteixException extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public NoExisteixException(String message) {
        super(message);
    }
}
