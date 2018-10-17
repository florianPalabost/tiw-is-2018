package fr.univlyon1.m2tiw.tiw1.metier;

import java.text.SimpleDateFormat;

public class Utils {
    public static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    private static final String SEP = " - ";

    public static final String titreFromFilm(String film) {
        return film.substring(0, film.lastIndexOf(SEP));
    }

    public static final String versionFromFilm(String film) {
        return film.substring(film.lastIndexOf(SEP) + SEP.length());
    }
}
