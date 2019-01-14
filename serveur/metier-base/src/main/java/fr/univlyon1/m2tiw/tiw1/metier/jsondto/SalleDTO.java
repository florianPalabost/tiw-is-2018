package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Salle;

public class SalleDTO {
    public String nom;
    public int capacite;
    
    /**
     *
     * asSalle .
     *
     * @return Salle
     */
    public Salle asSalle() {
        return new Salle(nom,capacite);
    }

    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("capacite")
    public int getCapacite() {
        return capacite;
    }

    @JsonProperty("capacite")
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public String toString() {
        return "{" + "nom: " + nom + ", capacite: " + capacite + '}';
    }
    
    
}
