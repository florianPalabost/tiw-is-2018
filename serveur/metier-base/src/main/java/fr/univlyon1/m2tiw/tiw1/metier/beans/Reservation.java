package fr.univlyon1.m2tiw.tiw1.metier.beans;

//import javax.persistence.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "getBySeance", query =
                "SELECT r FROM Reservation r WHERE r.seanceId = 'id'")
    })
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private boolean paye;
    @Column(nullable = false)
    private String seanceId;

    public Reservation() {
    }

    /**
     *
     * Constructeur de Reservation .
     *
     * @param prenom .
     *
     * @param nom .
     *
     * @param email .
     *
     */
    public Reservation(String prenom, String nom, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.paye = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPaye() {
        return paye;
    }

    public void setPaye(boolean paye) {
        this.paye = paye;
    }

    public String getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(String seanceId) {
        this.seanceId = seanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" + "id:" + id
                   + ", prenom:" + prenom
                   + ", nom:" + nom
                   + ", email:" + email
                   + ", paye:" + paye
                   + ", seanceId:" + seanceId + '}';
    }
    
    
}
