package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Film;

public class FilmDTO {
    public String titre;
    public String version;
    public String fiche;

    public Film asFilm() {
        return new Film(titre, version, fiche);
    }
}
