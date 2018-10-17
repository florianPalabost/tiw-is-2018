package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.metier.dao.JPAReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Cinema {
    private final String nom;
    private Map<String, Salle> salles;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;

    public Cinema(String nom, Collection<Salle> salles) throws IOException, ParseException {
        this.nom = nom;
        this.salles = new HashMap<String, Salle>();
        reservationDAO = new JPAReservationDAO();
        setSalles(salles);
        programmationDAO = new JSONProgrammationDAO(this.getSalles());
    }

    public String getNom() {
        return nom;
    }

    public void addSalle(Salle salle) {
        this.salles.put(salle.getNom(), salle);
    }

    public void removeSalle(Salle salle) {
        this.salles.remove(salle);
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
        programmationDAO.delete(seance);
    }

    public int getNbSeances() {
        return programmationDAO.getNbSeance();
    }

    public Collection<Salle> getSalles() {
        return salles.values();
    }

    public void setSalles(Collection<Salle> nSalles) {
        this.salles.clear();
        for (Salle s : nSalles) {
            addSalle(s);
        }
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

    public Salle getSalle(String salle) {
        return salles.get(salle);
    }

    public Film getFilm(String film) {
        String titre = Utils.titreFromFilm(film);
        String version = Utils.titreFromFilm(film);
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
