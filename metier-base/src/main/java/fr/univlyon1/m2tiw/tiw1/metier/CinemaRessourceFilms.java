package fr.univlyon1.m2tiw.tiw1.metier;

import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ReservationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.dao.SalleDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.utils.SeanceCompleteException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

public class CinemaRessourceFilms extends AbstractCinema {

    public CinemaRessourceFilms(String nom, SalleDAO salleDAO, ProgrammationDAO programmationDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        super(nom, salleDAO, programmationDAO, reservationDAO);
    }

    private void addFilm(Film film) throws IOException {
        getProgrammationDAO().save(film);
    }

    private FilmDTO addFilm(FilmDTO filmDTO) throws IOException {
        Film film = filmDTO.asFilm();
        addFilm(film);
        return FilmDTO.fromFilm(film);
    }

    private void removeFilm(Film film) throws IOException {
        getProgrammationDAO().delete(film);
    }

    private Collection<Film> getFilms() {
        return getProgrammationDAO().getFilms();
    }

    private Film getFilm(String film) {
        String titre = Utils.titreFromFilm(film);
        String version = Utils.versionFromFilm(film);
        return getProgrammationDAO().getFilmByTitreVersion(titre, version);
    }

    private void removeFilm(String film) throws IOException {
        Film f = getFilm(film);
        removeFilm(f);
    }

    @Override
    public Object processRequest(String commande, Map<String, Object> parameters) throws IOException, ParseException, SeanceCompleteException {
        switch (commande) {
            case "add":
                return addFilm((FilmDTO) parameters.get("film"));
            case "remove": {
                removeFilm((String) parameters.get("id"));
                return true;
            }
        }
        return null;
    }

}
