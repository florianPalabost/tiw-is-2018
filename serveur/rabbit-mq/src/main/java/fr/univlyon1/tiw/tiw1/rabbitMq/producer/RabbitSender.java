package fr.univlyon1.m2tiw.tiw1.spring.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitSender {
    @Autowired
    private RabbitTemplate template;

    private static final Logger LOGGER = Logger.getLogger(RabbitSender.class.getName());
    public void sendToBorne(String message) {
        this.template.convertAndSend("borne", message);
    }
}
