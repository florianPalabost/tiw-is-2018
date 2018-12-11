package fr.univlyon1.m2tiw.tiw1.metier;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONCinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaWrapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class CinemaTest {

    private final ObjectMapper mapper = new ObjectMapper();

//    @Test
//    /**
//     * Teste si on a bien 4 séances / jour x 7 jours x 3 salles = 84 séances
//     */
//    public void getNbSeances() throws Exception {
//        JSONCinemaDAO dao = new JSONCinemaDAO();
//        Cinema cinema = dao.load();
//        assertEquals(84, cinema.getNbSeances());
//    }

    @Ignore
    @Test
    public void testChargementJackson() throws IOException {
        CinemaWrapper wrapper = mapper.readValue(AbstractCinema.class.getResource("/sample-data/mon-cinema.json"), CinemaWrapper.class);
        assertEquals(84, wrapper.cinema.seances.size());
    }

    /*@Test
    public void testReservation() throws IOException, SeanceCompleteException {
        JSONCinemaDAO dao = new JSONCinemaDAO();
        Cinema cinema = dao.load();
        Seance s = cinema.getSeances().get(1);
        Reservation r = s.createReservation("titi","machin", "titi.machin@nowhere.net");
        s.cancelReservation(r);
    }*/
}
