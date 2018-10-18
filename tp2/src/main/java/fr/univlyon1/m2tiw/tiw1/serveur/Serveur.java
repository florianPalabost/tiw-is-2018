package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.DefaultPicoContainer;

import java.io.IOException;
import java.text.ParseException;

public class Serveur {

    private Cinema cinema;

    public Serveur() throws IOException, ParseException {
        DefaultPicoContainer pico = new DefaultPicoContainer();
        CinemaDAO cinemaDAO = new JSONCinemaDAO();
        String nom = cinemaDAO.getNomCinema();
        pico.addComponent(nom);
        pico.addComponent(JSONSalleDAO.class);
        pico.addComponent(JSONProgrammationDAO.class);
        pico.addComponent(JPAReservationDAO.class);
        pico.addComponent(Cinema.class);
        cinema = pico.getComponent(Cinema.class);
        getCinema().start();
    }

    public String addFilm(FilmDTO filmDTO) throws IOException {
        Film film = getCinema().addFilm(filmDTO);
        return film.getTitreVersion();
    }

    public void removeFilm(String film) throws IOException {
        getCinema().removeFilm(film);
    }

    public String createSeance(SeanceDTO seanceDTO) throws IOException, ParseException {
        return getCinema().createSeance(seanceDTO);
    }

    public void removeSeance(String id) throws IOException, ParseException {
        getCinema().removeSeance(id);
    }

    public String reserver(ReservationDTO reservationDTO) throws SeanceCompleteException {
        return getCinema().reserver(reservationDTO);
    }

    public void annulerReservation(String reservationId) {
        getCinema().annulerReservation(reservationId);
    }

    public Cinema getCinema() {
        return cinema;
    }
}
