package fr.univlyon1.tiw.tiw1.banque.dto;

import fr.univlyon1.tiw.tiw1.banque.metier.Autorisation;

public class AutorisationDTO {
    private long destinataire;
    private double montant;

    public AutorisationDTO() {
    }

    public AutorisationDTO(Autorisation autorisation) {
        this.destinataire = autorisation.getDestinataire().getId();
        this.montant = autorisation.getMaximum();
    }

    public long getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(long destinataire) {
        this.destinataire = destinataire;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
