package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class CinemaDTO {

    public String nom;
    public Collection<SalleDTO> salles;
    public Collection<FilmDTO> films;
    public Collection<SeanceDTO> seances;

    public Cinema asCinema() throws ParseException, IOException {
        Collection<Salle> sallesCinema = salles.stream().map(SalleDTO::asSalle).collect(Collectors.toList());
        Cinema cinema = new Cinema(nom, sallesCinema);
        for (FilmDTO f : films) {
            cinema.addFilm(f.asFilm());
        }
        for (SeanceDTO s : seances) {
            Date d = Utils.DATE_PARSER.parse(s.date);
            cinema.createSeance(cinema.getSalle(s.salle), cinema.getFilm(s.film), d, s.prix);
        }
        return cinema;
    }
}
