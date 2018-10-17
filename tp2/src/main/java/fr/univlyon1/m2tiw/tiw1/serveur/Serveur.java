package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.dao.CinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONCinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONSalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

public class Serveur {

    private Cinema cinema;

    public Serveur() throws IOException, ParseException {
        CinemaDAO cinemaDAO = new JSONCinemaDAO();
        String nom = cinemaDAO.getNomCinema();
        SalleDAO salleDAO = new JSONSalleDAO();
        Collection<Salle> salles = salleDAO.loadSalles();
        cinema = new Cinema(nom, salles);
    }

}
