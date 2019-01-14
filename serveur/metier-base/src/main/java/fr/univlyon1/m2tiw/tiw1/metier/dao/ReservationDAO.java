package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.beans.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ReservationDAO extends CrudRepository<Reservation, Long> {
    public static final String CTX_PERSISTENCE = "/persistence";
    public static final String CONTEXT = "reservation";
    //void save(Reservation reservation);

    // void delete(Reservation reservation);

    // Collection<Reservation> getBySeance(String seanceId);
//
//    Collection<Reservation> findSeanceById(String seanceId);
//
    Reservation findById(long id);
//
//    Reservation getById(long id);

    @Override
    <S extends Reservation> S save(S s);

    @Override
    void delete(Reservation reservation);

    @Query("from Reservation r where r.email = ?1")
    Collection<Reservation> findByEmail(String email);
}
