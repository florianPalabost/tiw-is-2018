package fr.univlyon1.tiw.tiw1.reservation.security;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate template;

    private static final Logger LOGGER = Logger.getLogger(RabbitSender.class.getName());
    public void sendToBorne(String message) {
        LOGGER.info("NOTIFICATION START MSG::"+message);
        this.template.convertAndSend("borne", message);
        LOGGER.info("NOTIFICATION END SUCCESSFULLY");
    }
}
