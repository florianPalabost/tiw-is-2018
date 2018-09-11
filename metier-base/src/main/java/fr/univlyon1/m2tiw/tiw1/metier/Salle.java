package fr.univlyon1.m2tiw.tiw1.metier;

public class Salle {
    private final String nom;
    private final int capacite;

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }
}
