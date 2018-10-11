package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Reservation;

import java.util.Collection;

public interface ReservationDAO {
    void save(Reservation reservation);

    void delete(Reservation reservation);

    Collection<Reservation> getBySeance(String seanceId);

    Reservation getById(long id);
}
