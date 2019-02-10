package fr.univlyon1.m2tiw.tiw1.rabbitMq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.tiw1.metier.cinemaRessource.CinemaRessourceReservations;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;

public class RabbitReceiver {
    @Autowired
    private ApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "cinema")
    public void receiveBorne(String in) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(in);

        try {
            JsonNode prelevementNode = jsonNode.get("prelevement");

            if(prelevementNode.get("statut").asBoolean()) {
                System.out.println(prelevementNode.get("compte").asText());
                System.out.println(prelevementNode.get("montant").asText());
                System.out.println(prelevementNode.get("ref").asText());

                HashMap<String, Object> parametres = new HashMap<>();
                parametres.put("reservationId", prelevementNode.get("ref").asText());
                context.getBean(CinemaRessourceReservations.class).process("setPaye", parametres);

                String json = "{ \"paye\": { \"statut\": true, \"compte\": "+prelevementNode.get("compte").asText()+", \"montant\": "+prelevementNode.get("montant").asText()+", \"ref\": "+prelevementNode.get("ref")+" } }";
                context.getBean(RabbitSender.class).sendToBorne(json);
            } else {
                String json = "{ \"paye\": { \"statut\": false } }";
                System.out.println("Cinema : Le paiement n'a pas abouti selon Banque");
            }
        } catch (Exception e) {
            String json = "{ \"paye\": { \"statut\": false } }";
            System.out.println("Cinema : Le format du message n'est pas prise enn charge");
        }
    }
}
