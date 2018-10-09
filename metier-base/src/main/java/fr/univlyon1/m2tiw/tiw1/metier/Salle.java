package fr.univlyon1.m2tiw.tiw1.metier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Salle {
    private final String nom;
    private final int capacite;

    @JsonCreator
    public Salle(@JsonProperty("nom") String nom, @JsonProperty("capacite") int capacite) {
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
