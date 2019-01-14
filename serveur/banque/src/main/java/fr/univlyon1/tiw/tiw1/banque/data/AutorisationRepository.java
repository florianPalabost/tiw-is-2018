package fr.univlyon1.tiw.tiw1.banque.data;

import fr.univlyon1.tiw.tiw1.banque.metier.Autorisation;
import org.springframework.data.repository.CrudRepository;

public interface AutorisationRepository extends CrudRepository<Autorisation, Long> {
}
