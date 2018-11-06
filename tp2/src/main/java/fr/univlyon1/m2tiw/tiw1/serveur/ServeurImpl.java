package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.CinemaRessourceFilms;
import fr.univlyon1.m2tiw.tiw1.metier.CinemaRessourceReservations;
import fr.univlyon1.m2tiw.tiw1.metier.CinemaRessourceSalles;
import fr.univlyon1.m2tiw.tiw1.metier.CinemaRessourceSeances;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class ServeurImpl implements Serveur {

    private DefaultPicoContainer pico;

    public ServeurImpl() throws IOException, ParseException {
        pico = new DefaultPicoContainer(new Caching());
        CinemaDAO cinemaDAO = new JSONCinemaDAO();
        String nom = cinemaDAO.getNomCinema();
        pico.addComponent(nom);
        pico.addComponent(JSONSalleDAO.class);
        pico.addComponent(JPAReservationDAO.class);
        pico.addComponent(JSONProgrammationDAO.class);
        pico.addComponent(CinemaRessourceSeances.class);
        pico.addComponent(CinemaRessourceFilms.class);
        pico.addComponent(CinemaRessourceSalles.class);
        pico.addComponent(CinemaRessourceReservations.class);
        pico.getComponent(CinemaRessourceSeances.class).start();
        pico.getComponent(CinemaRessourceFilms.class).start();
        pico.getComponent(CinemaRessourceSalles.class).start();
        pico.getComponent(CinemaRessourceReservations.class).start();
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        int sep = commande.indexOf("/");
        String obj = commande.substring(0, sep);
        String com = commande.substring(sep + 1);
        switch (obj) {
            case "seance":
                return pico.getComponent(CinemaRessourceSeances.class).processRequest(com, parameters);
            case "reservation":
                return pico.getComponent(CinemaRessourceReservations.class).processRequest(com, parameters);
            case "film":
                return pico.getComponent(CinemaRessourceFilms.class).processRequest(com, parameters);
            case "salle":
                return pico.getComponent(CinemaRessourceSalles.class).processRequest(com, parameters);
            default:
                return null;
        }
    }
}
