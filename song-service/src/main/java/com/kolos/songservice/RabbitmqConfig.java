package com.kolos.songservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${queue.delete.ids}")
    private String queueDeleteIds;

    @Value("${queue.health.check}")
    private String queueHealthCheck;

    @Value("${topic.exchange.health.check}")
    private String topicHealthCheck;

    @Value("${routing.key.health.check}")
    private String routingKeyCommunicationHealthCheck;



    @Bean
    public Queue deleteIds() {
        return new Queue(queueDeleteIds, false);
    }

    @Bean
    public Queue healthCheckQueue() {
        return new Queue(queueHealthCheck, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicHealthCheck);
    }

    @Bean
    public Binding bindingHealthCheck(Queue healthCheckQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(healthCheckQueue)
                .to(topicExchange)
                .with(routingKeyCommunicationHealthCheck);
    }
}
