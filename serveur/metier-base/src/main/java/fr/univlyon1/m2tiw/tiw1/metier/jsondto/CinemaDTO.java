package fr.univlyon1.m2tiw.tiw1.metier.jsondto;

import fr.univlyon1.m2tiw.tiw1.metier.beans.Cinema;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.logging.Logger;

public class CinemaDTO {
    public static final SimpleDateFormat DATE_PARSER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    public String nom;
    public Collection<SalleDTO> salles;
    public Collection<FilmDTO> films;
    public Collection<SeanceDTO> seances;
    private static final Logger LOGGER = Logger.getLogger(CinemaDTO.class.getName());

    /**
     *
     * As Cinema .
     *
     *
     * @param nom nom du cinema (pour fichiers json)
     * @return Cinema
     *
     * @throws ParseException ParseException
     * @throws IOException IOException
     */
    public Cinema asCinema(String nom) throws ParseException, IOException {
        // List<Salle> salles =  new JSONSalleDAO().load();
        /*JSONProgrammationDAO progDAO = new JSONProgrammationDAO(nom,salles);
        JPAReservationDAO reservDAO = new JPAReservationDAO();*/

        //  LOGGER.info("CINEMA DTO->sallles");
        // LOGGER.info(sallesCinema.toString());
        //sallesCinema.addAll(salles.stream().map(SalleDTO::asSalle).collect(Collectors.toList()));
        // LOGGER.info("progDAO.toString()");
        // LOGGER.info(progDAO.toString());
        //Cinema cinema = new Cinema(nom, salles,progDAO,reservDAO);
        // progDAO.initData(cinema);
        
        // voir si il faut pas deplacer/ supp ces deux loop
        
        /* for (FilmDTO f : films) {
            cinema.addFilm(f.asFilm());
        }
        for (SeanceDTO s : seances) {
            Date d = DATE_PARSER.parse(s.date);
            cinema.createSeance(cinema.getSalle(s.salle), cinema.getFilm(s.film), d, s.prix);
        }*/
        //return cinema;
        return null;
    }
}
