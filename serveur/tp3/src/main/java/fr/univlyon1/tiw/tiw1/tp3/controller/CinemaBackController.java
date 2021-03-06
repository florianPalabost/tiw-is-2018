package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.metier.beans.Film;
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
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
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

    @GetMapping(path="/cinema/seances/{id}")
    public String showSeance(@PathVariable String id,Map<String, Object> model) throws Exception {
        Seance seance = cinemaService.findSeanceById(id);
        if(seance != null) {
//            LOGGER.info("SEANCE : "+seance);
            model.put("seance",seance);
            return "seances/show";
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
        Film f = cinemaService.findFilmByKey(key);
        if (f!= null){
            //cinemaService.updateFilm(key,f);
        }
        return "redirect:listFilms";
    }

    @GetMapping(path="/cinema/films/{key}/seances")
    public String showSeancesOfFilm(@PathVariable String key, Model model) throws IOException {
        Film f = cinemaService.findFilmByKey(key);
        if(f != null) {
            Collection<Seance> listSeances = cinemaService.findSeancesOfFilmByKey(key);
            model.addAttribute("seances", listSeances);
            model.addAttribute("nomFilm", f.getKey());
        }
        return "seances/listSeances";
    }

    @GetMapping(path="/cinema/films/{key}/seances/addSeance")
    public String showAddSeanceForm(@PathVariable String key, Model model) throws IOException {
        Film f = cinemaService.findFilmByKey(key);
        Collection<Salle> salles = cinemaService.findAllSalles();
        if(f != null) {
            model.addAttribute("seance", new SeanceDTO());
            model.addAttribute("film", f);
            model.addAttribute("salles", salles);
            return "seances/addSeance";
        }
        return "redirect:showSeancesOfFilm";
    }

    @PostMapping(path="/cinema/films/{key}/seances/createseance")
    public String createSeance(@PathVariable String key, @Valid SeanceDTO seanceDTO, BindingResult result, Model model) throws IOException, ParseException {
        if (result.hasErrors()) {
            return "seances/addSeance";
        }
        Film f = cinemaService.findFilmByKey(key);
        seanceDTO.setFilm(f.getKey());
        LOGGER.info("RESULT::::::::"+result.toString());
        if(f.getTitre() != "") {
            LOGGER.info("new seance"+seanceDTO);
            LOGGER.info("model seance: "+ model.toString());
            cinemaService.saveSeance(seanceDTO);
        }
        return "redirect:/backend/cinema/films/"+f.getKey()+"/seances";
    }

    @GetMapping(path="/cinema/films/{keyF}/seances/{keyS}")
    public String showSeance(@PathVariable String keyF,@PathVariable String keyS,Model model) throws Exception {
        Film f = cinemaService.findFilmByKey(keyF);
        if(!f.getTitre().equals("")) {
            Seance s = cinemaService.findSeanceById(keyS);
            if (s != null) {
                model.addAttribute("seance", s);
            }
        }
        return "seances/show";
    }
    @GetMapping(path="/cinema/films/{keyFilm}/seances/{keyS}/edit")
    public String showEditSeanceForm(@PathVariable String keyFilm, @PathVariable String keyS, Model model) throws Exception {
        Film f = cinemaService.findFilmByKey(keyFilm);
        Collection<Salle> salles = cinemaService.findAllSalles();
        if(!f.getTitre().equals("")) {
            Seance s = cinemaService.findSeanceById(keyS);
            if (s != null) {
                model.addAttribute("film", f);
                model.addAttribute("salles", salles);
                model.addAttribute("seance", s);
            }
        }
        return "seances/edit";
    }
    @PostMapping(path="/cinema/films/{key}/seances/updateseance")
    public String updateSeance(@PathVariable String key, @Valid SeanceDTO seanceDTO, BindingResult result, Model model) throws IOException, ParseException {
        if (result.hasErrors()) {
            return "seances/edit";
        }
        Film f = cinemaService.findFilmByKey(key);
        seanceDTO.setFilm(f.getKey());
        LOGGER.info("RESULT MAJ::::::::"+result.toString());
        if(f.getTitre() != "") {
            LOGGER.info("new seance"+seanceDTO);
            LOGGER.info("model seance: "+ model.toString());
            cinemaService.updateSeance(seanceDTO);
        }
        return "redirect:/backend/cinema/films/"+f.getKey()+"/seances";
    }

    @GetMapping("/cinema/seances/{id}/reservations")
    public String listReservations(@PathVariable String id, Map<String, Object> model) throws Exception {
        // Seance s = cinemaService.findSeanceById(id);
        RestTemplate restTemplate = new RestTemplate();
        String ressUrl = "http://reservations:8091/reservations";
        Collection<Object> result = restTemplate.getForObject(ressUrl, Collection.class);
        LOGGER.info("TEST RestTEMPLATE::::::::"+result.toString());
//        if(s != null) {
//            model.put("reservations",s.getReservations());
//            return "listReservations";
//        }
        return null;
    }
}
