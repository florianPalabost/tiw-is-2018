package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONCinemaDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.JSONProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProgrammationDAOTest {

    @Test
    public void testEcritFichiers() throws IOException, ParseException {
        Cinema cinema = new JSONCinemaDAO().load();
    }

    @Test
    public void testGetSeancesByFilm() throws IOException, ParseException {
        Cinema cinema = new JSONCinemaDAO().load();
        ProgrammationDAO dao = cinema.getProgrammationDAO();
        Film mi = dao.getFilmByTitreVersion("Mission Impossible - Fallout", "VF");
        Collection<Seance> seances = dao.getSeanceByFilm(mi);
        assertEquals(14, seances.size());
    }

    @Test
    public void testGetFilm() throws IOException, ParseException {
        Cinema cinema = new JSONCinemaDAO().load();
        ProgrammationDAO dao = cinema.getProgrammationDAO();
        Film mi = dao.getFilmByTitreVersion("Mission Impossible - Fallout", "VF");
        assertNotNull(mi);
        assertEquals("https://www.imdb.com/title/tt4912910/?ref_=inth_ov_tt", mi.getFiche());
    }

    @Test
    public void testGetSeanceById() throws IOException, ParseException {
        Cinema cinema = new JSONCinemaDAO().load();
        ProgrammationDAO dao = cinema.getProgrammationDAO();
        Seance s = cinema.getSeances().get(0);
        Seance s2 = dao.getSeanceById(s.getId());
        assertEquals(s, s2);
    }
}
