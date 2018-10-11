package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.util.*;

public class Seance {
    private final Film film;
    private final Salle salle;
    private final java.util.Date date;
    private final float prix;
    private List<Reservation> reservations;
    private final String id;

    public Seance(Film film, Salle salle, Date date, float prix) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.prix = prix;
        this.reservations = new ArrayList<Reservation>();
        this.id = UUID.fromString(film.getTitre() + film.getVersion() + salle.getNom() + date.toString()).toString();
    }

    public Film getFilm() {
        return film;
    }

    public Salle getSalle() {
        return salle;
    }

    public Date getDate() {
        return date;
    }

    public float getPrix() {
        return prix;
    }

    public void createReservation(String prenom, String nom, String email) throws SeanceCompleteException {
        if (this.salle.getCapacite() >= this.reservations.size())
            throw new SeanceCompleteException();
        Reservation resa = new Reservation(prenom, nom, email);
        this.reservations.add(resa);
        resa.setPaye(true);
    }

    public void cancelReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return Objects.equals(id, seance.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
