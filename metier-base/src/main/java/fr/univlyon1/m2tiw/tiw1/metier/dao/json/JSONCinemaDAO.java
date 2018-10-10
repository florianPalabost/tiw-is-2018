package fr.univlyon1.m2tiw.tiw1.metier.dao.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.dao.CinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class JSONCinemaDAO implements CinemaDAO {

    private static final URL RESOURCE = JSONCinemaDAO.class.getResource("/sample-data/mon-cinema.json");

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Cinema load() throws IOException {
        CinemaDTO cinemaDTO = mapper.readValue(RESOURCE, CinemaWrapper.class).cinema;
        try {
            return cinemaDTO.asCinema();
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
