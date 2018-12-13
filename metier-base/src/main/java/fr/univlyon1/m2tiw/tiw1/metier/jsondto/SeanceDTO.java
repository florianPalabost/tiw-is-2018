package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Seance;
import fr.univlyon1.m2tiw.tiw1.metier.Utils;

public class SeanceDTO {
    public String film;
    public String salle;
    public String date;
    public float prix;

    public SeanceDTO(String film, String salle, String date, float prix) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.prix = prix;
    }

    public SeanceDTO() {
    }

    public static SeanceDTO fromSeance(Seance seance) {
        SeanceDTO dto = new SeanceDTO();
        dto.film = seance.getFilm().getTitre() + " - " + seance.getFilm().getVersion();
        dto.salle = seance.getSalle().getNom();
        dto.date = Utils.DATE_PARSER.format(seance.getDate());
        dto.prix = seance.getPrix();
        return dto;
    }
}