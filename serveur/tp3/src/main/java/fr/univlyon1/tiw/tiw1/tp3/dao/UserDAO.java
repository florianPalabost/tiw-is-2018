package fr.univlyon1.tiw.tiw1.tp3.dao;

import fr.univlyon1.tiw.tiw1.tp3.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User,Long> {
    @Override
    Collection<User> findAll();

    @Override
    <S extends User> S save(S s);

    @Override
    void delete(User user);

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}
