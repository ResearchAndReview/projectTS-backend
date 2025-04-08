package org.researchandreview.projecttsbackend;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = System.getenv("MQ_EXCHANGE_NAME");
    public static final String ROUTING_KEY = System.getenv("MQ_ROUTING_KEY");
    public static final String QUEUE_NAME = System.getenv("MQ_QUEUE_NAME");

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
