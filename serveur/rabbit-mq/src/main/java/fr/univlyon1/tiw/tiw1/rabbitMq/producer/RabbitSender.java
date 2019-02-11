package fr.univlyon1.tiw.tiw1.rabbitMq.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    private static final Logger LOGGER = Logger.getLogger(RabbitSender.class.getName());
    public void sendToBorne(String message) {
        LOGGER.info("NOTIFICATION START MSG::"+message);
        this.template.convertAndSend("borne", message);
        String msgTest = "JPP IS !!!!";
        LOGGER.info("JPP ++");
        this.template.convertAndSend(queue.getName(), msgTest);
        LOGGER.info("NOTIFICATION END SUCCESSFULLY");
    }
}
