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
import java.util.List;
import java.util.stream.Collectors;

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

    public void addFilm(Film film) throws IOException {
        programmationDAO.save(film);
    }

    public Film addFilm(FilmDTO filmDTO) throws IOException {
        Film film = filmDTO.asFilm();
        addFilm(film);
        return film;
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

    public String createSeance(SeanceDTO seanceDTO) throws ParseException, IOException {
        Film f = getFilm(seanceDTO.film);
        Salle s = getSalle(seanceDTO.salle);
        Date d = Utils.DATE_PARSER.parse(seanceDTO.date);
        float p = seanceDTO.prix;
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

    public String reserver(ReservationDTO reservationDTO) throws SeanceCompleteException {
        Seance s = programmationDAO.getSeanceById(reservationDTO.seance);
        Reservation r = s.createReservation(reservationDTO.prenom, reservationDTO.nom, reservationDTO.email);
        return r.getId().toString();
    }

    public void annulerReservation(String reservationId) {
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
}
