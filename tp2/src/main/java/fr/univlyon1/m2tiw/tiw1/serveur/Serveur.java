package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

public class Serveur {

    private Cinema cinema;

    public Serveur() throws IOException, ParseException {
        CinemaDAO cinemaDAO = new JSONCinemaDAO();
        String nom = cinemaDAO.getNomCinema();
        SalleDAO salleDAO = new JSONSalleDAO();
        ProgrammationDAO programmationDAO = new JSONProgrammationDAO(salleDAO);
        ReservationDAO reservationDAO = new JPAReservationDAO();
        cinema = new Cinema(nom, salleDAO, programmationDAO, reservationDAO);
    }

    public String addFilm(FilmDTO filmDTO) throws IOException {
        Film film = cinema.addFilm(filmDTO);
        return film.getTitreVersion();
    }

    public void removeFilm(String film) throws IOException {
        cinema.removeFilm(film);
    }

    public String createSeance(SeanceDTO seanceDTO) throws IOException, ParseException {
        return cinema.createSeance(seanceDTO);
    }

    public void removeSeance(String id) throws IOException, ParseException {
        cinema.removeSeance(id);
    }

    public String reserver(ReservationDTO reservationDTO) throws SeanceCompleteException {
        return cinema.reserver(reservationDTO);
    }

    public void annulerReservation(String reservationId) {
        cinema.annulerReservation(reservationId);
    }
}
