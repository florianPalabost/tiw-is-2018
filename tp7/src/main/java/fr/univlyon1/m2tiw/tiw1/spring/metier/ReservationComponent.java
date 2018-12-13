package fr.univlyon1.m2tiw.tiw1.spring.metier;

import fr.univlyon1.m2tiw.tiw1.metier.Reservation;
import fr.univlyon1.m2tiw.tiw1.metier.Seance;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationComponent {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private ProgrammationDAO programmationDAO;

    @Transactional
    public Reservation reserver(String prenom, String nom, String email, String seanceId)
            throws SeanceCompleteException, SeanceNotFoundException {
        Seance seance = programmationDAO.getSeanceById(seanceId);
        if (seance == null) {
            throw new SeanceNotFoundException("La séance " + seanceId + " n'existe pas.");
        } else {
            Reservation reservation = seance.createReservation(prenom, nom, email);
            return reservation;
        }
    }

    @Transactional
    public void annulerReservation(Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationDAO.getById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("La réservation " + id + " est inconnue");
        } else {
            Seance s = programmationDAO.getSeanceById(reservation.getSeanceId());
            s.cancelReservation(reservation);
        }
    }

}
