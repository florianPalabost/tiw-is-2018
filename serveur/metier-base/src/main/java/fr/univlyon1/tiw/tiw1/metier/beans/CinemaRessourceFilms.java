package fr.univlyon1.tiw.tiw1.metier.beans;

import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONCinemaDAO;
import fr.univlyon1.tiw.tiw1.metier.dao.impl.JSONProgrammationDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * CinemaRessourceFilms .
 *
 * @author florian
 */

// Gestion et consultation des films pour ressources
@Component
public class CinemaRessourceFilms extends ACinemaRessource {
    // addFilm(), removeFilm() et getFilm()
    private static final Logger LOGGER = Logger.getLogger(CinemaRessourceSalles.class.getName());

    private Map<String, Film> films;
    private JSONCinemaDAO cinemaDAO = new JSONCinemaDAO();
    private JSONProgrammationDAO progDAO = new JSONProgrammationDAO();

    public CinemaRessourceFilms() throws IOException, ParseException {
        this.films =  progDAO.getFilms();
    }

    private ResponseEntity<Void> addFilm(Film film) throws IOException {
        this.films.put(film.getTitre() + " - " + film.getVersion(), film);
        progDAO.save(film);
        return null;
    }

    private void removeFilm(String key) throws IOException {
        if (this.films.containsKey(key)){
            this.films.remove(key);
            String[] tmpTitreVersion = key.split("-");
            Film f = progDAO.getFilmByTitreVersion(tmpTitreVersion[0],tmpTitreVersion[1]);
            progDAO.delete(f);
        }
    }
    
    private void setFilms(Collection<Film> nFilms) throws IOException {
        this.films.clear();
        for (Film f : nFilms) {
            addFilm(f);
        }
    }
    
    private Film getFilm(String film) {
        return films.get(film);
    }
    
    public Collection<Film> getFilms() {
        return films.values();
    }
    
    public int getNbFilms() {
        return films.size();
    }

    public Film getFilmByKey(String key){
        return films.get(key);
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
            case "getFilms":
                return getFilms();
                
            case "getFilm":
                return getFilm((String) parametres.get("titre"))
                        ;
           case "getFilmByKey":
                return getFilm((String) parametres.get("key"));

            case "addFilm":
                addFilm((Film) parametres.get("film"));
                return null;

            case "removeFilm":
                removeFilm((String) parametres.get("key"));
                return null;
                
            case "getNbFilms":
                return getNbFilms();
                
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
