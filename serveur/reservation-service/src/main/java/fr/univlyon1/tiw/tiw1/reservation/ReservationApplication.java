package fr.univlyon1.tiw.tiw1.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "fr.univlyon1.tiw.tiw1.metier.dao")
public class ReservationApplication {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class, args);
    }

}

