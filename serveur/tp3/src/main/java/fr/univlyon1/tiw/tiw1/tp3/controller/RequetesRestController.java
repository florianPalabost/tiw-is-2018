package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.metier.beans.Film;
import fr.univlyon1.tiw.tiw1.metier.beans.Reservation;
import fr.univlyon1.tiw.tiw1.metier.beans.Salle;
import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.tp3.service.FrontCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController //pour rest req
//@RequestMapping("/backend")
public class RequetesRestController {
    // CRUD Films et Seances (forms,...)
    @Autowired
    private FrontCinemaService cinemaService;

    private static final Logger LOGGER = Logger.getLogger(CinemaBackController.class.getName());

    // ----------- GET METHODS -----------------
    @GetMapping(path="/cinema/films", produces = { "application/json" })
    public @ResponseBody Collection<Film> retrieveAllFilms() throws IOException {
        return cinemaService.findAllFilms();
    }

    @GetMapping(path="/cinema/films/{key}",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Film retrieveFilm(@PathVariable String key) throws IOException {
        Film f = cinemaService.findFilmByKey(key);
        if(f != null) {
            return f;
        }
        return null;
    }

    @GetMapping(path="/cinema/salles",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Collection<Salle> retrieveAllSalles() throws IOException {
        return cinemaService.findAllSalles();
    }

    @GetMapping(path="/cinema/salles/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Salle retrieveSalle(@PathVariable String name) throws IOException {
        Salle s = cinemaService.findSalleByName(name);
        if(s != null) {
            return s;
        }
        return null;
    }

    @GetMapping(path="/cinema/seances",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Collection<Seance> retrieveAllSeances() throws IOException {
        return cinemaService.findAllSeances();
    }

    @GetMapping(path="/cinema/seances/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Seance retrieveSeance(@PathVariable String id) throws Exception {
        Seance s = cinemaService.findSeanceById(id);
        if(s != null) {
            return s;
        }
        return null;
    }

    // ----- POST REQUESTS -----
    @PostMapping(value="/cinema/films",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> persistFilm(@RequestBody Film film) throws IOException {
        LOGGER.info(film.toString());
        return cinemaService.saveFilm(film);
    }

    @GetMapping(path="/cinema/films/{key}/seances",produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Collection<Seance> retrieveSeancesOfFilm(@PathVariable String key) throws IOException {
        Film f = cinemaService.findFilmByKey(key);
        if(f != null) {
            return cinemaService.findSeancesOfFilmByKey(key);
        }
        return null;
    }


}
