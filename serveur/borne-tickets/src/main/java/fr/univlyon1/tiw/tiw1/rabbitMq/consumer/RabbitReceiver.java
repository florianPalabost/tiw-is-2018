package fr.univlyon1.tiw.tiw1.rabbitMq.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.tiw.tiw1.rabbitMq.producer.RabbitSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
public class RabbitReceiver {
    @Autowired
    private ApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(RabbitReceiver.class.getName());

    @RabbitListener(queues = "cinema-11301169")
    public void receiveBorne(String in) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(in);

        try {
            JsonNode prelevementNode = jsonNode.get("prelevement");

            if(prelevementNode.get("statut").asBoolean()) {
                LOGGER.info(prelevementNode.get("compte").asText());
                LOGGER.info(prelevementNode.get("montant").asText());
                LOGGER.info(prelevementNode.get("ref").asText());


                HashMap<String, Object> parametres = new HashMap<>();
                parametres.put("reservationId", prelevementNode.get("ref").asText());
                String url = "http://reservations:8091/reservations/"+prelevementNode.get("ref").asText();
                LOGGER.info("URL in RECEIVE::"+url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.put(url, Object.class);

                    // context.getBean().process("setPaye", parametres);

                String json = "{ \"paye\": { \"statut\": true, \"compte\": "+prelevementNode.get("compte").asText()+", \"montant\": "+prelevementNode.get("montant").asText()+", \"ref\": "+prelevementNode.get("ref")+" } }";
                context.getBean(RabbitSender.class).sendToBorne(json);
            } else {
                String json = "{ \"paye\": { \"statut\": false } }";
                LOGGER.info("Cinema : Le paiement n'a pas abouti selon Banque");
            }
        } catch (Exception e) {
            String json = "{ \"paye\": { \"statut\": false } }";
            LOGGER.info("Cinema : Le format du message n'est pas prise enn charge");
        }
    }
}
