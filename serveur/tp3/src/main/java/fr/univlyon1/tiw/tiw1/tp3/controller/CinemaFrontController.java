package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.metier.beans.Film;
import fr.univlyon1.tiw.tiw1.tp3.service.FrontCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/cinema")
public class CinemaFrontController {

    private static final Logger LOGGER = Logger.getLogger(CinemaFrontController.class.getName());

    @Autowired
    private FrontCinemaService cinemaService;

    @GetMapping
    public String indexCine(Map<String, Object> model) throws IOException {
        // test recup films
        Collection<Film> films = cinemaService.findAllFilms();
//        LOGGER.info("--- FILMS : "+films.toString());
//        LOGGER.info("--- SALLES : "+cinemaService.findAllSalles().toString());
//        LOGGER.info("--- SEANCES : "+cinemaService.findAllSeances().toString());
        Map<String,Object> cine = new HashMap<>();
        cine.put("nom",cinemaService.getNom());
        cine.put("films",cinemaService.findAllFilms());
        cine.put("salles",cinemaService.findAllSalles());
        cine.put("seances",cinemaService.findAllSeances());

        model.put("cinema",cine);
        return "indexCine";
    }




//    @GetMapping("/seances/{id}/addReservation")
//    public String showAddReserv(@PathVariable String id, Map<String, Object> model) throws Exception {
//        Seance seance = cinemaService.findSeanceById(id);
//        model.put("seance",seance);
//        Reservation r = new Reservation();
//        model.put("reservation",r);
//        return "reservations/addReserv";
//    }
//
//    @PostMapping("/seances/saveReserv")
//    public String saveReserv(Reservation reservation, BindingResult result, Model model) throws IOException {
//        if (result.hasErrors()) {
//            return "reservations/addReserv";
//        }
//
//        cinemaService.saveReservation(reservation);
//        return "redirect:showSeance";
//    }
    
}
