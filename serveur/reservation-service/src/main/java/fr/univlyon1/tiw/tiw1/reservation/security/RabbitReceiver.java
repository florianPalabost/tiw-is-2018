package fr.univlyon1.tiw.tiw1.reservation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ_lyon1.tiw.tiw1.cinema.reservation.AnnulerReservation;
import fr.univ_lyon1.tiw.tiw1.cinema.reservation.ReservationInconnue_Exception;
import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Component
public class RabbitReceiver {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private ServiceReservation serviceReservation;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(RabbitReceiver.class.getName());

    @RabbitListener(queues = "cinema-11301169")
    public void receiveBorne(byte[] msg) throws IOException {
        String message = new String(msg, StandardCharsets.UTF_8);
        // JsonNode jsonNode = objectMapper.readTree(in);
        LOGGER.info("RECEIVE MESSAGE START.........");
        LOGGER.info("msg recu::::"+message);
        JSONArray array = null;
        try {
            array = new JSONArray(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Long reservationId = null;
        try {
            reservationId = array.getJSONObject(0).getLong("ref");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        boolean success = false;
        try {
            success = array.getJSONObject(0).getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        boolean paye = success;

        final String POST_PARAMS =
                "{\n" +
                        "\t\"id\": \"" + reservationId + "\",\n" +
                        "\t\"paye\": \"" + paye + "\"\n" +
                        "}\n";
            LOGGER.info(POST_PARAMS);
//        Reservation reservation = serviceReservation.retrieveReservationByRId(reservationId);
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://reservations:8091/reservations/updatePaye";
//        restTemplate.postForLocation(url, Reservation.class);

        if(paye) {
            LOGGER.info("MAJ boolean paye de la reservation : "+reservationId);
            serviceReservation.updateReservationPaye(reservationId, paye);
            LOGGER.info("La maj de la reservation de ref : "+reservationId+" a bien ete faite");
        }
        else {
            LOGGER.info("PAIEMENT REFUSE ANNULATION DE LA RESERVATION");
            AnnulerReservation annulerReservation = new AnnulerReservation();
            annulerReservation.setId(String.valueOf(reservationId));
            try {
                serviceReservation.annuler(annulerReservation);
                LOGGER.info("Reservation de reference : "+reservationId+" annulee.");
            } catch (ReservationInconnue_Exception e) {
                e.printStackTrace();
            }
        }

//        try {
//                LOGGER.info("hello from receiveBORN");
//            }
//        catch (Exception e) {
//            String json = "{ \"paye\": { \"statut\": false } }";
//            LOGGER.info("Cinema : Le format du message n'est pas prise enn charge");
//        }
    }
}
