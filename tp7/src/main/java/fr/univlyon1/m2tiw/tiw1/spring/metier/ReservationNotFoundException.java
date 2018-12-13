package fr.univlyon1.m2tiw.tiw1.spring.metier;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException() {
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReservationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}