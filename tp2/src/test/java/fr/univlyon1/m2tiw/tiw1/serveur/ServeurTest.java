package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.ReservationDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class ServeurTest {

    private static final Logger LOG = LoggerFactory.getLogger(ServeurTest.class);

    @Test
    public void testServeurInstanciation() throws IOException, ParseException {
        Serveur s = new ServeurImpl();
    }

    @Test
    public void testCreerSupprimer() throws IOException, ParseException, SeanceCompleteException {
        Serveur s = new ServeurImpl();
        String film = ((FilmDTO) s.processRequest("film/add", Collections.singletonMap("film", new FilmDTO("film - A", "V1", "http://fiche.org/fiche-exemple")))).asFilm().getTitreVersion();
        String salle = "Salle 1";
        LOG.debug("film: {}, salle: {}", film, salle);
        String seance = (String) s.processRequest("seance/create", Collections.singletonMap("seance", new SeanceDTO(film, salle, "2018-09-20 20:00:00 +0200", 5.0f)));
        String reservation = (String) s.processRequest("reservation/reserver", Collections.singletonMap("reservation", new ReservationDTO(seance, "toto", "machin", "toto@machin.com")));
        assertTrue((Boolean) s.processRequest("reservation/annuler", Collections.singletonMap("id", reservation)));
        assertTrue((Boolean) s.processRequest("seance/remove", Collections.singletonMap("id", seance)));
        assertTrue((Boolean) s.processRequest("film/remove", Collections.singletonMap("id", film)));
    }

}
