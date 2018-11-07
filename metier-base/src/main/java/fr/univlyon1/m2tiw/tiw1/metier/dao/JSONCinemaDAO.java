package fr.univlyon1.m2tiw.tiw1.metier.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.CinemaRessourceSeances;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class JSONCinemaDAO implements CinemaDAO {

    static final URL RESOURCE = JSONCinemaDAO.class.getResource("/sample-data/mon-cinema.json");

    private final ObjectMapper mapper = new ObjectMapper();

//    @Override
//    public Cinema load() throws IOException {
//        try {
//            String nom = getNomCinema();
//            JSONSalleDAO salleDAO = new JSONSalleDAO();
//            JPAReservationDAO reservationDAO = new JPAReservationDAO();
//            JSONProgrammationDAO programmationDAO = new JSONProgrammationDAO(nom, salleDAO, reservationDAO);
//            Cinema cinema = new CinemaRessourceSeances(nom, salleDAO, programmationDAO, reservationDAO); // juste pour les tests
//            return cinema;
//        } catch (ParseException e) {
//            throw new IOException(e);
//        }
//    }

    @Override
    public String getNomCinema() throws IOException {
        return mapper.readValue(RESOURCE, CinemaWrapper.class).cinema.nom;
    }

}
