package fr.univlyon1.tiw.tiw1.tp3.service;

import fr.univlyon1.tiw.tiw1.metier.beans.*;
import fr.univlyon1.tiw.tiw1.metier.dao.CinemaDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONCinemaDAO;
import fr.univlyon1.tiw.tiw1.metier.jsondto.SeanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Transactional
@Service
public class FrontCinemaService implements ICinemaService{
    private String nom;
    @Autowired
    private CinemaRessourceSalles cinemaRessourceSalles;
    @Autowired
    private CinemaRessourceFilms cinemaRessourceFilms;
    @Autowired
    private CinemaRessourceSeances cinemaRessourceSeances;

    private CinemaDAO cinemaDAO = new JSONCinemaDAO();

    private static final Logger LOGGER = Logger.getLogger(FrontCinemaService.class.getName());

    public FrontCinemaService() throws IOException {
        this.nom = cinemaDAO.getNomCinema();
    }

    public void setCinemaRessourceFilms(CinemaRessourceFilms cinemaRessourceFilms) {
        this.cinemaRessourceFilms = cinemaRessourceFilms;
    }

    public Collection<Salle> findAllSalles() throws IOException {
        return (Collection<Salle>) cinemaRessourceSalles.process("getSalles", new HashMap<>());
    }

    public void setCinemaRessourceSalles(CinemaRessourceSalles cinemaRessourceSalles) {
        this.cinemaRessourceSalles = cinemaRessourceSalles;
    }


    public Collection<Seance> findAllSeances() throws IOException {
        return (Collection<Seance>) cinemaRessourceSeances.process("getSeances", new HashMap<>());
    }

    public void setCinemaRessourceSeances(CinemaRessourceSeances cinemaRessourceSeances) {
        this.cinemaRessourceSeances = cinemaRessourceSeances;
    }

    @Override
    public Collection<Film> findAllFilms() throws IOException {
        return (Collection<Film>) cinemaRessourceFilms.process("getFilms", new HashMap<>());
    }

    public Film findFilmByKey(String key) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key",key);
        return (Film) cinemaRessourceFilms.process("getFilmByKey",params );
    }

    public Film findFilmByTitre(String titre) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("titre",titre);
        return (Film) cinemaRessourceFilms.process("getFilm",params );
    }

    public Salle findSalleByName(String name) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name",name);
        return (Salle) cinemaRessourceSalles.process("getSalleByName",params );
    }

    /**
     *
     * @param id
     * @return the seance associate to this id
     * @throws IOException
     */
    public Seance findSeanceById(String id) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("id",id);
        Seance seance = (Seance) cinemaRessourceSeances.process("getSeanceById",params );
        if(seance == null)
            throw new Exception("La seance "+ seance.getId()+ "n'existe pas !");
        else
            return seance;
    }

    /**
     *
     * @param key
     * @return list of seances of a film identify by his key
     * @throws IOException
     */
    public Collection<Seance> findSeancesOfFilmByKey(String key) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key",key);
        // liste des seances pouvant etre nulle pour un film, on ne gere pas l'exception
        return (Collection<Seance>)cinemaRessourceSeances.process("getSeancesOfFilm",params);
    }

    /**
     *
     * @param email
     * @return list of reservations of the user referenced by mail
     */

//    public Collection<Reservation> findReservationsOfUserByMail(String email) throws IOException {
//         Map<String,Object> params = new HashMap<>();
//         params.put("email",email);
//         return (Collection<Reservation>) cinemaRessourceSeances.process("getReservationsOfUserByMail", params);
//    }

    public String getNom() {
        return nom;
    }

    /**
     * method to save the film
     * @param film
     * @return
     * @throws IOException
     */
    public ResponseEntity<Void> saveFilm(Film film) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("film",film);
        cinemaRessourceFilms.process("addFilm", params);
        return null;
    }

    public ResponseEntity<Void> deleteFilm(String key) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("key",key);
        cinemaRessourceFilms.process("removeFilm",params);
        return null;
    }

    public ResponseEntity<Void> saveSeance(SeanceDTO seance) throws IOException, ParseException {
        Map<String, Object> params = new HashMap<>();
        // on recupere les objets films et salles
        Film f = cinemaRessourceFilms.getFilmByKey(seance.getFilm());
        LOGGER.info("FILM::::::::"+f.toString());

        params.put("nomSalle",seance.getSalle());
        Salle salle = (Salle) cinemaRessourceSalles.process("getSalle", params);
        LOGGER.info("SALLE:::::::"+salle.toString());

        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(seance.getDate());
        LOGGER.info("DATE:::::::::::"+d);

        params.put("film", f);
        params.put("salle", salle);
        params.put("prix", seance.getPrix());
        params.put("date", d);
        cinemaRessourceSeances.process("createSeance", params);
        return null;
    }
//    public ResponseEntity<Reservation> saveReservation(Reservation reservation) throws IOException {
//        Map<String,Object> params = new HashMap<>();
//        params.put("reservation",reservation);
//
//        cinemaRessourceSeances.process("saveReservation", params);
//        return null;
//    }
//
//    public Collection<Reservation> findReservationsOfSeance(String keyS) throws IOException {
//        Map<String, Object> params = new HashMap<>();
//        //params.put
//        cinemaRessourceSeances.process("saveReservation", params);
//        return null;
//    }

    public ResponseEntity<Seance> updateSeance(SeanceDTO seanceDTO) throws IOException {
        Map<String,Object> params = new HashMap<>();
        params.put("seance", seanceDTO);
        cinemaRessourceSeances.process("updateSeance", params);
        return null;
    }
}
