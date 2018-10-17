package fr.univlyon1.m2tiw.tiw1.metier.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SalleDTO;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static fr.univlyon1.m2tiw.tiw1.metier.dao.JSONCinemaDAO.RESOURCE;

public class JSONSalleDAO implements SalleDAO {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Collection<Salle> loadSalles() throws IOException {
        CinemaWrapper wrapper = mapper.readValue(RESOURCE, CinemaWrapper.class);
        return wrapper.cinema.salles.stream().map(SalleDTO::asSalle).collect(Collectors.toList());
    }
}
