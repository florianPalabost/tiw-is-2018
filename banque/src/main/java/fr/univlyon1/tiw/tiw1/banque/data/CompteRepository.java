package fr.univlyon1.tiw.tiw1.banque.data;

import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompteRepository extends CrudRepository<Compte, Long> {

    default Compte findByIdOrFail(Long idCompte) throws CompteInconnuException {
        Optional<Compte> result = findById(idCompte);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new CompteInconnuException(idCompte);
        }
    }

}
