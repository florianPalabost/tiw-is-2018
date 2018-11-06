package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class CinemaRessourceSalles extends AbstractCinema {

    public CinemaRessourceSalles(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        super(nom, salleDAO, programmationDAO, reservationDAO);
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        switch (commande) {
            case "get":
                if (parameters.containsKey("salle")) {
                    return getSalle((String) parameters.get("salle"));
                } else {
                    return getSalles();
                }
        }
        return null;
    }
}
