package fr.univlyon1.m2tiw.tiw1.metier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class JPATest {
    EntityManager em = null;

    @Before
    public void setup() {
        em = Persistence.createEntityManagerFactory("test-pu").createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void testEmSetup() {
        // Nothing to do
    }

    @Test
    public void testReservationPersist() {
        Reservation r = new Reservation("toto", "machin", "toto.machin@nowhere.net");
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
        em.clear();
        Reservation r2 = em.find(Reservation.class, r.getId());
        assertEquals(r, r2);
    }
}
