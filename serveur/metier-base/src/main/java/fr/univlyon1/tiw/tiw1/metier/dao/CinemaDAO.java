package fr.univlyon1.tiw.tiw1.metier.dao;

import java.io.IOException;

public interface CinemaDAO {
//    public Cinema load(String nom, List<Salle> salles, JSONProgrammationDAO progDAO,
//                       ReservationDAO reservDAO) throws IOException;

    public String getNomCinema() throws IOException;
}
