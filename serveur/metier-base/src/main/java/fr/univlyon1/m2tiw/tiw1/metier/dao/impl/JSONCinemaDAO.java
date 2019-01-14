package fr.univlyon1.m2tiw.tiw1.metier.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.dao.CinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;

import java.io.IOException;
import java.net.URL;

public class JSONCinemaDAO implements CinemaDAO {

    private static final URL RESOURCE = JSONCinemaDAO.class.getResource(
            "/sample-data/mon-cinema.json");

    private final ObjectMapper mapper = new ObjectMapper();

//    @Override
//    public Cinema load(String nom, List<Salle> salles, JSONProgrammationDAO progDAO,
//                       ReservationDAO reservDAO) throws IOException {
//        CinemaDTO cinemaDTO = mapper.readValue(RESOURCE, CinemaWrapper.class).cinema;
//        try {
//            return cinemaDTO.asCinema(nom);
//        } catch (ParseException ex) {
//            Logger.getLogger(JSONCinemaDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    public String getNomCinema() throws IOException {
        return mapper.readValue(RESOURCE, CinemaWrapper.class).cinema.nom;
    }

}
