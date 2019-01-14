package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.beans.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Film;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Seance;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface ProgrammationDAO {
    public static final String CONTEXT = "programmation";

    void initData(Cinema cinema) throws Exception;

    Seance getSeanceById(String id);

    Film getFilmByTitreVersion(String titre, String version);

    Collection<Seance> getSeanceByFilm(Film film);

    void save(Seance seance) throws IOException;

    void save(Film film) throws IOException;

    void delete(Seance seance) throws IOException;

    void delete(Film f) throws IOException;
    
    Map<String, Film> getFilms();
    
    Collection<Seance> getSeances();
    
    public Map<String, Salle> getSalles();
}
