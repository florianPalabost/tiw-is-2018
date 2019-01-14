package fr.univlyon1.m2tiw.tiw1.metier.dao.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Cinema;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Film;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Salle;
import fr.univlyon1.m2tiw.tiw1.metier.beans.Seance;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.FilmDTO;
import fr.univlyon1.m2tiw.tiw1.metier.jsondto.SeanceDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static fr.univlyon1.m2tiw.tiw1.metier.jsondto.CinemaDTO.DATE_PARSER;

@Component
public class JSONProgrammationDAO implements ProgrammationDAO {

    public static File SEANCES_JSON = new File("seances.json");
    public static File FILMS_JSON = new File("films.json");
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static JavaType list_of_seances_type =
            TypeFactory.defaultInstance().constructCollectionType(
                    Collection.class, SeanceDTO.class);
    private static JavaType list_of_films_type =
            TypeFactory.defaultInstance().constructCollectionType(
            Collection.class, FilmDTO.class);

    private List<Film> films = null;
    private Map<String, Seance> seances = null;
    private Map<String, Salle> salles;
    private JSONSalleDAO salleDAO = new JSONSalleDAO();
    /**
     *
     * JSONProgrammationDAO .

     *
     * @throws IOException Exception IO
     * @throws ParseException Exception de parse
     */
    public JSONProgrammationDAO() throws IOException, ParseException {
        setSalles(salleDAO.load());
        load("mon-cinema");
    }

    /**
     *
     * Setter de salles .
     *
     * @param salles .
     *
     */
    public void setSalles(Collection<Salle> salles) {
        this.salles = new HashMap<>();
        for (Salle s : salles) {
            this.salles.put(s.getNom(), s);
        }
    }

    private void load(String nom) throws IOException, ParseException {
        films = new ArrayList<>();
        seances = new HashMap<>();
        FILMS_JSON = new File(nom + "_films.json");
        SEANCES_JSON = new File(nom + "_seances.json");
        if (FILMS_JSON.exists()) {
            Collection<FilmDTO> filmDTOs = mapper.readValue(FILMS_JSON, list_of_films_type);
            films.addAll(filmDTOs.stream().map(FilmDTO::asFilm).collect(Collectors.toList()));
            if (SEANCES_JSON.exists()) {
                Collection<SeanceDTO> seanceDTOs = mapper.readValue(
                        SEANCES_JSON, list_of_seances_type);
                for (SeanceDTO dto : seanceDTOs) {
                    Seance s = new Seance(getFilmById(dto.film), salles.get(dto.salle),
                            DATE_PARSER.parse(dto.date), dto.prix);
                    seances.put(s.getId(), s);
                }
            }      
        }
    }

    @Override
    public void initData(Cinema cinema) throws Exception {
        films = new ArrayList<>();
        films.addAll((Collection<Film>) cinema.process("FILM", "getFilms",null));
        seances = new HashMap<>();
        for (Seance s :  (Collection<Seance>)cinema.process("SEANCE", "getSeances", null)) {
            seances.put(s.getId(), s);
        }
        save();
    }

    @Override
    public Seance getSeanceById(String id) {
        return seances.get(id);
    }

    @Override
    public Film getFilmByTitreVersion(String titre, String version) {
        for (Film f : films) {
            if (f.getTitre().equals(titre) && f.getVersion().equals(version)) {
                return f;
            }
        }
        return null;
    }

    private Film getFilmById(String id) {
        for (Film f : films) {
            if ((f.getTitre() + " - " + f.getVersion()).equals(id)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public Collection<Seance> getSeanceByFilm(Film film) {
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
        seances.put(seance.getId(), seance);
        save();
    }

    private void save() throws IOException {
        Collection<SeanceDTO> seanceDTOs =
                seances.values().stream().map(SeanceDTO::fromSeance).collect(Collectors.toList());
        mapper.writeValue(SEANCES_JSON, seanceDTOs);
        Collection<FilmDTO> filmDTOs = films.stream().map(
                FilmDTO::fromFilm).collect(Collectors.toList());
        mapper.writeValue(FILMS_JSON, filmDTOs);
    }

    @Override
    public void save(Film film) throws IOException {
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
        seances.remove(seance);
        save();
    }
    /*
        @Override
        public String toString() {
            return "{" + "films=" + films + ", seances=" + seances + ", salles=" + salles + '}';
        }
    */

    public Map<String, Film> getFilms() {
        return films.stream().collect(Collectors.toMap(Film::getKey, item -> item));
    }

    public Collection<Seance> getSeances() {
        return seances.values();
    }

    public Map<String, Salle> getSalles() {
        return salles;
    }


    public void delete(Film f) throws IOException {
        films.remove(f);
        save();
    }
}
