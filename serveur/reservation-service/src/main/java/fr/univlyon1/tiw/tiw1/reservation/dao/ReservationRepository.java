package fr.univlyon1.tiw.tiw1.reservation.dao;


import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

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

    Collection<Reservation> findAll();

    @Override
    Optional<Reservation> findById(Long aLong);

    Reservation getById(Long id);

    @Modifying
    @Query(value = "UPDATE reservation r set paye = :isPaye where r.id = :idR",
            nativeQuery = true)
    void updateReservation(@Param("idR") long idR , @Param("isPaye") boolean isPaye);

    // Iterable<Reservation> getAllByEmail(String email);
}
