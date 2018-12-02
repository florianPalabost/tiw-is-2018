package fr.univlyon1.tiw.tiw1.banque.metier;

import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CompteOperations {

    @Autowired
    private CompteRepository compteRepository;

    @Transactional
    public void prelevement(long idCompte, long destinataire, double valeur) throws CompteInconnuException, OperationImpossibleException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        if (compteParent.getMaximumAutorisation(compteDestinataire) >= valeur) {
            compteParent.debit(valeur);
            compteDestinataire.credit(valeur);
        } else {
            throw new OperationImpossibleException("Prélèvement non autorisé");
        }
    }

    @Transactional
    public void autoriser(long idCompte, long destinataire, double maximum) throws CompteInconnuException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        compteParent.autoriser(compteDestinataire, maximum);
    }
}
