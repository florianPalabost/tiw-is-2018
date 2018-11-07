package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

public class ReservationDTO {
    public String seance;
    public String prenom;
    public String nom;
    public String email;

    public ReservationDTO(String seance, String prenom, String nom, String email) {
        this.seance = seance;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public ReservationDTO() {
    }
}
