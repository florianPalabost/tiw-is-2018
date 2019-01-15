package fr.univlyon1.tiw.tiw1.utils;

public class SeanceCompleteException extends Exception {
    public SeanceCompleteException() {
        super("Cette seance est complete.");
    }
}
