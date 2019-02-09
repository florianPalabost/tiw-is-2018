package fr.univlyon1.tiw.tiw1.reservation.metier;

import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.ReservationNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.SeanceNotFoundException;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class ReservationComponent {

    @Autowired
    private ReservationRepository reservationDAO;

    @Autowired
    private ProgrammationDAO programmationDAO;

    @Transactional
    public Reservation reserver(String prenom, String nom, String email, String seanceId)
            throws SeanceCompleteException, SeanceNotFoundException {
        Seance seance = programmationDAO.getSeanceById(seanceId);
        if (seance == null) {
            throw new SeanceNotFoundException("La séance " + seanceId + " n'existe pas.");
        } else {
            Reservation reservation = new Reservation(prenom,nom,prenom);
           //  Reservation reservation = seance.createReservation(prenom, nom, email);
            RestTemplate restTemplate = new RestTemplate();
        String ressUrl = "http://reservations:8091/cinema/seances/"+reservation.getSeanceId();
       restTemplate.postForEntity(ressUrl, reservation, Reservation.class);
            return reservation;
        }
    }

    @Transactional
    public void annulerReservation(Long id) throws ReservationNotFoundException {
        fr.univlyon1.tiw.tiw1.reservation.metier.Reservation reservation = reservationDAO.getById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("La réservation " + id + " est inconnue");
        } else {
            // Seance s = programmationDAO.getSeanceById(reservation.getSeanceId());
            // s.cancelReservation(reservation);
            RestTemplate restTemplate = new RestTemplate();
        String ressUrl = "http://reservations:8091//reservations/"+ reservation.getId();
        restTemplate.delete(ressUrl);
        }
    }

}
