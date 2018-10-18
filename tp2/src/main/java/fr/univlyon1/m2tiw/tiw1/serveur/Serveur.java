package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
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

    public String addFilm(String titre, String version, String fiche) throws IOException {
        Film film = new Film(titre, version, fiche);
        cinema.addFilm(film);
        return film.getTitreVersion();
    }

    public void removeFilm(String film) throws IOException {
        cinema.removeFilm(film);
    }

    public String createSeance(String film, String salle, String date, String prix) throws IOException, ParseException {
        return cinema.createSeance(film, salle, date, prix);
    }

    public void removeSeance(String id) throws IOException, ParseException {
        cinema.removeSeance(id);
    }

    public String reserver(String seance, String prenom, String nom, String email) throws SeanceCompleteException {
        return cinema.reserver(seance, nom, prenom, email);
    }

    public void annulerReservation(String reservationId) {
        cinema.annulerReservation(reservationId);
    }
}
