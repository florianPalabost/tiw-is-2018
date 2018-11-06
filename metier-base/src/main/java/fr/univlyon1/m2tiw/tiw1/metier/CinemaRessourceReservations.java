package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class CinemaRessourceReservations extends AbstractCinema {

    public CinemaRessourceReservations(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        super(nom, salleDAO, programmationDAO, reservationDAO);
    }

    private String reserver(ReservationDTO reservationDTO) throws SeanceCompleteException {
        Seance s = getProgrammationDAO().getSeanceById(reservationDTO.seance);
        Reservation r = s.createReservation(reservationDTO.prenom, reservationDTO.nom, reservationDTO.email);
        return r.getId().toString();
    }

    private void annulerReservation(String reservationId) {
        getReservationDAO().delete(getReservationDAO().getById(Long.parseLong(reservationId)));
    }

    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        switch (commande) {
            case "reserver":
                return reserver((ReservationDTO) parameters.get("reservation"));
            case "annuler": {
                annulerReservation((String) parameters.get("id"));
                return true;
            }
        }
        return null;
    }
}
