package fr.univlyon1.tiw.tiw1.banque;

import fr.univlyon1.tiw.tiw1.banque.service.CompteService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebServiceConfiguration {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    // Config from http://cxf.apache.org/docs/springboot.html
    @Bean
    public Endpoint endpoint(Bus bus, CompteService compteService) {
        List<Handler> h = new ArrayList<>();
        h.add(new BanqueEventHandler(rabbitTemplate, new Queue("cinema-11301169")));
        EndpointImpl endpoint = new EndpointImpl(bus, compteService);
        endpoint.setHandlers(h);
        endpoint.publish("/compte");
        return endpoint;
    }

    @Bean
    public Queue candidateQueue() {
        return new Queue("cinema-11301169");
    }
}
