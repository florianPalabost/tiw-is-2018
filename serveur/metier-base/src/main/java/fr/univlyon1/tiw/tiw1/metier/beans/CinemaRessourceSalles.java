package fr.univlyon1.tiw.tiw1.metier.beans;

import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONProgrammationDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * CinemaRessourceSalles .
 *
 * @author florian
 */
// gestion des salles
@Component
public class CinemaRessourceSalles extends ACinemaRessource {
    //addSalle, removeSalle(on oubli ces deux normalement) et enfin getSalles(), getSalle()
    private Map<String, Salle> salles = new HashMap<>();
    private JSONProgrammationDAO programmationDAO = new JSONProgrammationDAO();
    private static final Logger LOGGER = Logger.getLogger(CinemaRessourceSalles.class.getName());

    
    /* public CinemaRessourceSalles(List<Salle> salles) {
        setSalles(salles);
    } */

    /**
     *
     * CinemaRessourceSalles .
     *
     *
     */

    public CinemaRessourceSalles() throws IOException, ParseException {
        this.salles = programmationDAO.getSalles();
    }

    private void addSalle(Salle salle) {
        this.salles.put(salle.getNom(), salle);
    }

    private void removeSalle(String nomSalle) {
        this.salles.remove(nomSalle);
    }
    
    private Salle getSalle(String salle) {
        return salles.get(salle);
    }
    
    private Collection<Salle> getSalles() {
        return salles.values();
    }

    /**
     *
     * setSalles .
     *
     * @param nSalles .
     *
     */
    public void setSalles(Collection<Salle> nSalles) {
        this.salles.clear();
        for (Salle s : nSalles) {
            addSalle(s);
        }
    }
    
    public int getNbSalles() {
        return this.salles.size();
    }

    private Salle getSalleByName(String name) {
        return salles.get(name);
    }
    /**
     *
     * process .
     *
     * @param commande .
     * @param parametres .
     *
     * @return Object .
     *
     * @throws IOException Exception IO
     */
    public Object process(String commande, Map<String,Object> parametres) throws IOException {
        switch (commande) {
            case "getSalles":
                return getSalles();
            case "getSalle":
                return getSalle((String) parametres.get("nomSalle"));

            case "getSalleByName":
                return getSalleByName((String) parametres.get("name"));
            case "addSalle":
                addSalle((Salle)parametres.get("salle"));
                return null;

            case "removeSalle":
                removeSalle((String)parametres.get("nomSalle"));
                return null;
                
            case "getNbSalles":
                return getNbSalles();

            default:
                return null;
        }
    }
    
    @Override
    public void start() {
        LOGGER.info("Component " + this.getClass() + " started");
    }
    
    @Override
    public void stop() {
        LOGGER.info("Component " + this.getClass() + " stopped");
    }
    
}
