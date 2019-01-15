package fr.univlyon1.tiw.tiw1.metier.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Salle {
    private final String nom;
    private final int capacite;

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    @JsonProperty("capacite")
    public int getCapacite() {
        return capacite;
    }

    @Override
    public String toString() {
        return "{" + "nom:" + nom + ", capacite:" + capacite + '}';
    }
    
    
}
