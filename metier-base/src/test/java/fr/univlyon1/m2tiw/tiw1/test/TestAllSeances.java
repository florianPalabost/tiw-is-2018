package fr.univlyon1.m2tiw.tiw1.test;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestAllSeances {
    //TODO importer le fichier mon-cinema.json
    private final Cinema cinema = new Cinema("Mon Cinema");

    @Test
    /*
     * Teste si on a bien 4 séances / jour x 7 jours x 3 salles = 84 séances
     */
    public void testSeances() {
        assertEquals(84, cinema.getNbSeances());
    }
}
