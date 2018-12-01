package fr.univlyon1.tiw.tiw1.banque.service;

import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(targetNamespace = CompteService.NAMESPACE)
public class CompteService {

    public static final String NAMESPACE = "http://www.univ-lyon1.fr/tiw/tiw1/banque/compte";

    @Autowired
    private CompteRepository compteRepository;

    public boolean prelevement(long idCompte, long destinataire, double valeur) {
        // TODO: implement
        return false;
    }

    public void autoriser(long idCompte, long destinataire, double maximum) {
        // TODO: implement
    }
}
