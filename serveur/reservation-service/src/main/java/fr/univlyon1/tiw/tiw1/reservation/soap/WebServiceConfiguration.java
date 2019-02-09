package fr.univlyon1.tiw.tiw1.reservation.soap;

import fr.univlyon1.tiw.tiw1.reservation.services.ControleurService;
import fr.univlyon1.tiw.tiw1.reservation.services.ServiceReservation;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {

    // Config from http://cxf.apache.org/docs/springboot.html
    @Bean
    public Endpoint endpoint(Bus bus, ServiceReservation reservationService) {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationService);
        endpoint.publish("/reservation");
        return endpoint;
    }
    @Bean
    public Endpoint endpoint2(Bus bus, ControleurService controleurService) {
        EndpointImpl endpoint = new EndpointImpl(bus, controleurService);
        endpoint.publish("/controleur");
        return endpoint;
    }
}
