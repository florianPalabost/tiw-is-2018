package fr.univlyon1.m2tiw.tiw1.metier.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.univlyon1.m2tiw.tiw1.Annuaire;
import fr.univlyon1.m2tiw.tiw1.metier.Film;
import fr.univlyon1.m2tiw.tiw1.metier.Seance;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static fr.univlyon1.m2tiw.tiw1.metier.Constants.*;
import static fr.univlyon1.m2tiw.tiw1.metier.Utils.DATE_PARSER;

public class JSONProgrammationDAO implements ProgrammationDAO {

    private static final Logger LOG = LoggerFactory.getLogger(JSONProgrammationDAO.class);

    public static final String SEANCES_JSON = "seances.json";
    public static final String FILMS_JSON = "films.json";
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static JavaType list_of_seances_type = TypeFactory.defaultInstance().constructCollectionType(Collection.class, SeanceDTO.class);
    private static JavaType list_of_films_type = TypeFactory.defaultInstance().constructCollectionType(Collection.class, FilmDTO.class);

    private List<Film> films = null;
    private Map<String, Seance> seances = null;
    private SalleDAO salleDAO;
    private File seancesFile;
    private File filmsFile;
    private ReservationDAO reservationDAO;

    public JSONProgrammationDAO(String nomCinema, SalleDAO salleDAO, ReservationDAO reservationDAO) throws IOException, ParseException {
        this.seancesFile = new File(nomCinema, SEANCES_JSON);
        this.filmsFile = new File(nomCinema, FILMS_JSON);
        this.salleDAO = salleDAO;
        this.reservationDAO = reservationDAO;
    }

    private void save() throws IOException {
        if (!filmsFile.getParentFile().exists()) {
            filmsFile.getParentFile().mkdirs();
        }
        Collection<SeanceDTO> seanceDTOs = seances.values().stream().map(SeanceDTO::fromSeance).collect(Collectors.toList());
        mapper.writeValue(seancesFile, seanceDTOs);
        Collection<FilmDTO> filmDTOs = films.stream().map(FilmDTO::fromFilm).collect(Collectors.toList());
        mapper.writeValue(filmsFile, filmDTOs);
    }

    private void load() {
        try {
            films = new ArrayList<>();
            seances = new HashMap<>();
            if (filmsFile.exists()) {
                Collection<FilmDTO> filmDTOs = mapper.readValue(filmsFile, list_of_films_type);
                films.addAll(filmDTOs.stream().map(FilmDTO::asFilm).collect(Collectors.toList()));
                if (seancesFile.exists()) {
                    Collection<SeanceDTO> seanceDTOs = mapper.readValue(seancesFile, list_of_seances_type);
                    for (SeanceDTO dto : seanceDTOs) {
                        final Film film = getFilmById(dto.film);
                        if (film != null) {
                            Seance s = new Seance(film, getSalleDAO().getSalle(dto.salle), DATE_PARSER.parse(dto.date), dto.prix);
                            s.setReservationDAO(getReservationDAO());
                            seances.put(s.getId(), s);
                        } else {
                            LOG.warn("Seance without matching film ({}). It will not be loaded.", dto.film);
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            LOG.error("Could not load data in JSONProgrammationDAO", e);
        }
    }

    private ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    private SalleDAO getSalleDAO() {
        return this.salleDAO;
    }

    @Override
    public Seance getSeanceById(String id) {
        if (seances == null) {
            load();
        }
        return seances.get(id);
    }

    @Override
    public Film getFilmByTitreVersion(String titre, String version) {
        if (seances == null) {
            load();
        }
        for (Film f : films) {
            if (f.getTitre().equals(titre) && f.getVersion().equals(version)) {
                return f;
            }
        }
        return null;
    }

    private Film getFilmById(String id) {
        if (seances == null) {
            load();
        }
        for (Film f : films) {
            if ((f.getTitreVersion()).equals(id)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public Collection<Seance> getSeanceByFilm(Film film) {
        if (seances == null) {
            load();
        }
        Collection<Seance> result = new ArrayList<>();
        for (Seance s : seances.values()) {
            if (s.getFilm().equals(film)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public void save(Seance seance) throws IOException {
        if (seances == null) {
            load();
        }
        seances.put(seance.getId(), seance);
        seance.setReservationDAO(getReservationDAO());
        save();
    }

    @Override
    public void save(Film film) throws IOException {
        if (seances == null) {
            load();
        }
        int idx = films.indexOf(film);
        if (idx == -1) {
            films.add(film);
        } else {
            films.set(idx, film);
        }
        save();
    }

    @Override
    public void delete(Seance seance) throws IOException {
        if (seances == null) {
            load();
        }
        LOG.debug("Removing {} ({} seances)", seance, seances.size());
        seances.remove(seance.getId());
        LOG.debug("{} seances after remove", seances.size());
        save();
    }

    @Override
    public void delete(Film film) throws IOException {
        if (seances == null) {
            load();
        }
        films.remove(film);
        save();
    }

    @Override
    public int getNbSeance() {
        if (seances == null) {
            load();
        }
        return seances.size();
    }

    @Override
    public Collection<Film> getFilms() {
        if (seances == null) {
            load();
        }
        return films;
    }

    @Override
    public void clearFilms() throws IOException {
        if (seances == null) {
            load();
        }
        films.clear();
        save();
    }

    @Override
    public Collection<Seance> getSeances() {
        if (seances == null) {
            load();
        }
        return seances.values();
    }

    @Override
    public void clearSeance() throws IOException {
        if (seances == null) {
            load();
        }
        seances.clear();
        save();
    }
}
