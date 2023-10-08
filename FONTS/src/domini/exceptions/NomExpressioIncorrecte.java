package domini.exceptions;

public class NomExpressioIncorrecte extends Exception{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public NomExpressioIncorrecte(String message) {
        super(message);
    }
}
