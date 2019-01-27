package fr.univlyon1.tiw.tiw1.reservation.metier;


import fr.univlyon1.tiw.tiw1.metier.beans.Reservation;
import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;
import fr.univlyon1.tiw.tiw1.reservation.metier.SeanceNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.metier.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationComponent {

    @Autowired
    private ReservationRepository reservationRepository;

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
        Reservation reservation = reservationRepository.getById(id);
        if (reservation == null) {
            throw new fr.univlyon1.tiw.tiw1.reservation.metier.ReservationNotFoundException("La réservation " + id + " est inconnue");
        } else {
            Seance s = programmationDAO.getSeanceById(reservation.getSeanceId());
            s.cancelReservation(reservation);
        }
    }

}
