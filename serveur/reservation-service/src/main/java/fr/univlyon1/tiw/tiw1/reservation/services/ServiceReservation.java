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

import javax.jws.WebService;
import java.io.IOException;

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
}
