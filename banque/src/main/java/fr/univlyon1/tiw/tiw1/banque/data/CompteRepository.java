package fr.univlyon1.tiw.tiw1.banque.data;

import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
import org.springframework.data.repository.Repository;

public interface CompteRepository extends Repository<Compte, Long> {
}
