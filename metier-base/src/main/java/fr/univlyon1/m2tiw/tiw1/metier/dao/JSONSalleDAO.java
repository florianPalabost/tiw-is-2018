package fr.univlyon1.m2tiw.tiw1.metier.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SalleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static fr.univlyon1.m2tiw.tiw1.metier.dao.JSONCinemaDAO.RESOURCE;

public class JSONSalleDAO implements SalleDAO {

    private static final Logger LOG = LoggerFactory.getLogger(JSONSalleDAO.class);

    private static ObjectMapper mapper = new ObjectMapper();
    private Map<String, Salle> salles;

    private void loadSallesIfNeeded() throws IOException {
        if (salles == null) {
            CinemaWrapper wrapper = mapper.readValue(RESOURCE, CinemaWrapper.class);
            salles = new HashMap<>();
            wrapper.cinema.salles.stream()
                    .map(SalleDTO::asSalle)
                    .forEach(salle -> salles.put(salle.getNom(), salle));
        }
    }

    @Override
    public Salle getSalle(String nom) throws IOException {
        if (salles == null) {
            loadSallesIfNeeded();
        }
        Salle salle = salles.get(nom);
        if (salle == null) {
            LOG.debug("Salle {} not found", nom);
        }
        return salle;
    }

    @Override
    public Collection<Salle> getSalles() throws IOException {
        loadSallesIfNeeded();
        return salles.values();
    }
}
