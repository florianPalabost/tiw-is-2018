package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.tp3.beans.User;
import fr.univlyon1.tiw.tiw1.tp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost","http://127.0.0.1:4200","http://127.0.0.1"})
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    // TODO find a way to persist the users created at least
    /**
     *
     * @return list of all users registered in the h2 database
     * @throws IOException
     */
    @GetMapping("/")
    public @ResponseBody Collection<User> retrieveAllUsers() throws IOException {
        return userService.findAllUsers();
    }

    /**
     * save the user in the h2 database
     * @param user
     * @return
     * @throws IOException
     */
    @PostMapping(value="/",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> persistUser(@RequestBody User user) throws IOException {
        userService.register(user);
        return null;
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable String email) throws IOException {
        userService.deleteUser(email);
        return null;
    }
}
