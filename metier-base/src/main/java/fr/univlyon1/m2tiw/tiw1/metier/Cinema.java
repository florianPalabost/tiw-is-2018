package fr.univlyon1.m2tiw.tiw1.metier;


import fr.univlyon1.m2tiw.tiw1.metier.dao.JPAReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Cinema {
    private final String nom;
    private Map<String, Salle> salles;
    private Map<String, Film> films;
    private List<Seance> seances;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;

    public Cinema(String nom, Collection<Salle> salles) throws IOException, ParseException {
        this.nom = nom;
        this.salles = new HashMap<String, Salle>();
        this.films = new HashMap<String, Film>();
        this.seances = new ArrayList<Seance>();
        reservationDAO = new JPAReservationDAO();
        setSalles(salles);
        programmationDAO = new JSONProgrammationDAO(this.getSalles());
        // Attention, les salles doivent être cohérentes avec l'information contenue dans le fichier JSON des seances
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
        this.films.put(film.getTitre() + " - " + film.getVersion(), film);
        programmationDAO.save(film);
    }

    public void removeFilm(Film film) {
        this.films.remove(film);
    }

    public void createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        this.seances.add(seance);
        programmationDAO.save(seance);
        seance.setReservationDAO(reservationDAO);
    }

    public void removeSeance(Seance seance) throws IOException {
        seances.remove(seance);
        programmationDAO.delete(seance);
    }

    public int getNbSeances() {
        return seances.size();
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
        return films.values();
    }

    public void setFilms(Collection<Film> nFilms) throws IOException {
        this.films.clear();
        for (Film f : nFilms) {
            addFilm(f);
        }
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
        for (Seance s : seances) {
            s.setReservationDAO(reservationDAO);
        }
    }

    public Salle getSalle(String salle) {
        return salles.get(salle);
    }

    public Film getFilm(String film) {
        return films.get(film);
    }
}
