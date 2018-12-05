package fr.univlyon1.tiw.tiw1.banque.metier;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Autorisation {
    private static final String GENERATOR_NAME = "autorisation_generator";

    @Id
    @GeneratedValue(generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = "autorisation_sequence")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private Compte parent;

    @ManyToOne
    @JoinColumn(name = "destinataire")
    private Compte destinataire;
    private double maximum;

    public Autorisation() {
    }

    public Autorisation(Compte parent, Compte destinataire, double maximum) {
        this.parent = parent;
        this.destinataire = destinataire;
        this.maximum = maximum;
    }

    public Compte getParent() {
        return parent;
    }

    public Compte getDestinataire() {
        return destinataire;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autorisation that = (Autorisation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
