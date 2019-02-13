package fr.univlyon1.tiw.tiw1.borne.controller;


import fr.univlyon1.tiw.tiw1.borne.configuration.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.xml.soap.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import static org.apache.cxf.common.jaxb.JAXBUtils.LOG;

@Controller
public class BorneReservationController {

    private static final Logger LOGGER = Logger.getLogger(BorneReservationController.class.getName());


    @GetMapping("/borne/reserverPage")
    public String showPageBorne(Map<String, Object> model, Reservation reservation) throws Exception {
        LOGGER.info("controler borne show page reserv");
            model.put("reservation",reservation);
            return "reservation";
    }

    @PostMapping(value="/borne/reserver")
    public String saveReservationFromBorne(@Valid Reservation reservation, BindingResult result, Model model) throws IOException, SOAPException {
        if (result.hasErrors()) {
            return "reservation";
        }
        LOGGER.info("info from form : "+reservation.toString());
        LOGGER.info("COntroller save Reservation form borne...");

        // COnstruction d'un msg soap
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMsg = factory.createMessage();
        SOAPPart part = soapMsg.getSOAPPart();

        SOAPEnvelope envelope = part.getEnvelope();
        SOAPHeader header = envelope.getHeader();
        SOAPBody body = envelope.getBody();

        header.addTextNode("Reservation from borne");

        SOAPBodyElement element = body.addBodyElement(envelope.createName("reserver", "res",null));
        element.addChildElement("prenom").addTextNode(reservation.getPrenom());
        element.addChildElement("nom").addTextNode(reservation.getNom());
        element.addChildElement("email").addTextNode(reservation.getEmail());
        element.addChildElement("seance").addTextNode(reservation.getSeanceId());

        LOGGER.info("Message SOAP::::"+body.getTextContent());
        // LOGGER.info("Message SOAP::::"+body.toString());


        // TODO appeler reservationService soap pour reserver
        /* RestTemplate restTemplate = new RestTemplate();
        String ressUrl = "http://reservations:8091/reservations";
        Collection<Object> res = restTemplate.getForObject(ressUrl, Collection.class); */

        HttpURLConnection postConnection = null;
                URL obj = null;
                obj = new URL("http://reservations:8091/services/reservation");
                postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("POST");
                postConnection.setRequestProperty("Content-Type", "text/xml");

               /* obj = new URL("http://localhost:8091/reservations/" + reservationId + "/delete");
                postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("DELETE"); */

        final String POST_PARAMS =
                       "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \"xmlns:res=\"http://www.univ-lyon1.fr/tiw/tiw1/cinema/reservation\">"+
                            "\t<soapenv:Header/>"+
                            "\t<soapenv:Body>"+
                                "\t\t<res:reserver>"+
                                   "\t\t\t<res:prenom>"+reservation.getPrenom()+"</res:prenom>"+
                                    "\t\t\t<res:nom>"+reservation.getNom()+"</res:nom>"+
                                   "\t\t\t<res:email>"+reservation.getEmail()+"</res:email>"+
                                    "\t\t\t<res:seance>"+reservation.getSeanceId()+"</res:seance>"+
                               "\t\t</res:reserver>"+
                            "\t</soapenv:Body>"+
                        "</soapenv:Envelope>";
        LOG.info("POST_PARAMS : "+ POST_PARAMS);
                postConnection.setDoOutput(true);
                OutputStream os = postConnection.getOutputStream();
                os.write(POST_PARAMS.getBytes());
                os.flush();
                os.close();
                int responseCode = postConnection.getResponseCode();

               // LOG.info("Reservation: " + reservationId + " success: " + success);

                LOG.info("POST BORNE Response Code :  " + responseCode);

                LOG.info("POST BORNE Response Message : " + postConnection.getResponseMessage());

                LOG.info("POST BORNE Response Message : " + POST_PARAMS);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success

                    BufferedReader in = new BufferedReader(new InputStreamReader(

                            postConnection.getInputStream()));

                    String inputLine;

                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in .readLine()) != null) {

                        response.append(inputLine);

                    } in .close();

                    // print result

                   LOG.info(response.toString());

                } else {

                    LOG.info("POST NOT WORKED");

                }



        // reponse reservation
        // next call banque pour prelever user
        // reponse banque -> borne et banque -> reservation
        // rep reservation maj -> borne


        return "index";
    }
}
