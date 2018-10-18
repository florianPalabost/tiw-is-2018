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

public class ServeurTest {

    private static final Logger LOG = LoggerFactory.getLogger(ServeurTest.class);

    @Test
    public void testServeurInstanciation() throws IOException, ParseException {
        Serveur s = new Serveur();
    }

    @Test
    public void testCreerSupprimer() throws IOException, ParseException, SeanceCompleteException {
        Serveur s = new Serveur();
        String film = s.addFilm(new FilmDTO("film - A", "V1", "http://fiche.org/fiche-exemple"));
        String salle = "Salle 1";
        LOG.debug("film: {}, salle: {}", film, salle);
        String seance = s.createSeance(new SeanceDTO(film, salle, "2018-09-20 20:00:00 +0200", 5.0f));
        String reservation = s.reserver(new ReservationDTO(seance, "toto", "machin", "toto@machin.com"));
        s.annulerReservation(reservation);
        s.removeSeance(seance);
        s.removeFilm(film);
    }

}
