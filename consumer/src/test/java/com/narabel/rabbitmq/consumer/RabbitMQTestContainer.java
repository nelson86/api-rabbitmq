package com.narabel.rabbitmq.consumer;

import lombok.extern.log4j.Log4j2;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Log4j2
@SpringBootTest(classes = Service.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = RabbitMQTestContainer.Initializer.class)
@Testcontainers
public class RabbitMQTestContainer {

    private static final String DOCKER_IMAGE_NAME = "rabbitmq:3-management";

    @ClassRule
    public static final RabbitMQContainer rabbitMqContainer =
            new RabbitMQContainer(DOCKER_IMAGE_NAME)
                    .withAdminPassword("spring");

    @Test
    public void prueba() {
        Assertions.assertTrue(rabbitMqContainer.isRunning());
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@SuppressWarnings("NullableProblems") ConfigurableApplicationContext configurableApplicationContext) {

            rabbitMqContainer.start();

            String host = rabbitMqContainer.getHost();
            log.info("spring.rabbitmq.host={}", host);
            System.setProperty("spring.rabbitmq.host", host);

            Integer amqpPort = rabbitMqContainer.getAmqpPort();
            log.info("spring.rabbitmq.port={}", amqpPort);
            System.setProperty("spring.rabbitmq.port", String.valueOf(amqpPort));

            String adminUsername = rabbitMqContainer.getAdminUsername();
            log.info("spring.rabbitmq.username={}", adminUsername);
            System.setProperty("spring.rabbitmq.username", adminUsername);

            String adminPassword = rabbitMqContainer.getAdminPassword();
            log.info("spring.rabbitmq.password={}", adminPassword);
            System.setProperty("spring.rabbitmq.password", adminPassword);
        }
    }
}
