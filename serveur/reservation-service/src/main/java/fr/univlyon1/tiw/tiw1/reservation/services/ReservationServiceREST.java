package fr.univlyon1.tiw.tiw1.reservation.services;

import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.logging.Logger;

@Service
public class ReservationServiceREST {

    private static final Logger LOGGER = Logger.getLogger(ReservationServiceREST.class.getName());

   @Autowired
    private ReservationRepository reservationRepository;
//    public Collection<Seance> getSeances() {
//        return programmationDAO.getSeances();
//    }

    public Collection<Reservation> findReservationsOfUserByMail(String email) {
        return reservationRepository.findByEmail(email);
    }

    // Appel rest a seance grace a restTemplate
    public Collection<Seance> getSeances() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://admin:8080/cinema/seances";
        Collection<Seance> r = null;
        Collection<Seance> result = restTemplate.getForObject(url, r.getClass());
        return result;
    }

    public Seance getSeance(String sId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://admin:8080/cinema/seances/"+sId;
        Seance result = restTemplate.getForObject(url, Seance.class);
        if(result == null) {
            LOGGER.warning("ERROR : La seance n°"+sId+" n'existe pas");
        }
        return result;
    }

    public Collection<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public void recordReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id){
        Reservation reservation = reservationRepository.getById(id);
        if(reservation != null) {
            reservationRepository.delete(reservation);
        }
    }

    public String postUpdateReservationPaye(String id, boolean paye) {

        String url = "http://reservations:8091/reservations/update/paye";
        LOGGER.info("Update de la paye de la reservation"+id+".");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("id", id);
        map.add("paye", Boolean.toString(paye));

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);

        return response.toString();
    }

    public void updateReservationById(String idR,boolean isPaye) {
        Reservation reservation = reservationRepository.getById(Long.valueOf(idR));
        if(reservation != null) {
            reservationRepository.updateReservation(Long.valueOf(idR), isPaye);
        }
    }

}
