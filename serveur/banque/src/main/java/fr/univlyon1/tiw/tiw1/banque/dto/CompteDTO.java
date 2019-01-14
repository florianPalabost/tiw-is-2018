package fr.univlyon1.tiw.tiw1.banque.dto;

import fr.univlyon1.tiw.tiw1.banque.metier.Compte;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CompteDTO {
    private long id;
    private double solde;
    private Collection<AutorisationDTO> autorisations;

    public CompteDTO(Compte compte) {
        this(compte.getId(), compte.getSolde());
        this.autorisations =
                compte.getAutorisations().stream()
                        .map(AutorisationDTO::new)
                        .collect(Collectors.toCollection(ArrayList::new));
    }

    public CompteDTO(long id, double solde) {
        this.id = id;
        this.solde = solde;
        this.autorisations = new ArrayList<>();
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

    @XmlElement(name = "autorisation")
    public Collection<AutorisationDTO> getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(Collection<AutorisationDTO> autorisations) {
        this.autorisations = autorisations;
    }
}
