package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.metier.dao.*;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Cinema {

    private static final Logger LOG = LoggerFactory.getLogger(Cinema.class);

    private final String nom;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;
    private SalleDAO salleDAO;

    public Cinema(String nom, SalleDAO salleDAO) throws IOException, ParseException {
        this.nom = nom;
        this.reservationDAO = new JPAReservationDAO();
        this.salleDAO = salleDAO;
        this.programmationDAO = new JSONProgrammationDAO(this.salleDAO);
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

    public void addFilm(Film film) throws IOException {
        programmationDAO.save(film);
    }

    public void removeFilm(Film film) throws IOException {
        programmationDAO.delete(film);
    }

    public Seance createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        programmationDAO.save(seance);
        seance.setReservationDAO(reservationDAO);
        return seance;
    }

    public void removeSeance(Seance seance) throws IOException {
        LOG.debug("Deleting seance: {}", seance);
        programmationDAO.delete(seance);
    }

    public int getNbSeances() {
        return programmationDAO.getNbSeance();
    }

    public Collection<Salle> getSalles() throws IOException {
        return salleDAO.getSalles();
    }

    public Collection<Film> getFilms() {
        return programmationDAO.getFilms();
    }

    public void setFilms(Collection<Film> nFilms) throws IOException {
        programmationDAO.clearFilms();
        for (Film f : nFilms) {
            addFilm(f);
        }
    }

    public List<Seance> getSeances() {
        return programmationDAO.getSeances().stream().collect(Collectors.toList());
    }

    public void setSeances(List<Seance> seances) throws IOException {
        programmationDAO.clearSeance();
        for (Seance s : seances) {
            s.setReservationDAO(reservationDAO);
        }
    }

    public Salle getSalle(String salle) throws IOException {
        return salleDAO.getSalle(salle);
    }

    public Film getFilm(String film) {
        String titre = Utils.titreFromFilm(film);
        String version = Utils.versionFromFilm(film);
        return programmationDAO.getFilmByTitreVersion(titre, version);
    }

    public String createSeance(String film, String salle, String date, String prix) throws ParseException, IOException {
        Film f = getFilm(film);
        Salle s = getSalle(salle);
        Date d = Utils.DATE_PARSER.parse(date);
        float p = Float.parseFloat(prix);
        Seance seance = createSeance(s, f, d, p);
        return seance.getId();
    }

    public void removeFilm(String film) throws IOException {
        Film f = getFilm(film);
        removeFilm(f);
    }

    public void removeSeance(String id) throws ParseException, IOException {
        removeSeance(programmationDAO.getSeanceById(id));
    }

    public String reserver(String seance, String prenom, String nom, String email) throws SeanceCompleteException {
        Seance s = programmationDAO.getSeanceById(seance);
        Reservation r = s.createReservation(prenom, nom, email);
        return r.getId().toString();
    }

    public void annulerReservation(String reservationId) {
        reservationDAO.delete(reservationDAO.getById(Long.parseLong(reservationId)));
    }
}
