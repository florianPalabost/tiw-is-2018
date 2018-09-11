package fr.univlyon1.m2tiw.tiw1.metier;

public class Reservation {
    private final String prenom;
    private final String nom;
    private final String email;
    private boolean paye;

    public Reservation(String prenom, String nom, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.paye = false;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPaye() {
        return paye;
    }

    public void setPaye(boolean paye) {
        this.paye = paye;
    }
}
