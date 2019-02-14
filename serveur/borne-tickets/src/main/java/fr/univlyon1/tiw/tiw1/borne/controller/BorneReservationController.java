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


        LOG.info("borne_reserver");
        // TODO appeler reservation Service soap pour reserver


        HttpURLConnection postConnection = null;
                URL obj = null;
                obj = new URL("http://reservations:8091/controleur/reserver");
         String SOAPAction = "http://localhost:8091/services/reservation";
                postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("POST");
                postConnection.setRequestProperty("Content-Type", "text/xml");
                postConnection.setRequestProperty("api-key", "key-api-2");
                postConnection.setRequestProperty("SOAPAction", "http://localhost:8091/services/reservation");

                // postConnection = (HttpURLConnection) obj.openConnection();
                //postConnection.setRequestMethod("OPTIONS");

        final String POST_PARAMS =
                       "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:res=\"http://www.univ-lyon1.fr/tiw/tiw1/cinema/reservation\">"+
                            "\n\t<soapenv:Header/>"+
                            "\n\t<soapenv:Body>"+
                                "\n\t\t<res:reserver>"+
                                   "\n\t\t\t<res:prenom>"+reservation.getPrenom()+"</res:prenom>"+
                                    "\n\t\t\t<res:nom>"+reservation.getNom()+"</res:nom>"+
                                   "\n\t\t\t<res:email>"+reservation.getEmail()+"</res:email>"+
                                    "\n\t\t\t<res:seance>"+reservation.getSeanceId()+"</res:seance>"+
                               "\n\t\t</res:reserver>"+
                            "\n\t</soapenv:Body>"+
                        "\n</soapenv:Envelope>";
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
                    model.addAttribute("succes_reservation", true);

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
                    model.addAttribute("succes_reservation",false);
                    LOG.info("POST NOT WORKED");

                }

        // reponse reservation -> ok


        // next call banque pour prelever user

        // on met le compte en dur pour le test, normalement il faudrait rediriger l'utilisateur vers un autre formulaire
        // pour son numero de compte ou vers une vrai api de paiement
//        postConnection.disconnect();
//
//        HttpURLConnection postConnection1 = null;
//
//        obj = new URL("http://localhost:8082/services/compte");
//        SOAPAction = "http://localhost:8082/services/compte";
//        postConnection1 = (HttpURLConnection) obj.openConnection();
//        postConnection1.setRequestMethod("POST");
//        postConnection1.setRequestProperty("Content-Type", "text/xml");
//        postConnection1.setRequestProperty("SOAPAction", "http://localhost:8082/services/compte");
//
//
//        final String POST_PARAMSS =
//                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:res=\"http://www.univ-lyon1.fr/tiw/tiw1/cinema/reservation\">"+
//                        "\n\t<soapenv:Header/>"+
//                        "\n\t<soapenv:Body>"+
//                        "\n\t\t<com:prelevement>"+
//                        "\n\t\t\t<source>123456789</source>"+
//                        "\n\t\t\t<destinataire>234567890</destinataire>"+
//                        "\n\t\t\t<montant>10.0</montant>"+
//                        // "\n\t\t\t<ref>"+re+"</ref>"+
//                        "\n\t\t</com:prelevement>"+
//                        "\n\t</soapenv:Body>"+
//                        "\n</soapenv:Envelope>";
//        LOG.info("POST_PARAMSS : "+ POST_PARAMSS);
//        postConnection1.setDoOutput(true);
//        os = postConnection1.getOutputStream();
//        os.write(POST_PARAMSS.getBytes());
//        os.flush();
//        os.close();
//
//        responseCode = postConnection1.getResponseCode();
//
//        // LOG.info("Reservation: " + reservationId + " success: " + success);
//
//        LOG.info("POST PRELEV BORNE Response Code :  " + responseCode);
//
//        LOG.info("POST PRELEV BORNE Response Message : " + postConnection1.getResponseMessage());
//
//        LOG.info("POST PRELEV BORNE Response Message : " + POST_PARAMSS);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) { //success
//            model.addAttribute("succes_prelevement", true);
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//
//                    postConnection1.getInputStream()));
//
//            String inputLine;
//
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in .readLine()) != null) {
//
//                response.append(inputLine);
//
//            } in .close();
//
//            // print result
//
//            LOG.info(response.toString());
//
//        } else {
//            model.addAttribute("succes_prelevement",false);
//            LOG.info("POST 2 NOT WORKED");
//
//        }

        // reponse banque -> borne et banque -> reservation
        // rep reservation maj -> borne


        return "index";
    }
}
