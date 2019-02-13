package fr.univlyon1.tiw.tiw1.borne.configuration;

import fr.univlyon1.tiw.tiw1.borne.consumer.RabbitReceiver;
import fr.univlyon1.tiw.tiw1.borne.producer.RabbitSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("rabbitmq",5672);
    }
    @Bean
    public Queue cinema() {
        return new Queue("cinema-11301169");
    }

    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }

    @Bean
    public RabbitSender sender() {
        return new RabbitSender();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("test-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("http://banque:8082");
    }
}
