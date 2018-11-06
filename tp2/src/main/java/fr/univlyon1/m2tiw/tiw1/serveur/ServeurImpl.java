package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.DefaultPicoContainer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class ServeurImpl implements Serveur {

    private Cinema cinema;

    public ServeurImpl() throws IOException, ParseException {
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

    public Cinema getCinema() {
        return cinema;
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        return getCinema().processRequest(commande, parameters);
    }
}
