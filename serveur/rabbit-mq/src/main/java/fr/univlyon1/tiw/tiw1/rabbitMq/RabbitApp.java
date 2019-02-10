package fr.univlyon1.tiw.tiw1.rabbitMq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitApp {
    static final String topicExchangeName = "exchange-test";

    // static final String queueName = "cinema-1-11301169";
    public static void main(String[] args) {
        SpringApplication.run(RabbitApp.class, args);
    }
}
