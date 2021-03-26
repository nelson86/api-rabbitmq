package com.narabel.rabbitmq.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.List;
import java.util.Map;

public interface ConnectionRabbitMQ {
    Exchange getExchange();
    Map<String, Queue> getQueues(Map<String, Object> arguments );
    List<Binding> getBinding();

    default void setConnection(RabbitAdmin rabbitAdmin, Map<String, Object> arguments) {
        rabbitAdmin.declareExchange( getExchange() );
        getQueues( arguments ).values().forEach(queue -> rabbitAdmin.declareQueue( queue ));
        getBinding().forEach(binding -> rabbitAdmin.declareBinding(binding));
    }

    static void setConnection(RabbitAdmin rabbitAdmin, Map<String, Object> arguments, String exchangeName, String queueName, String routingKeyName) {
        Exchange exchange = ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();

        Queue queue = new Queue(queueName, true, false, false);

        Binding binding = BindingBuilder
                .bind( queue )
                .to( exchange )
                .with( routingKeyName )
                .noargs();

        rabbitAdmin.declareExchange( exchange );
        rabbitAdmin.declareQueue( queue );
        rabbitAdmin.declareBinding(binding);

    }

}
