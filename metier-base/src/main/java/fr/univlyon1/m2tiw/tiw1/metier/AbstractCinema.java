package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.Annuaire;
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

import static fr.univlyon1.m2tiw.tiw1.metier.Constants.*;

public abstract class AbstractCinema implements Cinema {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCinema.class);

//    private final String nom;
//    private ReservationDAO reservationDAO;
//    private ProgrammationDAO programmationDAO;
//    private SalleDAO salleDAO;
    private Annuaire annuaire;

    public AbstractCinema(Annuaire annuaire) throws IOException, ParseException {
        this.annuaire = annuaire;
//        this.nom = nom;
//        this.reservationDAO = reservationDAO;
//        this.salleDAO = salleDAO;
//        this.programmationDAO = programmationDAO;
    }

    public String getNom() {
        return (String) annuaire.get(NOM_CINEMA);
    }

    ReservationDAO getReservationDAO() {
        return (ReservationDAO) annuaire.get(RESERVATION_DAO);
    }

    ProgrammationDAO getProgrammationDAO() {
        return (ProgrammationDAO) annuaire.get(PROGRAMMATION_DAO);
    }

    SalleDAO getSalleDAO() {
        return (SalleDAO) annuaire.get(SALLE_DAO);
    }

    public int getNbSeances() {
        return getProgrammationDAO().getNbSeance();
    }

    protected Collection<Salle> getSalles() throws IOException {
        return getSalleDAO().getSalles();
    }

    protected Salle getSalle(String salle) throws IOException {
        return getSalleDAO().getSalle(salle);
    }

    @Override
    public void start() {
        LOG.info("Composant {} démarré. Objet d'accès aux données : {}", getClass().getSimpleName(), getProgrammationDAO());
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
