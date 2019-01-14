package fr.univlyon1.tiw.tiw1.banque;

import fr.univlyon1.tiw.tiw1.banque.service.CompteService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {

    // Config from http://cxf.apache.org/docs/springboot.html
    @Bean
    public Endpoint endpoint(Bus bus, CompteService compteService) {
        EndpointImpl endpoint = new EndpointImpl(bus, compteService);
        endpoint.publish("/compte");
        return endpoint;
    }
}
