package fr.univlyon1.tiw.tiw1.borne.producer;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RabbitSender {
    private static final String queueName = "cinema-1-11301169";

    private static final Logger LOGGER = Logger.getLogger(RabbitSender.class.getName());
    public void sendToBorne(String message) {
//        LOGGER.info("NOTIFICATION START MSG from borne_1 ::"+message);
//        this.template.convertAndSend("borne", message);
//        String msgTest = "JPP IS !!!!";
//        LOGGER.info("JPP ++");
//        this.template.convertAndSend(queue.getName(), msgTest);
//        LOGGER.info("NOTIFICATION END SUCCESSFULLY");
    }
}
