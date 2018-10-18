package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Film;

public class FilmDTO {
    public String titre;
    public String version;
    public String fiche;

    public FilmDTO(String titre, String version, String fiche) {
        this.titre = titre;
        this.version = version;
        this.fiche = fiche;
    }

    public FilmDTO() {
    }

    public Film asFilm() {
        return new Film(titre, version, fiche);
    }

    public static FilmDTO fromFilm(Film f) {
        FilmDTO dto = new FilmDTO();
        dto.titre = f.getTitre();
        dto.version = f.getVersion();
        dto.fiche = f.getFiche();
        return dto;
    }
}
