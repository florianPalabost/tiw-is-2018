package fr.univlyon1.tiw.tiw1.banque.metier;

import fr.univlyon1.tiw.tiw1.banque.data.AutorisationRepository;
import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CompteOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompteOperations.class);
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private AutorisationRepository autorisationRepository;

    @Transactional
    public void prelevement(long idCompte, long destinataire, double valeur) throws CompteInconnuException, OperationImpossibleException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        if (compteParent.getMaximumAutorisation(compteDestinataire) >= valeur) {
            compteParent.debit(valeur);
            compteDestinataire.credit(valeur);
        } else {
            LOGGER.info("Prélèvement non autorisé: {} supérieur au maximum de {}",
                    valeur,
                    compteParent.getMaximumAutorisation(compteDestinataire));
            throw new OperationImpossibleException("Prélèvement non autorisé");
        }
    }

    @Transactional
    public void autoriser(long idCompte, long destinataire, double maximum) throws CompteInconnuException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        Autorisation autorisation = compteParent.autoriser(compteDestinataire, maximum);
        LOGGER.debug("Saving autorisation from {} to {}: {}",
                autorisation.getParent().getId(), autorisation.getDestinataire().getId(), autorisation.getMaximum());
        autorisationRepository.save(autorisation);
    }
}
