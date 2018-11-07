package fr.univlyon1.m2tiw.testspringboot.beans;

import org.springframework.stereotype.Component;

@Component
public class HelloBean {
    private final String message = "Hello";
    private String user, greetings;

    public HelloBean() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        this.greetings = this.message + " " + this.user;
    }

    public String getGreetings() {
        return this.greetings;
    }
}