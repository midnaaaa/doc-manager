package domini.exceptions;

public class JaExisteixException extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public JaExisteixException(String message) {
        super(message);
    }
}
