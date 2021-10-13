package twisk.exceptions;

public abstract class TwiskException extends Exception {

    public TwiskException(String errorMessage){
        super(errorMessage);
    }
}
