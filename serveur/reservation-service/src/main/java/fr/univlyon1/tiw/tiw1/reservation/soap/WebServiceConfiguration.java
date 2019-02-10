package fr.univlyon1.tiw.tiw1.reservation.soap;

import fr.univlyon1.tiw.tiw1.reservation.services.ControleurService;
import fr.univlyon1.tiw.tiw1.reservation.services.LogicalMessageHandler;
import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
import fr.univlyon1.tiw.tiw1.reservation.services.SoapMessageHandler;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebServiceConfiguration {

    // Config from http://cxf.apache.org/docs/springboot.html
    @Bean
    public Endpoint endpoint(Bus bus, ServiceReservation reservationService) {
        List<Handler> h = new ArrayList<>();
        h.add(new SoapMessageHandler());
        h.add(new LogicalMessageHandler());
        EndpointImpl endpoint = new EndpointImpl(bus, reservationService);
        endpoint.setHandlers(h);
        endpoint.publish("/reservation");
        return endpoint;
    }
    @Bean
    public Endpoint endpoint2(Bus bus, ControleurService controleurService) {
        List<Handler> h = new ArrayList<>();
        h.add(new SoapMessageHandler());
        h.add(new LogicalMessageHandler());

        EndpointImpl endpoint = new EndpointImpl(bus, controleurService);
        endpoint.setHandlers(h);
        endpoint.publish("/controleur");
        return endpoint;
    }
}
