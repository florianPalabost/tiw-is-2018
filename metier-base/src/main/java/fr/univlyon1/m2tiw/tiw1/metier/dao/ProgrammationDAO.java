package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.Seance;

import java.io.IOException;
import java.util.Collection;

public interface ProgrammationDAO {
    void initData(Cinema cinema) throws IOException;
    Seance getSeanceById(String id);
    Film getFilmByTitreVersion(String titre, String version);
    Collection<Seance> getSeanceByFilm(Film film);
}
