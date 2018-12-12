package fr.univlyon1.m2tiw.tiw1.spring.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Reservation;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class ReservationSpringDAO implements ReservationDAO {
    @Autowired
    private ReservationRepository repository;

    @Override
    public void save(Reservation reservation) {
        repository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        repository.delete(reservation);
    }

    @Override
    public Collection<Reservation> getBySeance(String seanceId) {
        return repository.getAllBySeanceIdAnd(seanceId);
    }

    @Override
    public Reservation getById(long id) {
        Optional<Reservation> res = repository.findById(id);
        return res.isPresent() ? res.get() : null;
    }
}
