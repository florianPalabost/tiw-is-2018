package fr.univlyon1.tiw.tiw1.reservation.controller;

import fr.univ_lyon1.tiw.tiw1.cinema.reservation.*;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/controleur")
@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
public class ReservationController {

    // TODO VERIF LES METHODES ENTRE LES 2 CONTROLEURS


    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected ServiceReservation serviceReservation;

    private static final Logger LOGGER = Logger.getLogger(ReservationController.class.getName());

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/xml")
    @ResponseBody
    public void controleSoap(@RequestBody String source) {
        LOGGER.info("::::::::::::CONTROLEUR SOAP:::::::::::");
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(source)));
            LOGGER.info(doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());
//            context.getBean(ControleurService.class).invoke(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/cancel",method = RequestMethod.POST, headers = "Accept=application/xml")
    @ResponseBody
    public ResponseEntity<Reservation> cancelReserv(@RequestBody String source) throws ReservationInconnue_Exception, IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(source)));
        LOGGER.info(":::::::::CANCEL RESERVATION::::::");
        LOGGER.info("ID RESERVATION ::::"+doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());
		
        AnnulerReservation annulerReservation = new AnnulerReservation();
        annulerReservation.setId(doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());
        serviceReservation.annuler(annulerReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/reserver",method = RequestMethod.POST, headers = "Accept=application/xml")
    @ResponseBody
    public ResponseEntity<Reservation> recordReservation(@RequestBody String source) throws ReservationInconnue_Exception, IOException, SAXException, ParserConfigurationException, SeanceComplete_Exception, SeanceInconnue_Exception {
        LOGGER.info("ping");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(source)));

        LOGGER.info(":::::::::ADD RESERVATION::::::");
        LOGGER.info("Prenom RESERVATION ::::"+doc.getElementsByTagName("soapenv:Body")
                .item(0).getChildNodes()// res:reserver
                .item(1).getChildNodes()
                .item(1).getFirstChild().getTextContent());
        LOGGER.info("nom RESERVATION ::::"+doc.getElementsByTagName("res:nom").item(0).getFirstChild().getTextContent());
        LOGGER.info("email RESERVATION ::::"+doc.getElementsByTagName("res:email").item(0).getFirstChild().getTextContent());
        LOGGER.info("seanceid RESERVATION ::::"+doc.getElementsByTagName("res:seance").item(0).getFirstChild().getTextContent());
       
	   
        serviceReservation.reserver(doc.getElementsByTagName("soapenv:Body")
                .item(0).getChildNodes()// res:reserver
                .item(1).getChildNodes()
                .item(1).getFirstChild().getTextContent(),
                doc.getElementsByTagName("res:nom").item(0).getFirstChild().getTextContent(),
                doc.getElementsByTagName("res:email").item(0).getFirstChild().getTextContent(),
                doc.getElementsByTagName("res:seance").item(0).getFirstChild().getTextContent()
                );

        // appeler Banque
        LOGGER.info("La reservation a ete sauvegardee !");

        // Send mSg
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/reservations/updatePaye",headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> updateReservationPaye(@RequestBody Reservation reservation, BindingResult result) {
        LOGGER.info("Update Paye Reservation controller  .... Reservation id n°"+reservation.getId());
        // reservationService.updateReservationById(idR,);
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        serviceReservation.updateReservationPaye(reservation.getId(),true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
