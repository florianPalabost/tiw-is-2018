package fr.univlyon1.tiw.tiw1.reservation.services;

import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.ReservationNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.SeanceNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ServiceReservationComponent {
    @Autowired
    private ReservationRepository reservationDAO;
    @Autowired
    private ProgrammationDAO programmationDAO;
//    @Autowired
//    private CinemaRessourceReservations cinemaRessourceReservations;

    private static final Logger LOGGER = Logger.getLogger(ServiceReservationComponent.class.getName());

    @Transactional
    public Reservation reserver(String prenom, String nom, String email, String seanceId)
            throws SeanceCompleteException, SeanceNotFoundException, IOException {
        Seance seance = programmationDAO.getSeanceById(seanceId);
        if (seance == null) {
            throw new SeanceNotFoundException("La séance " + seanceId + " n'existe pas.");
        } else {
            HashMap<String, Object> parametres = new HashMap<String, Object>();
            // parametres.put("ReservationDto", new ReservationDto(seanceId, prenom, nom, email));
            // return Long.parseLong((String) cinemaRessourceReservations.process("creerReservation", parametres));
            return null;
        }
    }

    @Transactional
    public void annulerReservation(long id) throws ReservationNotFoundException, IOException, SeanceCompleteException {
        LOGGER.info("appel annulerReservation::::"+id);
        Optional<Reservation> reservation = reservationDAO.findById(id);
        LOGGER.info("RESERVATION DAO trouvé:::::"+reservation.toString());
        if (!reservation.isPresent()) {
            throw new ReservationNotFoundException("La réservation " + id + " est inconnue");
        } else {
            reservationDAO.delete(reservation.get());
            // HashMap<String, Object> parametres = new HashMap<String, Object>();
            // parametres.put("reservationId", id);
            // cinemaRessourceReservations.process("annulerReservation", parametres);
        }
    }
}
