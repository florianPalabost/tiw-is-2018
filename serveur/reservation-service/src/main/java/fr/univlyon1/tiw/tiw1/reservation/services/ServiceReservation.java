package fr.univlyon1.tiw.tiw1.reservation.services;

import fr.univ_lyon1.tiw.tiw1.cinema.reservation.*;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.ReservationNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.SeanceNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebService;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
@WebService(targetNamespace = ServiceReservation.NAMESPACE,
        endpointInterface = "fr.univ_lyon1.tiw.tiw1.cinema.reservation.ReservationService",
        wsdlLocation = "/reservation.wsdl")
// @HandlerChain(file = "/META-INF/SoapMessageHandler.xml")
@Component
public class ServiceReservation implements ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceReservation.class);
    public static final String NAMESPACE = "http://www.univ-lyon1.fr/tiw/tiw1/cinema/reservation";

    @Autowired
    private ServiceReservationComponent serviceReservationComponent;

    @Override
    public String reserver(String prenom, String nom, String email, String seance) throws SeanceInconnue_Exception, SeanceComplete_Exception {
        Reservation reservation = null;
        try {
            reservation = serviceReservationComponent.reserver(prenom, nom, email, seance);
        } catch (SeanceCompleteException e) {
            LOG.warn("Seance complete: " + seance, e);
            throw new SeanceComplete_Exception(e.getMessage());
        } catch (SeanceNotFoundException e) {
            LOG.warn("Seance inconnue: " + seance, e);
            throw new SeanceInconnue_Exception(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(reservation.getId());
    }

    @Override
    public ReservationAnnulee annuler(AnnulerReservation parameters) throws ReservationInconnue_Exception {
        try {
            serviceReservationComponent.annulerReservation(Long.parseLong(parameters.getId()));
        } catch (ReservationNotFoundException e) {
            LOG.warn("Réservation inconnue " + parameters.getId());
            throw new ReservationInconnue_Exception(e.getMessage());
        } catch (NumberFormatException | IOException | SeanceCompleteException e) {
            LOG.warn("Mauvais format d'identifiant de réservation: '" + parameters.getId() + "'");
            throw new ReservationInconnue_Exception(e.getMessage());
        }
        return new ReservationAnnulee();
    }

    public List<Reservation> retrieveReservationsBySeanceId(String id) {
        return serviceReservationComponent.retrieveReservationsBySeanceId(id);
    }

    public Reservation retrieveReservationByRId(Long id) {
        return serviceReservationComponent.retrieveReservationByRId(id);
    }

    public void updateReservation(String email, String nom, String prenom, Long reservationid, String seanceId) throws SeanceComplete_Exception {
        serviceReservationComponent.updateReservation(email,nom,prenom,reservationid,seanceId);
    }

    public Collection<Reservation> retrieveReservationsByEmail(String email) {
        return serviceReservationComponent.retrieveReservationsByEmail(email);
    }

    public void updateReservationPaye(Long reservationId, boolean isPaye){
        serviceReservationComponent.updateReservationPaye(reservationId,isPaye);
    }

}
