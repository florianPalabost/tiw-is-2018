package fr.univlyon1.tiw.tiw1.metier.beans;

import fr.univlyon1.tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;

import java.util.*;

public class Seance {
    private final Film film;
    private final Salle salle;
    private final Date date;
    private final float prix;
    private List<Reservation> reservations;
    private final String id;
    private ReservationDAO reservationDAO;

    /**
     *
     * Constructeur de Reservation .
     *
     * @param film .
     *
     * @param salle .
     *
     * @param date .
     *
     * @param prix .
     *
     */
    public Seance(Film film, Salle salle, Date date, float prix) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.prix = prix;
        this.reservations = new ArrayList<Reservation>();
        this.id = UUID.nameUUIDFromBytes(
                (film.getTitre()
                        + film.getVersion()
                        + salle.getNom()
                        + date.toString())
                        .getBytes())
                .toString();
    }

    public Seance() {
        this.film = null;
        this.salle = null;
        this.date = new Date();
        this.prix = 0;
        this.id = "";
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     *
     * Pour creer la reservation.
     *
     * @param prenom .
     *
     * @param nom .
     *
     * @param email .
     *
     * @throws SeanceCompleteException Exception de la seance complete.
     *
     */
    public Reservation createReservation(String prenom, String nom,
                                         String email) throws SeanceCompleteException {
        if (this.salle.getCapacite() <= this.reservations.size()) {
            throw new SeanceCompleteException();
        }
        Reservation resa = new Reservation(prenom, nom, email);
        this.reservations.add(resa);
        resa.setSeanceId(getId());
        resa.setPaye(true);
        if (reservationDAO != null) {
            reservationDAO.save(resa);
        }
        return resa;
    }

    /**
     *
     * Annuler une reservation .
     *
     * @param reservation .
     *
     */
    public void cancelReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        if (reservationDAO != null) {
            reservationDAO.delete(reservation);
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" + "film:" + film
                   + ", salle:" + salle
                   + ", date:" + date
                   + ", prix:" + prix
                   + ", reservations:" + reservations
                   + ", id:" + id + '}';
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seance seance = (Seance) o;
        return Objects.equals(id, seance.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
