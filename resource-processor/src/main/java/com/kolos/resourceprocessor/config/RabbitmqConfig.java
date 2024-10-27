package com.kolos.resourceprocessor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue saveMetadateQueue() {
        return new Queue("save_metadata", false);
    }

    @Bean
    public Queue healthCheckQueue() {
        return new Queue("health_check_resource_processor", false);
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
