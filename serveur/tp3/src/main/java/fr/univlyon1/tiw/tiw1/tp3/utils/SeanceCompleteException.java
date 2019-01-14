package fr.univlyon1.tiw.tiw1.tp3.utils;

public class SeanceCompleteException extends Exception {
    public SeanceCompleteException() {
        super("Cette seance est complete.");
    }
}
