package domini.exceptions;

public class ArxiuNoCorrecteException extends Exception{
		@Override
		public String getMessage() {
				return super.getMessage();
		}

		public ArxiuNoCorrecteException(String message) {
				super(message);
		}
}
