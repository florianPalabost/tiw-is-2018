package fr.univlyon1.tiw.tiw1.banque.metier;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.*;

@Entity
public class Compte implements Serializable {
    @Id
    private long id;
    private double solde = 0.0;
    @OneToMany(mappedBy = "parent")
    @MapKey(name = "destinataire")
    private Map<Compte, Autorisation> autorisations = new HashMap<>();

    public Compte() {
    }

    public Compte(long id, double solde) {
        this.id = id;
        this.solde = solde;
    }

    public long getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void debit(double valeur) throws OperationImpossibleException {
        setSolde(getSolde() - valeur);
    }

    public void credit(double valeur) {
        setSolde(getSolde() + valeur);
    }

    public void autoriser(Compte destinataire, double maximum) {
        // TODO: implement
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte compte = (Compte) o;
        return id == compte.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
