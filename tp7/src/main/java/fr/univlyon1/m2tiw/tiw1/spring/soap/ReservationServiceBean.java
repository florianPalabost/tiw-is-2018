package fr.univlyon1.m2tiw.tiw1.spring.soap;

import fr.univ_lyon1.tiw.tiw1.cinema.reservation.*;
import fr.univlyon1.m2tiw.tiw1.metier.Reservation;
import fr.univlyon1.m2tiw.tiw1.spring.metier.ReservationComponent;
import fr.univlyon1.m2tiw.tiw1.spring.metier.ReservationNotFoundException;
import fr.univlyon1.m2tiw.tiw1.spring.metier.SeanceNotFoundException;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(
        endpointInterface = "fr.univ_lyon1.tiw.tiw1.cinema.reservation.ReservationService",
        wsdlLocation = "/reservation.wsdl")
@Component
public class ReservationServiceBean implements ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceBean.class);
    @Autowired
    private ReservationComponent reservationComponent;

    @Override
    public String reserver(String prenom, String nom, String email, String seance) throws SeanceInconnue_Exception, SeanceComplete_Exception {
        Reservation reservation = null;
        try {
            reservation = reservationComponent.reserver(prenom, nom, email, seance);
        } catch (SeanceCompleteException e) {
            LOG.warn("Seance complete: " + seance, e);
            throw new SeanceComplete_Exception(e.getMessage());
        } catch (SeanceNotFoundException e) {
            LOG.warn("Seance inconnue: " + seance, e);
            throw new SeanceInconnue_Exception(e.getMessage());
        }
        return String.valueOf(reservation.getId());
    }

    @Override
    public ReservationAnnulee annuler(AnnulerReservation parameters) throws ReservationInconnue_Exception {
        try {
            reservationComponent.annulerReservation(Long.parseLong(parameters.getId()));
        } catch (ReservationNotFoundException e) {
            LOG.warn("Réservation inconnue " + parameters.getId());
            throw new ReservationInconnue_Exception(e.getMessage());
        } catch (NumberFormatException e) {
            LOG.warn("Mauvais format d'identifiant de réservation: '" + parameters.getId() + "'");
            throw new ReservationInconnue_Exception(e.getMessage());
        }
        return new ReservationAnnulee();
    }
}
