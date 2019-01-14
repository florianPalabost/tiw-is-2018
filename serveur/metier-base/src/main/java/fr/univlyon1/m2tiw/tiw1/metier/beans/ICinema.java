package fr.univlyon1.m2tiw.tiw1.metier.beans;

import java.io.IOException;
import java.util.Map;
import org.picocontainer.Startable;

/**
 *
 * ICinema .
 *
 * @author florian
 */
public interface ICinema extends Startable {
  
    // Methode de service
    public Object process(String methode, String commande,
                          Map<String, Object> parametres) throws IOException;
    
}
