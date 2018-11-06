package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;
import org.picocontainer.Startable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface Cinema extends Startable {
    @Override
    void start();

    @Override
    void stop();

    Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException;
}
