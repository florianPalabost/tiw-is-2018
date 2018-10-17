package fr.univlyon1.m2tiw.tiw1.metier.dao;

import fr.univlyon1.m2tiw.tiw1.metier.Salle;

import java.io.IOException;
import java.util.Collection;

public interface SalleDAO {
    Collection<Salle> loadSalles() throws IOException;
}
