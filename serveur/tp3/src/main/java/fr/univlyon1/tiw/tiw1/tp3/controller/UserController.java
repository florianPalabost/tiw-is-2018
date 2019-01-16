package fr.univlyon1.tiw.tiw1.tp3.controller;

import fr.univlyon1.tiw.tiw1.tp3.beans.User;
import fr.univlyon1.tiw.tiw1.tp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public @ResponseBody Collection<User> retrieveAllUsers() throws IOException {
        return userService.findAllUsers();
    }

    @PostMapping(value="/",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> persistUser(@RequestBody User user) throws IOException {
        userService.register(user);
        return null;
    }
}
