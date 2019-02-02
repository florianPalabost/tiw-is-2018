package fr.univlyon1.tiw.tiw1.metier.beans;

import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONProgrammationDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * CinemaRessourceSeances .
 *
 * @author alper
 */
// gestion des seances
@Component
public class CinemaRessourceSeances extends ACinemaRessource implements ICinema {
    //createSeance(), removeSeance(), getNbSeance()
    private static final Logger LOGGER = Logger.getLogger(CinemaRessourceSeances.class.getName());
    private List<Seance> seances;
    private ProgrammationDAO progDAO = new JSONProgrammationDAO();

//    @Autowired
//    private ReservationDAO reservationDAO;

    /**
     *
     * CinemaRessourceSeances .
     *
     *
     */
    public CinemaRessourceSeances() throws IOException, ParseException {
        this.seances = progDAO.getSeances();
    }

    /**
     *
     * setSeances .
     *
     * @param seances
     *
     */
    public void setSeances(List<Seance> seances) {
        this.seances = seances;
        for (Seance s : seances) {
//            s.setReservationDAO(reservationDAO);
        }
    }

    private void createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        LOGGER.info("SEANCE CREEEE:::::::"+seance.toString());
        this.seances.add(seance);
        progDAO.save(seance);
//        seance.setReservationDAO(reservationDAO);
    }

    public void removeSeance(Seance seance) throws IOException {
        seances.remove(seance);
        progDAO.delete(seance);
    }

    private int getNbSeances() {
        return seances.size();
    }

    public Collection<Seance> getSeances() {
        return seances;
    }

    public Collection<Seance> getSeancesOfFilm(String key) {
        Collection<Seance> seancesOfFilm = new ArrayList<>();
        for (Seance s : seances) {
            if(s.getFilm().getKey().equals(key)){
                LOGGER.info(s.toString());
                // call the reservation service and retrieve the reservations
                // GET /seances/{idSeance}/reservations
//                s.setReservations((List<Object>) reservationDAO.findBySeanceId(s.getId()));
                LOGGER.info("RESERVATIONS:::::::"+s.getReservations().toString());
                seancesOfFilm.add(s);
            }
        }
        if(seancesOfFilm.isEmpty()){
            return null;
        }
        LOGGER.info("SEANCES OF FILM :"+seancesOfFilm.toString());
        return seancesOfFilm;
    }

//    /**
//     * call the dao to save the reservation
//     * @param reservation
//     */
//    private void saveReservation(Reservation reservation){
//        reservationDAO.save(reservation);
//    }
//
//    /**
//     * call the dao to delete the reservation
//     * @param reservation
//     */
//    private void deleteReservation(Reservation reservation) {
//        reservationDAO.delete(reservation);
//    }
//
//    /**
//     * retrieve all the reservations of a user, found by email
//     * @param email
//     */

//    private Collection<Reservation> getReservationsOfUserByMail(String email) {
//        Collection<Reservation> reservationsUser = new ArrayList<>();
//        for (Seance s : seances) {
//           for(Reservation r : s.getReservations()){
//               if(r.getEmail().equals(email)){
//                   reservationsUser.add(r);
//               }
//           }
//        }
//        return reservationsUser;
//    }
    /**
     *
     * process .
     *
     * @param commande .
     * @param parametres .
     *
     * @return Object .
     *
     * @throws IOException Exception IO
     */
    public Object process(String commande, Map<String,Object> parametres) throws IOException {
        switch (commande) {
            case "createSeance":
                // A TESTER 
                createSeance((Salle)parametres.get("salle"),(Film) parametres.get("film"),
                        (Date)parametres.get("date"),(Float) parametres.get("prix"));
                return null;
                
            case "removeSeance":
                removeSeance((Seance) parametres.get("seance"));
                return null;
                
            case "getNbSeances":
                return getNbSeances();

            case "setSeances":
                setSeances((List<Seance>) parametres.get("seances"));
                return null;

            case "getSeances":
                return getSeances();

            case "getSeancesOfFilm":
                return getSeancesOfFilm((String) parametres.get("key"));

            case "getSeanceById":
                return getSeanceById((String) parametres.get("id"));
//            case "saveReservation":
//                saveReservation((Reservation) parametres.get("reservation"));
//                return null;
//            case "cancelReservation":
//                deleteReservation((Reservation) parametres.get("reservation"));
//                return null;
//            case "getReservationsOfUserByMail":
//                return getReservationsOfUserByMail((String) parametres.get("email"));
            default:
                return null;
        }
    }

    private Seance getSeanceById(String id) {
       for (Seance s : seances) {
           if(s.getId().equals(id)){
               return s;
           }
       }
       return null;
    }

    @Override
    public void start() {
        LOGGER.info("Component " + this.getClass() + " started");
    }
    
    @Override
    public void stop() {
        LOGGER.info("Component " + this.getClass() + " stopped");
    }
}
