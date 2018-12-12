package fr.univlyon1.m2tiw.tiw1.spring.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Reservation;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
    Collection<Reservation> getAllBySeanceId(String seanceId);
}
