package fr.univlyon1.tiw.tiw1.banque.service;

import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import fr.univlyon1.tiw.tiw1.banque.dto.CompteDTO;
import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
import fr.univlyon1.tiw.tiw1.banque.metier.CompteOperations;
import fr.univlyon1.tiw.tiw1.banque.metier.OperationImpossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@WebService(targetNamespace = CompteService.NAMESPACE)
@Component
public class CompteService {
    public static final String NAMESPACE = "http://www.univ-lyon1.fr/tiw/tiw1/banque/compte";
    private static final Logger LOGGER = LoggerFactory.getLogger(CompteService.class);
    @Autowired
    private CompteOperations operations;
    @Autowired
    private CompteRepository repository;
    @Autowired
    private EntityManager em;

    @Transactional
    public void prelevement(
            @WebParam(name = "source") long idCompte,
            @WebParam(name = "destinataire") long destinataire,
            @WebParam(name = "montant") double valeur)
            throws CompteInconnuException, OperationImpossibleException {
        operations.prelevement(idCompte, destinataire, valeur);
    }

    @Transactional
    public void prelevementRef(
            @WebParam(name = "source") long idCompte,
            @WebParam(name = "destinataire") long destinataire,
            @WebParam(name = "montant") double valeur,
            @WebParam(name = "ref") String ref)
            throws CompteInconnuException, OperationImpossibleException, IOException, TimeoutException {
        operations.prelevementWithREF(idCompte, destinataire, valeur, ref);
    }

    @Transactional()
    public void autoriser(
            @WebParam(name = "source") long idCompte,
            @WebParam(name = "destinataire") long destinataire,
            @WebParam(name = "montant-max") double maximum)
            throws CompteInconnuException {
        operations.autoriser(idCompte, destinataire, maximum);
        LOGGER.info("Nombre d'autorisations pour {}: {}", idCompte, repository.findByIdOrFail(idCompte).getAutorisations().size());
    }

    @Transactional
    @WebResult(name = "compte")
    public CompteDTO infosCompte(
            @WebParam(name = "num-compte") long idCompte)
            throws CompteInconnuException {
        Compte compte = repository.findByIdOrFail(idCompte);
        LOGGER.info("Le compte {} comporte {} autorisations", compte.getId(), compte.getAutorisations().size());
        return new CompteDTO(compte);
    }

    @WebResult(name = "compte")
    @Transactional
    public CompteDTO creerCompte() {
        Compte compte = new Compte();
        repository.save(compte);
        return new CompteDTO(compte);
    }
}
