package fr.univlyon1.m2tiw.tiw1.spring.metier;

public class SeanceNotFoundException extends Exception {
    public SeanceNotFoundException() {
    }

    public SeanceNotFoundException(String message) {
        super(message);
    }

    public SeanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeanceNotFoundException(Throwable cause) {
        super(cause);
    }

    public SeanceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
