package fr.univlyon1.tiw.tiw1.tp3.service;

import fr.univlyon1.tiw.tiw1.tp3.beans.User;
import fr.univlyon1.tiw.tiw1.tp3.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public Collection<User> findAllUsers() {
        return userDAO.findAll();
    }

    public void register(User user) {
        userDAO.save(user);
    }
}
