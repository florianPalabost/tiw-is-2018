package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Collection;

public class JPAReservationDAO implements ReservationDAO {

    private static final String PERSISTENCE_UNIT_NAME = "pu";
    private EntityManager em;

    public JPAReservationDAO(EntityManager em) {
        this.em = em;
    }

    public JPAReservationDAO() {
        em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    }

    @Override
    public void save(Reservation reservation) {
        em.getTransaction().begin(); // Ne devrait pas être ici, mais on verra ça avec les transactions déclaratives de Spring
        if (reservation.getId() == null) {
            em.persist(reservation);
        } else {
            Reservation inEm = em.find(Reservation.class, reservation.getId());
            if (inEm != null) {
                em.merge(reservation);
            } else {
                em.persist(reservation);
            }
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Reservation reservation) {
        Reservation persisted = getById(reservation.getId());
        if (persisted != null) {
            em.getTransaction().begin(); // Ne devrait pas être ici, mais on verra ça avec les transactions déclaratives de Spring
            em.remove(persisted);
            em.getTransaction().commit();
        }
    }

    @Override
    public Collection<Reservation> getBySeance(String seanceId) {
        return em.createNamedQuery("getBySeance", Reservation.class).setParameter(1, seanceId).getResultList();
    }

    @Override
    public Reservation getById(long id) {
        return em.find(Reservation.class, id);
    }
}
