package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Utils;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JPAReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONSalleDAO;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

public class CinemaDTO {

    public String nom;
    public Collection<SalleDTO> salles;
    public Collection<FilmDTO> films;
    public Collection<SeanceDTO> seances;

    public Cinema asCinema() throws ParseException, IOException {
        JSONSalleDAO salleDAO = new JSONSalleDAO();
        JSONProgrammationDAO programmationDAO = new JSONProgrammationDAO(salleDAO);
        JPAReservationDAO reservationDAO = new JPAReservationDAO();
        Cinema cinema = new Cinema(nom, salleDAO, programmationDAO, reservationDAO);
        return cinema;
    }
}
