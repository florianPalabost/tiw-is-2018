package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.AbstractCinema;
import fr.univlyon1.m2tiw.tiw1.metier.Cinema;

import java.io.IOException;

public interface CinemaDAO {
    //public Cinema load() throws IOException;
    public String getNomCinema() throws IOException;
}
