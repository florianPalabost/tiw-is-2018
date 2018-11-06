package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class CinemaRessourceSeances extends AbstractCinema {

    private static final Logger LOG = LoggerFactory.getLogger(CinemaRessourceSeances.class);

    public CinemaRessourceSeances(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        super(nom, salleDAO, programmationDAO, reservationDAO);
    }

    private Seance createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        getProgrammationDAO().save(seance);
        seance.setReservationDAO(getReservationDAO());
        return seance;
    }

    private void removeSeance(Seance seance) throws IOException {
        LOG.debug("Deleting seance: {}", seance);
        getProgrammationDAO().delete(seance);
    }


    private Film getFilm(String film) {
        String titre = Utils.titreFromFilm(film);
        String version = Utils.versionFromFilm(film);
        return getProgrammationDAO().getFilmByTitreVersion(titre, version);
    }

    private String createSeance(SeanceDTO seanceDTO) throws ParseException, IOException {
        Film f = getFilm(seanceDTO.film);
        Salle s = getSalle(seanceDTO.salle);
        Date d = Utils.DATE_PARSER.parse(seanceDTO.date);
        float p = seanceDTO.prix;
        Seance seance = createSeance(s, f, d, p);
        return seance.getId();
    }

    private void removeSeance(String id) throws ParseException, IOException {
        removeSeance(getProgrammationDAO().getSeanceById(id));
    }


    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        switch (commande) {
            case "create":
                return createSeance((SeanceDTO) parameters.get("seance"));
            case "remove": {
                removeSeance((String) parameters.get("id"));
                return true;
            }
        }
        return null;
    }
}
