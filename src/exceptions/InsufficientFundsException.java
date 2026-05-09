package exceptions;

public class InsufficientFundsException extends RuntimeError {
    public InsufficientFundsException(String message){
        super(message);
    }
}