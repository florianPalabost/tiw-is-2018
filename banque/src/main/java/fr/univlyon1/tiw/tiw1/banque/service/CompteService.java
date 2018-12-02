package fr.univlyon1.tiw.tiw1.banque.service;

import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import fr.univlyon1.tiw.tiw1.banque.dto.CompteDTO;
import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
import fr.univlyon1.tiw.tiw1.banque.metier.CompteOperations;
import fr.univlyon1.tiw.tiw1.banque.metier.OperationImpossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;

@WebService(targetNamespace = CompteService.NAMESPACE)
@Component
public class CompteService {
    public static final String NAMESPACE = "http://www.univ-lyon1.fr/tiw/tiw1/banque/compte";
    @Autowired
    private CompteOperations operations;
    @Autowired
    private CompteRepository repository;

    @Transactional
    public void prelevement(long idCompte, long destinataire, double valeur) throws CompteInconnuException, OperationImpossibleException {
        operations.prelevement(idCompte, destinataire, valeur);
    }

    @Transactional
    public void autoriser(long idCompte, long destinataire, double maximum) throws CompteInconnuException {
        operations.autoriser(idCompte, destinataire, maximum);
    }

    public CompteDTO infosCompte(long idCompte) throws CompteInconnuException {
        return new CompteDTO(repository.findByIdOrFail(idCompte));
    }

    @Transactional
    public CompteDTO creerCompte() {
        Compte compte = new Compte();
        repository.save(compte);
        return new CompteDTO(compte);
    }
}
