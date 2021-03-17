package com.narabel.rabbitmq.producer.controller;

import com.narabel.rabbitmq.producer.domain.User;
import com.narabel.rabbitmq.producer.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class ProducerController {

    private RabbitMqSender rabbitMqSender;

    @Autowired
    public ProducerController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @Value("${app.message}")
    private String message;

    @PostMapping(value = "user")
    public String publishUserDetails(@RequestBody User user) {
        for (Integer i = 1; i <= 10; i++){
            user.setUserId( i.toString() );
            rabbitMqSender.send(user);
        }
        return message;
    }
}
