package fr.univlyon1.tiw.tiw1.reservation.controller;


import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.reservation.services.ReservationServiceREST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost","http://127.0.0.1:4200","http://127.0.0.1","http://127.0.0.1:8080"})
@RestController
public class RestControllerReservation {
//    // TODO Verify that works with the new architecture
    @Autowired
    ReservationServiceREST reservationService;
//
        private static final Logger LOGGER = Logger.getLogger(RestControllerReservation.class.getName());
//    @RequestMapping("/seances")
//    public Collection<Seance> getSeances() {
//        return reservationService.getSeances();
//    }
//
    // @RolesAllowed("ADMIN")
    @GetMapping(path="/reservations",produces= MediaType.APPLICATION_JSON_VALUE)
    public Collection<Reservation> retrieveAllReservations() {
        LOGGER.info("reservations requetes cors");
        return reservationService.findAllReservations();
    }
    /**
     *
     * @param email
     * @return list of reservations of the user
     * @throws IOException
     */
    @GetMapping(path="/cinema/users/{email}/reservations",produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Collection<Reservation> retrieveReservationsOfUserByMail(@PathVariable String email) throws IOException {
        // Normalement verification de l'utilisateur mais on passe outre
        LOGGER.info("pong user");
        // On cherche les reservations de l'utilisateur correspondant à cet email
        return reservationService.findReservationsOfUserByMail(email);
    }

    @PostMapping(value="/cinema/films/{keyFilm}/seances/{keyS}",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> recordReservation(@RequestBody Reservation reservation, @PathVariable String keyFilm,
                                                         @PathVariable String keyS, Model model)
            throws Exception {
        reservationService.recordReservation(reservation);
        /* RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/cinema/films";
        LOGGER.info("pong dbt CORS res -> Seance");
        ResponseEntity<Object> response = restTemplate.getForEntity(url + "/"+keyFilm+"/seances/"+keyS, Object.class);
        LOGGER.info("CORS::::"+response.toString()); */


        /*
        Film f = cinemaService.findFilmByKey(keyFilm);
        if (f != null) {
            // check if the seance exist
            Seance s = cinemaService.findSeanceById(keyS);
            if (s != null){
                cinemaService.saveReservation(reservation);
            }
        }
        */
        // return (ResponseEntity<Reservation>) response.getBody();
        return new ResponseEntity<Reservation>(HttpStatus.OK);
    }

    @PostMapping(value="/cinema/seances/{keyS}",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> recordReservation1(@RequestBody Reservation reservation, @PathVariable String keyS, Model model)
            throws Exception {
        reservationService.recordReservation(reservation);
        /* RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/cinema/films";
        LOGGER.info("pong dbt CORS res -> Seance");
        ResponseEntity<Object> response = restTemplate.getForEntity(url + "/"+keyFilm+"/seances/"+keyS, Object.class);
        LOGGER.info("CORS::::"+response.toString()); */


        /*
        Film f = cinemaService.findFilmByKey(keyFilm);
        if (f != null) {
            // check if the seance exist
            Seance s = cinemaService.findSeanceById(keyS);
            if (s != null){
                cinemaService.saveReservation(reservation);
            }
        }
        */
        // return (ResponseEntity<Reservation>) response.getBody();
        return new ResponseEntity<Reservation>(HttpStatus.OK);
    }


    @DeleteMapping("/reservations/{idR}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long idR, Model model) {
        reservationService.deleteReservation(idR);
        return new ResponseEntity<Reservation>(HttpStatus.OK);
    }

    @GetMapping(path="/seances/{keyS}/reservations",produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Collection<Reservation> retrieveReservationsBySeanceId(@PathVariable String keyS) throws IOException {
        // Normalement verification de l'utilisateur mais on passe outre
        // On cherche les reservations de l'utilisateur correspondant à cet email
       // return reservationService.findReservationsBySeanceId(keyS);
        return null;
    }

}
