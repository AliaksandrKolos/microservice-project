package com.kolos.songservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue deleteIds() {
        return new Queue("deleteIds", false);
    }

    @Bean
    public Queue healthCheckQueue() {
        return new Queue("health_check_song_service", false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("healthCheckTopic");
    }

    @Bean
    public Binding bindingHealthCheck(Queue healthCheckQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(healthCheckQueue)
                .to(topicExchange)
                .with("communicationHealthCheck");
    }
}
