package fr.univlyon1.tiw.tiw1.reservation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class RabbitReceiver {
    @Autowired
    private ApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(RabbitReceiver.class.getName());

    @RabbitListener(queues = "cinema-11301169")
    public void receiveBorne(String in) throws IOException {
        // JsonNode jsonNode = objectMapper.readTree(in);
        LOGGER.info("RECEIVE MESSAGE START.........");
        LOGGER.info("msg recu::::"+in);
        try {
                LOGGER.info("hello");
            }
        catch (Exception e) {
            String json = "{ \"paye\": { \"statut\": false } }";
            LOGGER.info("Cinema : Le format du message n'est pas prise enn charge");
        }
    }
}
