package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.Salle;

public class SalleDTO {
    public String nom;
    public int capacite;

    public Salle asSalle() {
        return new Salle(nom, capacite);
    }
}
