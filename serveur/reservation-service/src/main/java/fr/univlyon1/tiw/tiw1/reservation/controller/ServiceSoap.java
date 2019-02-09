package fr.univlyon1.tiw.tiw1.reservation.controller;

import fr.univ_lyon1.tiw.tiw1.cinema.reservation.AnnulerReservation;
import fr.univ_lyon1.tiw.tiw1.cinema.reservation.ReservationInconnue_Exception;
import fr.univlyon1.tiw.tiw1.reservation.metier.Reservation;
import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
public class ServiceSoap {
    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected ServiceReservation serviceReservation;

    private static final Logger LOGGER = Logger.getLogger(ServiceSoap.class.getName());

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/xml")
    @ResponseBody
    public void controleSoap(@RequestBody String source) {
        /*System.out.println(source);
        System.exit(0);*/
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

        /*DOMResult dom = new DOMResult();
        Transformer trans = null;

        try {
            trans = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            trans.transform(source, dom);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("je suis dans controleurprincipal");
        Node node = dom.getNode();
//        Node root = node.getFirstChild().getNextSibling();

//        System.out.println("nom"+root.getNodeName());
        System.out.println(node.getFirstChild().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNodeName());

        context.getBean(ControleurService.class).invoke(source);*/
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
        //LOGGER.info(reservation.toString());
        AnnulerReservation annulerReservation = new AnnulerReservation();
        annulerReservation.setId(doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());
        serviceReservation.annuler(annulerReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/reserver",method = RequestMethod.POST, headers = "Accept=application/xml")
    @ResponseBody
    public ResponseEntity<Reservation> recordReservation(@RequestBody String source) throws ReservationInconnue_Exception, IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(source)));
        LOGGER.info(":::::::::ADD RESERVATION::::::");
        LOGGER.info("ID RESERVATION ::::"+doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());
        //LOGGER.info(reservation.toString());
        // AnnulerReservation annulerReservation = new AnnulerReservation();
        // annulerReservation.setId(doc.getElementsByTagName("soapenv:Body").item(0).getChildNodes().item(1).getChildNodes().item(1).getFirstChild().getTextContent());


        // serviceReservation.annuler(annulerReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
