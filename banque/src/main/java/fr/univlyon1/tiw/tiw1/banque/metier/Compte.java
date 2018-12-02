package fr.univlyon1.tiw.tiw1.banque.metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Compte implements Serializable {
    private static final String GENERATOR_NAME = "compte_generator";
    @Id
    @GeneratedValue(generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = "compte_sequence")
    private long id;
    private double solde = 0.0;
    @OneToMany(mappedBy = "parent")
    @MapKey(name = "destinataire")
    private transient Map<Compte, Autorisation> autorisations = new HashMap<>();

    public Compte() {
    }

    public Compte(long id, double solde) {
        this();
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
        if (valeur > getSolde()) {
            throw new OperationImpossibleException("Solde trop faible");
        }
        setSolde(getSolde() - valeur);
    }

    public void credit(double valeur) {
        setSolde(getSolde() + valeur);
    }

    public void autoriser(Compte destinataire, double maximum) {
        Autorisation autorisation = autorisations.get(destinataire);
        if (autorisation == null) {
            autorisation = new Autorisation(this, destinataire, maximum);
            autorisations.put(destinataire, autorisation);
        } else {
            autorisation.setMaximum(maximum);
        }
    }

    public double getMaximumAutorisation(Compte destinataire) {
        Autorisation autorisation = autorisations.get(destinataire);
        return autorisation == null ? 0.0 : autorisation.getMaximum();
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
