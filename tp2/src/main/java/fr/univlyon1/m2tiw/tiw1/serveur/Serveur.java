package fr.univlyon1.m2tiw.tiw1.serveur;

import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface Serveur {
    Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException;
}
