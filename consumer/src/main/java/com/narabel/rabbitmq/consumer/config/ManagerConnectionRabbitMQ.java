package com.narabel.rabbitmq.consumer.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class ManagerConnectionRabbitMQ {

    public void setConnection( RabbitAdmin rabbitAdmin, Map<String, Object> arguments ) {
/*
        log.info("Generando conexion para Usuario");
        ConnectionRabbitMQ.setConnection(rabbitAdmin, arguments,"user.exchange", "user.queue", "user.routingkey");
*/
        log.info("Generando conexion para Oficina");
        new ConnectionOffice().setConnection(rabbitAdmin, arguments);

    }

}
