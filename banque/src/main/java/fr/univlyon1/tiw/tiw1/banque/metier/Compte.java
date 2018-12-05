package fr.univlyon1.tiw.tiw1.banque.metier;

import org.slf4j.LoggerFactory;

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
    private Collection<Autorisation> autorisations = new ArrayList<>();

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

    public Autorisation autoriser(Compte destinataire, double maximum) {
        Optional<Autorisation> autoriationOpt =
                getAutorisationFor(destinataire);
        if (autoriationOpt.isPresent()) {
            final Autorisation autorisation = autoriationOpt.get();
            autorisation.setMaximum(maximum);
            return autorisation;
        } else {
            Autorisation autorisation = new Autorisation(this, destinataire, maximum);
            autorisations.add(autorisation);
            return autorisation;
        }
    }

    private Optional<Autorisation> getAutorisationFor(Compte destinataire) {
        return autorisations.stream().filter(a -> a.getDestinataire().equals(destinataire)).findFirst();
    }

    public double getMaximumAutorisation(Compte destinataire) {
        Optional<Autorisation> autoriationOpt =
                getAutorisationFor(destinataire);
        if (autoriationOpt.isPresent()) {
            return autoriationOpt.get().getMaximum();
        } else {
            LoggerFactory.getLogger(Compte.class).info("Pas d'autorisation pour {}", destinataire.getId());
            return 0.0;
        }
    }

    public Collection<Autorisation> getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(Collection<Autorisation> autorisations) {
        this.autorisations = autorisations;
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
