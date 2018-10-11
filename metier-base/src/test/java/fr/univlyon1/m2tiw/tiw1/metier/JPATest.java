package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.JPAReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        r.setSeanceId("s1");
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
        em.clear();
        Reservation r2 = em.find(Reservation.class, r.getId());
        assertEquals(r, r2);
    }

    @Test
    public void testReservationDAOPersistAndFind() {
        ReservationDAO dao = new JPAReservationDAO(em);
        Reservation r = new Reservation("toto", "machin", "toto.machin@nowhere.net");
        r.setSeanceId("s1");
        dao.save(r);
        em.clear();
        Reservation r2 = dao.getById(r.getId());
        assertEquals(r, r2);
    }

    @Test
    public void testGetReservationBySeance() {
        ReservationDAO dao = new JPAReservationDAO(em);
        Reservation r = new Reservation("toto", "machin", "toto.machin@nowhere.net");
        r.setSeanceId("s1");
        dao.save(r);
        Reservation r2 = new Reservation("titi", "machin", "titi.machin@nowhere.net");
        r2.setSeanceId("s1");
        dao.save(r2);
        Reservation r3 = new Reservation("titi", "machin", "titi.machin@nowhere.net");
        r3.setSeanceId("s2");
        dao.save(r3);
        Collection<Reservation> reservations = dao.getBySeance("s1");
        assertTrue(reservations.contains(r));
        assertTrue(reservations.contains(r2));
        assertEquals(2, reservations.size());
    }
}
