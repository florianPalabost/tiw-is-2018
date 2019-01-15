package fr.univlyon1.tiw.tiw1.tp3.service;

import fr.univlyon1.tiw.tiw1.metier.beans.*;
import fr.univlyon1.tiw.tiw1.metier.dao.CinemaDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONCinemaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    /*@Autowired
    private ReservationDAO reservationDAO;*/

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
    public Seance findSeanceById(String id) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id",id);
        return (Seance) cinemaRessourceSeances.process("getSeanceById",params );
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
        return (Collection<Seance>)cinemaRessourceSeances.process("getSeancesOfFilm",params);
    }

    /**
     *
     * @param email
     * @return list of reservations of the user referenced by mail
     */

    public Collection<Reservation> findReservationsOfUserByMail(String email) {
        // Map<String,Object> params = new HashMap<>();
        // params.put("email",email);
        // return reservationDAO.findByEmail(email);
        return null;
    }

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

    public ResponseEntity<Void> saveSeance(Seance seance) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("seance",seance);
        cinemaRessourceSeances.process("createSeance", params);
        return null;
    }
    public ResponseEntity<Void> saveReservation(Reservation reservation) {
        // reservationDAO.save(reservation);
        return null;
    }
}
