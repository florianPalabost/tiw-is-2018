package fr.univlyon1.tiw.tiw1.metier.beans;


import org.picocontainer.Startable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Logger;

public class Cinema implements Startable {
    private final String nom;
    /*private Map<String, Salle> salles;
    private Map<String, Film> films;
    private List<Seance> seances;
    private ReservationDAO reservationDAO;
    private ProgrammationDAO programmationDAO;
    private final String commande;*/
    CinemaRessourceFilms cineRessFilms;
    CinemaRessourceSalles cineRessSalles;
    CinemaRessourceSeances cineRessSeances;
    
    private static final Logger LOGGER = Logger.getLogger(Cinema.class.getName());

    /**
     *
     * Constructeur de Cinema avec nom.
     *
     * @param nom .
     * @param cineRessFilms .
     * @param cineRessSalles .
     * @param cineRessSeances .
     *
     * @throws IOException IOException
     * @throws ParseException ParseException
     *
     */
    public Cinema(String nom, CinemaRessourceFilms cineRessFilms,
                  CinemaRessourceSalles cineRessSalles,
            CinemaRessourceSeances cineRessSeances) throws IOException, ParseException {
        this.nom = nom;
        this.cineRessFilms = cineRessFilms;
        this.cineRessSalles = cineRessSalles;
        this.cineRessSeances = cineRessSeances;
   
        
        //this.salles = new HashMap<>();
        //this.films = new HashMap<>();
        //progDAO.getFilms().forEach((film) -> {
        //    this.films.put(film.getTitre(),film);
        //});

        //this.seances = new ArrayList<>(progDAO.getSeances().values());
        //reservationDAO = new JPAReservationDAO();
        //reservationDAO = reservDAO;
        //programmationDAO = progDAO;
        //setSalles(salles);
        //this.commande = "";
        //programmationDAO = new JSONProgrammationDAO(this.getSalles());
        
        // Attention, les salles doivent être cohérentes avec
        // l'information contenue dans le fichier JSON des seances
    }

    private String getNom() {
        return nom;
    }
    /*
    private void addSalle(Salle salle) {
        this.salles.put(salle.getNom(), salle);
    }

    private void removeSalle(Salle salle) {
        this.salles.remove(salle);
    }
    
    private void addFilm(Film film) throws IOException {
        this.films.put(film.getTitre() + " - " + film.getVersion(), film);
        programmationDAO.save(film);
    }

    private void removeFilm(Film film) {
        this.films.remove(film);
    }
    */
    /**
     *
     * Ajouter une seance .
     *
     * @param salle .
     * @param film .
     * @param date .
     * @param prix .
     *
     */
    
    /*
    private void createSeance(Salle salle, Film film, Date date, float prix) throws IOException {
        Seance seance = new Seance(film, salle, date, prix);
        this.seances.add(seance);
        programmationDAO.save(seance);
        seance.setReservationDAO(reservationDAO);
    }

    public void removeSeance(Seance seance) throws IOException {
        seances.remove(seance);
        programmationDAO.delete(seance);
    }

    private int getNbSeances() {
        return seances.size();
    }

    private Collection<Salle> getSalles() {
        return salles.values();
    }
    */
    /**
     *
     * Setter de salles .
     *
     * @param nSalles .
     *
     */
    /*
    public void setSalles(Collection<Salle> nSalles) {
        this.salles.clear();
        for (Salle s : nSalles) {
            addSalle(s);
        }
    }

    public Collection<Film> getFilms() {
        return films.values();
    }
    */
    /**
     *
     * Setter de seances .
     *
     * @param nFilms .
     *
     */
    /*
    private void setFilms(Collection<Film> nFilms) throws IOException {
        this.films.clear();
        for (Film f : nFilms) {
            addFilm(f);
        }
    }

    public List<Seance> getSeances() {
        return seances;
    }
    */
    /**
     *
     * Setter de seances .
     *
     * @param seances .
     *
     */
    /*
    private void setSeances(List<Seance> seances) {
        this.seances = seances;
        for (Seance s : seances) {
            s.setReservationDAO(reservationDAO);
        }
    }

    private Salle getSalle(String salle) {
        return salles.get(salle);
    }

    private Film getFilm(String film) {
        return films.get(film);
    }
    */

    /**
     *
     * process .
     *
     * @param methode .
     * @param commande .
     * @param parametres .
     * @return reponse souhaitee en fonction de methode, commande et params
     *
     */
    public Object process(String methode,String commande,
                          Map<String,Object> parametres) throws IOException, Exception {
        switch (methode) {
            case "CINEMA":
                return getNom();
            case "FILM":
                return cineRessFilms.process(commande, parametres);      
            case "SALLE":
                return cineRessSalles.process(commande, parametres);   
            case "SEANCE":
                return cineRessSeances.process(commande, parametres);   
            default:
                throw new Exception("Unknown commands : " + commande);
                
        }

    }
  

    /**
     * start the cinema .
     */
    @Override
    public void start() {
        LOGGER.info("Cinema STARTED ");
        //cineRessFilms.start();
        //cineRessSalles.start();
        //cineRessSeances.start();
        //LOGGER.info("Component Cinema STARTED. Objet d'acces aux données : " + this.getClass());
        /*LOGGER.info("Component Cinema STARTED. Objet d'acces aux données : "
        + this.programmationDAO.toString()); */
        //LOGGER.info(this.toString());
        // throw new UnsupportedOperationException("PB Not supported yet.");
        // To change body of generated methods, choose Tools | Templates.
    }

    /**
     * stop the cinema instance .
     */
    @Override
    public void stop() {
        LOGGER.info("Cinema STOPPED");
        //cineRessFilms.stop();
        //cineRessSalles.stop();
        //cineRessSeances.stop();
        // throw new UnsupportedOperationException("PB Not supported yet.");
        // To change body of generated methods, choose Tools | Templates.
    }
    
}
