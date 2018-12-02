package fr.univlyon1.tiw.tiw1.banque.metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Autorisation.Key.class)
public class Autorisation {

    public static class Key implements Serializable {
        private Compte parent;
        private Compte destinataire;

        public Compte getParent() {
            return parent;
        }

        public void setParent(Compte parent) {
            this.parent = parent;
        }

        public Compte getDestinataire() {
            return destinataire;
        }

        public void setDestinataire(Compte destinataire) {
            this.destinataire = destinataire;
        }

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
    @ManyToOne(cascade = CascadeType.ALL)
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
