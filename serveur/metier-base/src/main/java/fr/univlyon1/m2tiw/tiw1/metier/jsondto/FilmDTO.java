package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.beans.Film;

public class FilmDTO {
    public String titre;
    public String version;
    public String fiche;

    public Film asFilm() {
        return new Film(titre, version, fiche);
    }

    /**
     *
     * FilmDTO.
     *
     * @param f .
     *
     */
    public static FilmDTO fromFilm(Film f) {
        FilmDTO dto = new FilmDTO();
        dto.titre = f.getTitre();
        dto.version = f.getVersion();
        dto.fiche = f.getFiche();
        return dto;
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "titre='" + titre + '\'' +
                ", version='" + version + '\'' +
                ", fiche='" + fiche + '\'' +
                '}';
    }
}
