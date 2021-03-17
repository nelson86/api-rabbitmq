package com.narabel.rabbitmq.consumer.service;

import com.narabel.rabbitmq.consumer.domain.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.narabel.rabbitmq.consumer.config.RabbitMQConfig.QUEUE;

@Component
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    @RabbitListener(queues = {QUEUE})
    public void receivedMessage(User user) throws Exception {

        if(user.getUserId().contains("1"))
            throw new Exception("Error en valor Id: "+user.getUserId());

        logger.info("User Details Received is.. " + user);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
