package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.beans.Seance;


public class SeanceDTO {
    public String film;
    public String salle;
    public String date;
    public float prix;

    /**
     *
     * Seance DTO .
     *
     * @param seance
     *
     */
    public static SeanceDTO fromSeance(Seance seance) {
        SeanceDTO dto = new SeanceDTO();
        dto.film = seance.getFilm().getTitre() + " - " + seance.getFilm().getVersion();
        dto.salle = seance.getSalle().getNom();
        dto.date = CinemaDTO.DATE_PARSER.format(seance.getDate());
        dto.prix = seance.getPrix();
        return dto;
    }
/*
    public SeanceDTO() {
    }

    public SeanceDTO(String film, String salle, String date, float prix) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.prix = prix;
    }*/

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
