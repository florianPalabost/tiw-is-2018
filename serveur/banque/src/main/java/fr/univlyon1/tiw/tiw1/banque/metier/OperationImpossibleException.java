package fr.univlyon1.tiw.tiw1.banque.metier;

public class OperationImpossibleException extends Exception {

    public OperationImpossibleException() {
    }

    public OperationImpossibleException(String message) {
        super(message);
    }

    public OperationImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationImpossibleException(Throwable cause) {
        super(cause);
    }

    public OperationImpossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
