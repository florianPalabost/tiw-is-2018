package fr.univlyon1.tiw.tiw1.borne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BorneApp {
    static final String topicExchangeName = "exchange-test";

    // static final String queueName = "cinema-1-11301169";
    public static void main(String[] args) {
        SpringApplication.run(BorneApp.class, args);
    }

}
