package fr.univlyon1.m2tiw.tiw1.metier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Cinema {
    private final String nom;
    private Map<String, Salle> salles;
    private Map<String, Film> films;
    private List<Seance> seances;

    @JsonCreator()
    public Cinema(@JsonProperty("nom") String nom) {
        this.nom = nom;
        this.salles = new HashMap<String, Salle>();
        this.films = new HashMap<String, Film>();
        this.seances = new ArrayList<Seance>();
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

    public void addFilm(Film film) {
        this.films.put(film.getTitre(), film);
    }

    public void removeFilm(Film film) {
        this.films.remove(film);
    }

    public void createSeance(Salle salle, Film film, Date date, float prix) {
        this.seances.add(new Seance(film, salle, date, prix));
    }

    public void removeSeance(Seance seance) {
        seances.remove(seance);
    }

    public int getNbSeances() {
        return seances.size();
    }

    @JsonProperty("salles")
    public Collection<Salle> getSalles() {
        return salles.values();
    }

    public void setSalles(Collection<Salle> nSalles) {
        this.salles.clear();
        for(Salle s : nSalles) {
            addSalle(s);
        }
    }

    @JsonProperty("films")
    public Collection<Film> getFilms() {
        return films.values();
    }

    public void setFilms(Collection<Film> nFilms) {
        this.films.clear();
        for(Film f : nFilms) {
            addFilm(f);
        }
    }

    @JsonProperty("seances")
    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }
}
