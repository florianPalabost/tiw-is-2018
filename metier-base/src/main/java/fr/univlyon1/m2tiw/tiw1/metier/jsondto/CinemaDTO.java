package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class CinemaDTO {
    public static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    public String nom;
    public Collection<SalleDTO> salles;
    public Collection<FilmDTO> films;
    public Collection<SeanceDTO> seances;

    public Cinema asCinema() throws ParseException {
        Cinema cinema = new Cinema(nom);
        for (FilmDTO f : films) {
            cinema.addFilm(f.asFilm());
        }
        for (SalleDTO s : salles) {
            cinema.addSalle(s.asSalle());
        }
        for (SeanceDTO s : seances) {
            Date d = DATE_PARSER.parse(s.date);
            cinema.createSeance(cinema.getSalle(s.salle), cinema.getFilm(s.film), d, s.prix);
        }
        return cinema;
    }
}
