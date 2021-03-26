package com.narabel.rabbitmq.consumer.config;

import org.springframework.amqp.core.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionOffice implements ConnectionRabbitMQ {
    private static final String EXCHANGE = "intermediarioOficina";

    private static final String QUEUE1 = "cola-oficina-1";
    private static final String QUEUE2 = "cola-oficina-2";

    private static final String ROUTING_KEY1 = "routingkey-oficina1";

    private Map<String, Queue> queues = new HashMap<>();

    @Override
    public Exchange getExchange() {
        return ExchangeBuilder
                .directExchange(EXCHANGE)
                .durable(true)
                .build();
    }

    @Override
    public Map<String, Queue> getQueues(Map<String, Object> arguments) {
        Queue queues1 = QueueBuilder
                .durable(QUEUE1)
                //.withArguments(arguments)
                .build();

        Queue queues2 = QueueBuilder
                .durable(QUEUE2)
                //.withArguments(arguments)
                .build();

        queues.put(QUEUE1, queues1);
        queues.put(QUEUE2, queues2);

        return queues;
    }

    @Override
    public List<Binding> getBinding() {
        Binding binding = BindingBuilder
                .bind(queues.get(QUEUE1))
                .to(getExchange())
                .with(ROUTING_KEY1)
                .noargs();

        Binding binding2 = BindingBuilder
                .bind(queues.get(QUEUE2))
                .to(getExchange())
                .with(ROUTING_KEY1)
                .noargs();

        return Arrays.asList(binding, binding2);
    }
}
