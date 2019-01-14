package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seance {
    private final Film film;
    private final Salle salle;
    private final java.util.Date date;
    private final float prix;
    private List<Reservation> reservations;

    public Seance(Film film, Salle salle, Date date, float prix) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.prix = prix;
        this.reservations = new ArrayList<Reservation>();
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

    public void createReservation(String prenom, String nom, String email) throws SeanceCompleteException{
        if(this.salle.getCapacite() >= this.reservations.size())
            throw new SeanceCompleteException();
        Reservation resa = new Reservation(prenom, nom, email);
        this.reservations.add(resa);
        resa.setPaye(true);
    }

    public void cancelReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }
}
