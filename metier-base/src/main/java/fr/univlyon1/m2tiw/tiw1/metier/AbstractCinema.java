package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public abstract class AbstractCinema implements Cinema {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCinema.class);

    private final String nom;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;
    private SalleDAO salleDAO;

    public AbstractCinema(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        this.nom = nom;
        this.reservationDAO = reservationDAO;
        this.salleDAO = salleDAO;
        this.programmationDAO = programmationDAO;
    }

    public String getNom() {
        return nom;
    }

    ReservationDAO getReservationDAO() {
        return reservationDAO;
    }

    ProgrammationDAO getProgrammationDAO() {
        return programmationDAO;
    }

    SalleDAO getSalleDAO() {
        return salleDAO;
    }

    public int getNbSeances() {
        return programmationDAO.getNbSeance();
    }

    protected Collection<Salle> getSalles() throws IOException {
        return salleDAO.getSalles();
    }

    protected Salle getSalle(String salle) throws IOException {
        return salleDAO.getSalle(salle);
    }

    @Override
    public void start() {
        LOG.info("Composant Cinema démarré. Objet d'accès aux données : {}", programmationDAO);
    }

    @Override
    public void stop() {
        LOG.info("Composant Cinema arrêté.");
    }

    /*
    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        switch (commande) {
            case "addFilm":
                return addFilm((FilmDTO) parameters.get("film"));
            case "createSeance":
                return createSeance((SeanceDTO) parameters.get("seance"));
            case "reserver":
                return reserver((ReservationDTO) parameters.get("reservation"));
            case "annulerReservation": {
                annulerReservation((String) parameters.get("id"));
                return true;
            }
            case "removeSeance": {
                removeSeance((String) parameters.get("id"));
                return true;
            }
            case "removeFilm": {
                removeFilm((String) parameters.get("id"));
                return true;
            }
        }
        return null;
    }
    */
}
