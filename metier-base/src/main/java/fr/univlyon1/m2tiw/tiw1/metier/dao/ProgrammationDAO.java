package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.Seance;

import java.io.IOException;
import java.util.Collection;

public interface ProgrammationDAO {
    Seance getSeanceById(String id);
    Film getFilmByTitreVersion(String titre, String version);
    Collection<Seance> getSeanceByFilm(Film film);
    void save(Seance seance) throws IOException;
    void save(Film film) throws IOException;
    void delete(Seance seance) throws IOException;
    void delete(Film film) throws IOException;
    int getNbSeance();
    Collection<Film> getFilms();
    void clearFilms() throws IOException;
    Collection<Seance> getSeances();
    void clearSeance() throws IOException;
}
