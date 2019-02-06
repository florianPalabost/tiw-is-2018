package fr.univlyon1.tiw.tiw1.reservation.services;

import fr.univlyon1.tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.tiw.tiw1.reservation.dao.ReservationRepository;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class ReservationService {
   /* @Autowired
    private ProgrammationDAO programmationDAO;
    */
   @Autowired
    private ReservationRepository reservationRepository;
//    public Collection<Seance> getSeances() {
//        return programmationDAO.getSeances();
//    }

    public Collection<Reservation> findReservationsOfUserByMail(String email) {
        return reservationRepository.findByEmail(email);
    }

    public Collection<Seance> getSeances() {
        return null;
    }

    public Collection<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public void recordReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id){
        Reservation reservation = reservationRepository.getById(id);
        if(reservation != null) {
            reservationRepository.delete(reservation);
        }
    }
}
