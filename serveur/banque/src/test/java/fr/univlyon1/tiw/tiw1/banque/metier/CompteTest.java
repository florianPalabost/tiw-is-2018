package fr.univlyon1.tiw.tiw1.banque.metier;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompteTest {

    private Compte compte;
    private static final double DELTA = 0.000001;

    @Before
    public void initCompte() {
        compte = new Compte(123, 100.0);
    }

    @Test
    public void testDebitOK() throws OperationImpossibleException {
        compte.debit(50.0);
        assertEquals(50.0, compte.getSolde(), DELTA);
        compte.debit(50.0);
        assertEquals(0.0, compte.getSolde(), DELTA);
    }

    @Test
    public void testDebitNotOK() {
        try {
            compte.debit(200.0);
            assertTrue("Should have thrown exception", false);
        } catch (OperationImpossibleException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCredit() {
        compte.credit(50.0);
        assertEquals(150.0, compte.getSolde(), DELTA);
    }

    @Test
    public void testAutoriser() {
        Compte compte2 = new Compte(234, 50.0);
        assertEquals(0.0, compte.getMaximumAutorisation(compte2), DELTA);
        compte.autoriser(compte2, 50.0);
        assertEquals(50.0, compte.getMaximumAutorisation(compte2), DELTA);
        compte.autoriser(compte2, 25.0);
        assertEquals(25.0, compte.getMaximumAutorisation(compte2), DELTA);
    }
}
