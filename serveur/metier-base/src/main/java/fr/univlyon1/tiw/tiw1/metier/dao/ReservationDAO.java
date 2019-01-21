package fr.univlyon1.tiw.tiw1.metier.dao;

import fr.univlyon1.tiw.tiw1.metier.beans.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
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

    // to update a reservation
    //@Query("from Reservation r where r.email = ?1")
    Collection<Reservation> findByEmail(String email);
    Collection<Reservation> findBySeanceId(String id);
}
