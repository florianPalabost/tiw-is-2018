//package fr.univlyon1.tiw.tiw1.borne;
//
//import fr.univlyon1.tiw.tiw1.banque.service.CompteService;
//import fr.univlyon1.tiw.tiw1.reservation.services.LogicalMessageHandler;
//import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
//import fr.univlyon1.tiw.tiw1.reservation.services.SoapMessageHandler;
//import org.apache.cxf.Bus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.xml.ws.Endpoint;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class WebServiceConfiguration {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    // Config from http://cxf.apache.org/docs/springboot.html
//    @Bean
//    public Endpoint endpoint(Bus bus, ServiceReservation reservationService) {
//        List<Handler> h = new ArrayList<>();
//        h.add(new SoapMessageHandler());
//        h.add(new LogicalMessageHandler());
//        EndpointImpl endpoint = new EndpointImpl(bus, reservationService);
//        endpoint.setHandlers(h);
//        endpoint.publish("/reservation");
//        return endpoint;
//    }
//}
