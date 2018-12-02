package fr.univlyon1.tiw.tiw1.banque.service;


import fr.univlyon1.tiw.tiw1.banque.App;
import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import fr.univlyon1.tiw.tiw1.banque.dto.CompteDTO;
import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
import fr.univlyon1.tiw.tiw1.banque.metier.OperationImpossibleException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {App.class})
@Transactional
public class CompteServiceTest {

    private static final double DELTA = 0.000001;
    public static final long ID_COMPTE_1 = 123456789L;
    public static final long ID_COMPTE_2 = 234567890L;
    public static final long ID_COMPTE_3 = 345678901L;

    @Autowired
    private CompteService service;

    @Autowired
    private CompteRepository repository;

    @Test
    public void testCreerInfosCompte() throws CompteInconnuException {
        CompteDTO compte = service.creerCompte();
        long id = compte.getId();
        CompteDTO compte2 = service.infosCompte(id);
        assertEquals(id, compte2.getId());
        assertEquals(compte.getSolde(), compte2.getSolde(), DELTA);
    }

    @Test
    public void testAutorisation() throws CompteInconnuException {
        Compte compte2 = repository.findByIdOrFail(ID_COMPTE_2);
        assertEquals(0.0,
                repository.findByIdOrFail(ID_COMPTE_1).getMaximumAutorisation(compte2),
                DELTA);
        Compte compte1 = repository.findByIdOrFail(ID_COMPTE_1);
        service.autoriser(ID_COMPTE_1, ID_COMPTE_2, 5.0);
        assertEquals(5.0,
                repository.findByIdOrFail(ID_COMPTE_1).getMaximumAutorisation(compte2),
                DELTA);
    }

    @Test
    public void testPrelevementOk() throws CompteInconnuException, OperationImpossibleException {
        service.autoriser(ID_COMPTE_1, ID_COMPTE_2, 10.0);
        service.prelevement(ID_COMPTE_1, ID_COMPTE_2, 5.0);
        service.prelevement(ID_COMPTE_1, ID_COMPTE_2, 10.0);
        Compte compte1 = repository.findByIdOrFail(ID_COMPTE_1);
        Compte compte2 = repository.findByIdOrFail(ID_COMPTE_2);
        assertEquals(185.0, compte1.getSolde(), DELTA);
        assertEquals(115.0, compte2.getSolde(), DELTA);
    }

    @Test(expected = OperationImpossibleException.class)
    public void testPrelevementNotOkExists() throws CompteInconnuException, OperationImpossibleException {
        service.autoriser(ID_COMPTE_1, ID_COMPTE_2, 10.0);
        service.prelevement(ID_COMPTE_1, ID_COMPTE_2, 15.0);
    }

    @Test(expected = OperationImpossibleException.class)
    public void testPrelevementNotOkNoAutorisation() throws CompteInconnuException, OperationImpossibleException {
        service.prelevement(ID_COMPTE_1, ID_COMPTE_3, 1.0);
    }

}
