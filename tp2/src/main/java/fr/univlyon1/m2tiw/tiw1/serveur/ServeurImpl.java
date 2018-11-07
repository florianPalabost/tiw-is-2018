package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.Annuaire;
import fr.univlyon1.m2tiw.tiw1.metier.*;
import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.Startable;
import org.picocontainer.behaviors.Caching;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static fr.univlyon1.m2tiw.tiw1.metier.Constants.*;

public class ServeurImpl implements Serveur {

    public static final String SERVEUR = "serveur";

    private Annuaire annuaire;

    public ServeurImpl(Annuaire annuaire) throws IOException, ParseException {
        this.annuaire = annuaire;
        annuaire.add(SERVEUR, this);
        DefaultPicoContainer pico = new DefaultPicoContainer(new Caching());
        CinemaDAO cinemaDAO = new JSONCinemaDAO();
        String nom = cinemaDAO.getNomCinema();
        pico.addComponent(annuaire);
        pico.addComponent(nom);
        pico.addComponent(JSONSalleDAO.class);
        pico.addComponent(JPAReservationDAO.class);
        pico.addComponent(JSONProgrammationDAO.class);
        pico.addComponent(CinemaRessourceSeances.class);
        pico.addComponent(CinemaRessourceFilms.class);
        pico.addComponent(CinemaRessourceSalles.class);
        pico.addComponent(CinemaRessourceReservations.class);
        annuaire.add(NOM_CINEMA, nom);
        annuaire.add(SALLE_DAO, pico.getComponent(SalleDAO.class));
        annuaire.add(RESERVATION_DAO, pico.getComponent(ReservationDAO.class));
        annuaire.add(PROGRAMMATION_DAO, pico.getComponent(ProgrammationDAO.class));
        annuaire.add("application/seance", pico.getComponent(CinemaRessourceSeances.class));
        annuaire.add("application/film", pico.getComponent(CinemaRessourceFilms.class));
        annuaire.add("application/salle", pico.getComponent(CinemaRessourceSalles.class));
        annuaire.add("application/reservation", pico.getComponent(CinemaRessourceReservations.class));
        ((Startable) annuaire.get("application/seance")).start();
        ((Startable) annuaire.get("application/film")).start();
        ((Startable) annuaire.get("application/salle")).start();
        ((Startable) annuaire.get("application/reservation")).start();
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        int sep = commande.indexOf("/");
        String obj = commande.substring(0, sep);
        String com = commande.substring(sep + 1);
        Cinema cinema = (Cinema) annuaire.get("application/"+obj);
        return cinema.processRequest(com, parameters);
    }
}
