package fr.univlyon1.tiw.tiw1.metier.beans;

import fr.univlyon1.tiw.tiw1.metier.dao.ProgrammationDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * ACinemaRessource .
 *
 * @author florian
 */
public abstract class ACinemaRessource implements ICinema {
    public static final String CTX_CINE_RESS = "/app";
    private static final Logger LOGGER = Logger.getLogger(ACinemaRessource.class.getName());
    // private final String nom;
    private Map<String, Salle> salles;
    private Map<String, Film> films;
    private List<Seance> seances;
//    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;
    private final String methode = "";
    private CinemaRessourceFilms cineRessFilms;
    private CinemaRessourceSalles cineRessSalles;
    private CinemaRessourceSeances cineRessSeances;

    @Override
    public void start() {
        LOGGER.info("Component CinemaAbs STARTED. Objet d'acces aux données : "
                + this.programmationDAO.toString());
    }

    @Override
    public void stop() {
        LOGGER.info("CinemaAbs STOPPED");
    }

    /**
     *
     * process .
     *
     * @param methode .
     * @param commande .
     * @param parametres .
     * @return Object .
     * @throws IOException Exception IO
     */
    @Override
    public Object process(String methode, String commande,
                          Map<String, Object> parametres) throws IOException  {
        // suivant la commande XXXXX appellez le bon CinemaRessourceXXXX
        switch (methode) {
            case "FILM":
                return cineRessFilms.process(commande, parametres);      
            case "SALLE":
                return cineRessSalles.process(commande, parametres);   
            case "SEANCE":
                return cineRessSeances.process(commande, parametres);   
            default:
                return null;
        }
    }
    
}
