package fr.univlyon1.tiw.tiw1.tp3.service;

import fr.univlyon1.tiw.tiw1.metier.beans.Film;
import fr.univlyon1.tiw.tiw1.metier.beans.Salle;
import fr.univlyon1.tiw.tiw1.metier.beans.Seance;

import java.io.IOException;
import java.util.Collection;

public interface ICinemaService {
    Collection<Film> findAllFilms() throws IOException;
    Collection<Salle> findAllSalles() throws IOException;
    Collection<Seance> findAllSeances() throws IOException;
}
