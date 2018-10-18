package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Cinema implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(Cinema.class);

    private final String nom;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;
    private SalleDAO salleDAO;

    public Cinema(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
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

    private void addFilm(Film film) throws IOException {
        programmationDAO.save(film);
    }

    private FilmDTO addFilm(FilmDTO filmDTO) throws IOException {
        Film film = filmDTO.asFilm();
        addFilm(film);
        return FilmDTO.fromFilm(film);
    }

    private void removeFilm(Film film) throws IOException {
        programmationDAO.delete(film);
    }

    private Seance createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        programmationDAO.save(seance);
        seance.setReservationDAO(reservationDAO);
        return seance;
    }

    private void removeSeance(Seance seance) throws IOException {
        LOG.debug("Deleting seance: {}", seance);
        programmationDAO.delete(seance);
    }

    public int getNbSeances() {
        return programmationDAO.getNbSeance();
    }

    private Collection<Salle> getSalles() throws IOException {
        return salleDAO.getSalles();
    }

    private Collection<Film> getFilms() {
        return programmationDAO.getFilms();
    }

    private Salle getSalle(String salle) throws IOException {
        return salleDAO.getSalle(salle);
    }

    private Film getFilm(String film) {
        String titre = Utils.titreFromFilm(film);
        String version = Utils.versionFromFilm(film);
        return programmationDAO.getFilmByTitreVersion(titre, version);
    }

    private String createSeance(SeanceDTO seanceDTO) throws ParseException, IOException {
        Film f = getFilm(seanceDTO.film);
        Salle s = getSalle(seanceDTO.salle);
        Date d = Utils.DATE_PARSER.parse(seanceDTO.date);
        float p = seanceDTO.prix;
        Seance seance = createSeance(s, f, d, p);
        return seance.getId();
    }

    private void removeFilm(String film) throws IOException {
        Film f = getFilm(film);
        removeFilm(f);
    }

    private void removeSeance(String id) throws ParseException, IOException {
        removeSeance(programmationDAO.getSeanceById(id));
    }

    private String reserver(ReservationDTO reservationDTO) throws SeanceCompleteException {
        Seance s = programmationDAO.getSeanceById(reservationDTO.seance);
        Reservation r = s.createReservation(reservationDTO.prenom, reservationDTO.nom, reservationDTO.email);
        return r.getId().toString();
    }

    private void annulerReservation(String reservationId) {
        reservationDAO.delete(reservationDAO.getById(Long.parseLong(reservationId)));
    }

    @Override
    public void start() {
        LOG.info("Composant Cinema démarré. Objet d'accès aux données : {}", programmationDAO);
    }

    @Override
    public void stop() {
        LOG.info("Composant Cinema arrêté.");
    }

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
}
