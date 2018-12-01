package fr.univlyon1.tiw.tiw1.banque.metier;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Autorisation.Key.class)
public class Autorisation {

    public static class Key implements Serializable {
        public Compte parent;
        public Compte destinataire;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(parent, key.parent) &&
                    Objects.equals(destinataire, key.destinataire);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, destinataire);
        }
    }

    @Id
    @ManyToOne
    private Compte parent;
    @Id
    @ManyToOne
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
}
