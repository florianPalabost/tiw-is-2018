package fr.univlyon1.m2tiw.tiw1.spring;

import fr.univlyon1.m2tiw.tiw1.spring.soap.ReservationServiceBean;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {

    // Config from http://cxf.apache.org/docs/springboot.html
    @Bean
    public Endpoint endpoint(Bus bus, ReservationServiceBean reservationService) {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationService);
        endpoint.publish("/reservation");
        return endpoint;
    }
}
