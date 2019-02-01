package fr.univlyon1.tiw.tiw1.reservation.dao;


import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Long> {
    Collection<Reservation> getAllBySeanceId(String seanceId);

    @Override
    <S extends Reservation> S save(S s);

    @Override
    void delete(Reservation reservation);

    // to update a reservation
    //@Query("from Reservation r where r.email = ?1")
    Collection<Reservation> findByEmail(String email);
    Collection<Reservation> findBySeanceId(String id);

    Reservation getById(Long id);
}
