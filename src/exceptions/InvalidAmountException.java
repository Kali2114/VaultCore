package exceptions;

public class InvalidAmountException extends RuntimeError {
    public InvalidAmountException(String message){
        super(message);
    }
}