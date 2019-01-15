package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.metier.beans.Film;
import fr.univlyon1.tiw.tiw1.metier.beans.Reservation;
import fr.univlyon1.tiw.tiw1.metier.beans.Salle;
import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.tiw.tiw1.tp3.service.FrontCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

// @RestController pour rest req
@Controller
@RequestMapping("/backend")
public class CinemaBackController {
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
    public @ResponseBody Seance retrieveSeance(@PathVariable String id) throws IOException {
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


    // ----- DELETE REQUESTS -----
    @DeleteMapping("/cinema/films/{key}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String key) throws IOException {
        return cinemaService.deleteFilm(key);
    }

    // -----FILMS-----
    @GetMapping("/cinema/films/gestion")
    public String listFilms(Map<String,Object> model) throws IOException {
        Collection<Film> films = cinemaService.findAllFilms();
        model.put("films",films);
        return "films/listFilms";
    }

    // -----  ADD Film -----
    @GetMapping("/cinema/films/addfilm")
    public String showAddFilmForm (Map<String, Object> model,Film film) {
        model.put("film",film);
        return "films/addFilm";
    }

    @PostMapping("/cinema/films/createfilm")
    public String createFilm(@Valid Film film, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "films/addFilm";
        }

        cinemaService.saveFilm(film);
        // model.addAttribute("f", userRepository.findAll());
        return "index";
    }

    // ----- UPDATE FILM

    @GetMapping("/cinema/films/{key}/edit")
    public String showEditFilmForm(@PathVariable String key, Map<String,Object> model) throws IOException {
        Film film = cinemaService.findFilmByKey(key);
        model.put("film",film);
        return "films/editFilm";
    }

    @PutMapping("/cinema/films/{key}/update")
    public String updateFilm(@PathVariable String key,@Valid Film film,BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "film/editFilm";
        }
        LOGGER.info("film update : "+film);
        // cinemaService.saveFilm(film);
        return "redirect:listFilms";
    }

    // ----- ADD Seance -----
    @GetMapping("/cinema/seances/addseance")
    public String showAddSeanceForm (Map<String, Object> model) throws IOException {
        Collection<Film> films = cinemaService.findAllFilms();
        Collection<Salle> salles = cinemaService.findAllSalles();

        // pas tres opti car on envoie tous les films,salles ...
        model.put("seance", new SeanceDTO());
        model.put("salles", salles);
        model.put("films",films);

        return "seances/addSeance";
    }

    @PostMapping("/cinema/seances/createseance")
    public String createSeance(@ModelAttribute("seance") Seance seance, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "seances/addSeance";
        }
        LOGGER.info("new seance"+seance);
        LOGGER.info("model seance: "+ model.toString());
        // cinemaService.saveSeance(seance);
        // model.addAttribute("f", userRepository.findAll());
        return "index";
    }

    @PostMapping("/cinema/films/{keyFilm}/seances/")
    public  ResponseEntity<Void> recordReservation(@Valid Reservation reservation, @PathVariable String keyFilm, BindingResult result, Model model) throws IOException {
        //Film f = cinemaService.findFilmByKey(keyFilm);

        //check if the seance exist, for this reservation
        Seance s = cinemaService.findSeanceById(reservation.getSeanceId());

        cinemaService.saveReservation(reservation);
        return null;
    }

}
