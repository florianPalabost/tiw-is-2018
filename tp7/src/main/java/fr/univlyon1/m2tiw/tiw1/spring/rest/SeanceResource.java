package fr.univlyon1.m2tiw.tiw1.spring.rest;

import fr.univlyon1.m2tiw.tiw1.metier.Seance;
import fr.univlyon1.m2tiw.tiw1.metier.dao.ProgrammationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SeanceResource {

    @Autowired
    private ProgrammationDAO programmationDAO;

    @RequestMapping("/seance")
    public Collection<Seance> getSeances() {
        return programmationDAO.getSeances();
    }

}
