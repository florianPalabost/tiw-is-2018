package fr.univlyon1.m2tiw.tiw1.spring.configuration;

import fr.univlyon1.m2tiw.tiw1.spring.rabbitMq.RabbitReceiver;
import fr.univlyon1.m2tiw.tiw1.spring.rabbitMq.RabbitSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue cinema() {
        return new Queue("cinema");
    }

    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }

    @Bean
    public RabbitSender sender() {
        return new RabbitSender();
    }
}
