package com.narabel.rabbitmq.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;
/*
    @Value("${spring.rabbitmq.deadLetter.queue}")
    private String dlQueue;

    @Value("${spring.rabbitmq.deadLetter.exchange}")
    private String dlExchange;

    @Value("${spring.rabbitmq.deadLetter.routingkey}")
    private String dlRoutingkey;
*/
    @Autowired
    ManagerConnectionRabbitMQ managerConnectionRabbitMQ;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
/*
    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(dlExchange);
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(dlQueue).build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(dlRoutingkey);
    }
*/
    @Bean
    public RabbitAdmin rabbitAdmin() {

        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup (true); // Activa el inicio automático cuando se inicia el servicio
/*
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", dlExchange);
        arguments.put("x-dead-letter-routing-key", dlRoutingkey);
*/
        this.managerConnectionRabbitMQ.setConnection(rabbitAdmin, null);

        return rabbitAdmin;
    }

}
