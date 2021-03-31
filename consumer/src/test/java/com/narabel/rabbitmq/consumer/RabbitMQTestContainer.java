package com.narabel.rabbitmq.consumer;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;

public class RabbitMQTestContainer {

    static final RabbitMQContainer RABBIT_MQ_CONTAINER;
    static {
        RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3");
        RABBIT_MQ_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setRabbitProps(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", RABBIT_MQ_CONTAINER::getHost);
        registry.add("spring.rabbitmq.port", RABBIT_MQ_CONTAINER::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);
    }

    @Test
    public void isRunning() {
        Assertions.assertTrue(RABBIT_MQ_CONTAINER.isRunning());
    }
}
