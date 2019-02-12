package fr.univlyon1.tiw.tiw1.reservation.services;

import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.ReservationNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.exceptions.SeanceNotFoundException;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.utils.SeanceCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ServiceReservationComponent {
    @Autowired
    private ReservationRepository reservationDAO;
    @Autowired
    private ProgrammationDAO programmationDAO;

    private static final Logger LOGGER = Logger.getLogger(ServiceReservationComponent.class.getName());

    @Transactional
    public Reservation reserver(String prenom, String nom, String email, String seanceId)
            throws SeanceCompleteException, SeanceNotFoundException, IOException {
        // Seance seance = programmationDAO.getSeanceById(seanceId);

        String url = "http://admin:8080/cinema/seances/"+seanceId;
        RestTemplate restTemplate = new RestTemplate();
        // Normalement ca devrait etre des objets Seances mais bon ...
        Object result = restTemplate.getForObject(url, Object.class);
        if (result == null) {
            throw new SeanceNotFoundException("La séance " + seanceId + " n'existe pas.");
        } else {
            // devrait pas se faire la
            Reservation reservation = new Reservation(prenom, nom, email);
            reservation.setSeanceId(seanceId);

            reservationDAO.save(reservation);
            return reservation;
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
        }
    }
    @Transactional
    public void updateReservationPaye(Long rId, boolean isPaye) {
        LOGGER.info("Le paiement a ete effectuee .... MAJ reservation n°"+rId);
        Reservation reservation = reservationDAO.getById(rId);

        if(reservation != null) {
            LOGGER.info("Reservation n°"+rId+" trouvée : MAJ boolean paye");
            reservation.setPaye(isPaye);
            reservationDAO.save(reservation);
        }
        else {
            LOGGER.warning("Aucune reservation d'identifiant : "+rId);
        }

    }
    @Transactional
    public List<Reservation> retrieveReservationsBySeanceId(String id) {
        Collection<Reservation> reservationCollection = reservationDAO.getAllBySeanceId(id);
        List<Reservation> reservationList = new ArrayList<>(reservationCollection);
        List<Reservation> listReservationsBySeance = new ArrayList<>();
        for(Reservation r : reservationList){
            if(r.getSeanceId().equals(id))
            {
                listReservationsBySeance.add(r);
            }
        }
        LOGGER.info("retrieveReservationBySeanceId::::"+listReservationsBySeance.toString());
        return listReservationsBySeance;
    }

    public Reservation retrieveReservationByRId(Long id) {
        return  reservationDAO.getById(id);
    }

    @Transactional
    public void updateReservation(String email, String nom, String prenom, Long idR, String seanceId) {
        Reservation reservation = reservationDAO.getById(idR);
        if(reservation != null) {
            // test si la seance est existante inutile etant donnée que la reservation existe deja
            // a moins que la mise a jour concerne l id de la seance ...

            reservation.setNom(nom);
            reservation.setPrenom(prenom);
            reservation.setEmail(email);
            reservation.setSeanceId(seanceId);

            reservationDAO.save(reservation);
        }
    }

    @Transactional
    public Collection<Reservation> retrieveReservationsByEmail(String email) {
        return reservationDAO.findByEmail(email);
    }
}
