package fr.univlyon1.m2tiw.tiw1.metier;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class CinemaTest {
    //TODO importer le fichier mon-cinema.json
    private final Cinema cinema = new Cinema("Mon Cinema");

    @Test
    @Ignore
    /**
     * Teste si on a bien 4 séances / jour x 7 jours x 3 salles = 84 séances
     */
    public void getNbSeances() throws Exception {
        assertEquals(84, cinema.getNbSeances());
    }
}
