package fr.univlyon1.m2tiw.tiw1.utils;

public class SeanceCompleteException extends Exception {
    public SeanceCompleteException() {
        super("Cette seance est complete.");
    }
}
