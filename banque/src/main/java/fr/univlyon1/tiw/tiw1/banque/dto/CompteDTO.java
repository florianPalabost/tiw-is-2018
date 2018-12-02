package fr.univlyon1.tiw.tiw1.banque.dto;

import fr.univlyon1.tiw.tiw1.banque.metier.Compte;

public class CompteDTO {
    private long id;
    private double solde;

    public CompteDTO(Compte compte) {
        this(compte.getId(), compte.getSolde());
    }

    public CompteDTO(long id, double solde) {
        this.id = id;
        this.solde = solde;
    }

    public CompteDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
