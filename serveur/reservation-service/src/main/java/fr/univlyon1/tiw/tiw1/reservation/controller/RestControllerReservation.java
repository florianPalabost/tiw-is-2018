package fr.univlyon1.tiw.tiw1.reservation.controller;


import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;


@RestController
public class RestControllerReservation {
    // TODO Verify that works with the new architecture
    @Autowired
    ReservationService reservationService;

    @RequestMapping("/seances")
    public Collection<Seance> getSeances() {
        return reservationService.getSeances();
    }

    /**
     *
     * @param email
     * @return list of reservations of the user
     * @throws IOException
     */
    @GetMapping(path="/users/{email}/reservations",produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Collection<Reservation> retrieveReservationsOfUserByMail(@PathVariable String email) throws IOException {
        // Normalement verification de l'utilisateur mais on passe outre

        // On cherche les reservations de l'utilisateur correspondant à cet email
        return reservationService.findReservationsOfUserByMail(email);
    }

    @PostMapping(value="/cinema/films/{keyFilm}/seances/{keyS}",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> recordReservation(@RequestBody Reservation reservation, @PathVariable String keyFilm,
                                                         @PathVariable String keyS, Model model)
            throws Exception {
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
        return null;
    }

}
